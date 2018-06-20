package com.codecool.queststore.backend.webControllers.adminController;

import com.codecool.queststore.backend.dao.AdminDAO;
import com.codecool.queststore.backend.model.Admin;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.sql.SQLException;


public class AAdminProfile extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        sendTemplateResponseProfile(exchange, "adminprofile",
                getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
    }

    private void sendTemplateResponseProfile(HttpExchange exchange, String templateName, String sessionId) {
        try {
            String login = getSessionIdContainer().getUserLogin(sessionId);
            Admin admin = new AdminDAO().loadAdmin(login);
            JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
            JtwigModel model = JtwigModel.newModel();
            model.with("firstname", admin.getFirstName());
            model.with("lastname", admin.getLastName());
            model.with("email", admin.getEmail());
            String response = template.render(model);
            sendResponse(exchange, response);
        } catch (SQLException e) {
            redirectToLocation(exchange, "login");
        }
    }
}
