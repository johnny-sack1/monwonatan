package com.codecool.queststore.backend.webControllers.studentController;

import com.codecool.queststore.backend.dao.StudentDAO;
import com.codecool.queststore.backend.model.Student;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.sql.SQLException;
import java.util.Map;

public class StudentProfile extends AbstractHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        if(exchange.getRequestMethod().equals("GET")) {
            sendTemplateResponseProfile(exchange, "studentProfile",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
        }
        else if (exchange.getRequestMethod().equals("POST")) {
            Map inputs = readFormData(exchange);
            String firstName = (String) inputs.get("firstName");
            String lastName = (String) inputs.get("lastName");
            sendTemplateResponseProfile(exchange, "studentProfile",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")), firstName, lastName);
        }
    }

    //TODO if user wants to change password

    public void sendTemplateResponseProfile(HttpExchange exchange, String templateName, String sessionId) {
        try {
            String login = getSessionIdContainer().getUserLogin(sessionId);
            Student student = new StudentDAO().loadStudent(login);
            JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
            JtwigModel model = JtwigModel.newModel();
            model.with("title", "Student profile");
            model.with("coins", student.getCoolcoins());
            model.with("expLevel", student.getExpLvl().getDescription());
            model.with("firstName", student.getFirstName());
            model.with("lastName", student.getLastName());
            String response = template.render(model);
            sendResponse(exchange, response);
        }
        catch (SQLException e) {
            redirectToLocation(exchange, "login");
        }
    }

    public void sendTemplateResponseProfile(HttpExchange exchange, String templateName, String sessionId, String firstName, String lastName) {
        try {
            StudentDAO studentDAO = new StudentDAO();
            String login = getSessionIdContainer().getUserLogin(sessionId);
            Student student = studentDAO.loadStudent(login);
            student.setFirstName(firstName);
            student.setLastName(lastName);
            studentDAO.updateStudent(student);
            JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
            JtwigModel model = JtwigModel.newModel();
            model.with("title", "Student profile");
            model.with("coins", student.getCoolcoins());
            model.with("expLevel", student.getExpLvl().getDescription());
            model.with("firstName", firstName);
            model.with("lastName", lastName);
            String response = template.render(model);
            sendResponse(exchange, response);
        }
        catch (SQLException e) {
            redirectToLocation(exchange, "login");
        }
    }
}
