package com.codecool.queststore.backend.webControllers.mentorController;

import com.codecool.queststore.backend.dao.MentorDAO;
import com.codecool.queststore.backend.model.Mentor;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.sql.SQLException;

public class MentorProfile extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        sendTemplateResponseProfile(exchange, "mentorprofile",
                getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
    }

    private void sendTemplateResponseProfile(HttpExchange exchange, String templateName, String sessionId) {
        try {
            String login = getSessionIdContainer().getUserLogin(sessionId);
            Mentor mentor = new MentorDAO().loadMentor(login);
            JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
            JtwigModel model = JtwigModel.newModel();
            model.with("firstname", mentor.getFirstName());
            model.with("lastname", mentor.getLastName());
            model.with("email", mentor.getEmail());
            model.with("address", mentor.getAddress());
            String response = template.render(model);
            sendResponse(exchange, response);
        } catch (SQLException e) {
            redirectToLocation(exchange, "login");
        }
    }
}
