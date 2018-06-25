package com.codecool.queststore.backend.webControllers.adminController;


import com.codecool.queststore.backend.dao.StudentDAO;
import com.codecool.queststore.backend.loginManager.PasswordManager;
import com.codecool.queststore.backend.model.Student;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class StudentProfile extends AbstractHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        if (method.equalsIgnoreCase("GET")) {
            sendTemplateResponseProfile(exchange, "studentProfile",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
        }

        if (method.equalsIgnoreCase("POST")) {
            try {
                Student student = loadStudentBySessionID(getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
                updateStudent(student, exchange);
            } catch (SQLException e) {
                redirectToLocation(exchange, "/student");
            }
        }
    }

    private void sendTemplateResponseProfile(HttpExchange exchange, String templateName, String sessionId) {
        try {
            Student student = loadStudentBySessionID(sessionId);
            JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
            JtwigModel model = JtwigModel.newModel();

            model.with("title", "Student profile");
            model.with("coins", student.getCoolcoins());
            model.with("expLevel", student.getExpLvl().getDescription());
            model.with("firstname", student.getFirstName());
            model.with("lastname", student.getLastName());

            String response = template.render(model);
            sendResponse(exchange, response);
        } catch (SQLException e) {
            redirectToLocation(exchange, "login");
        }
    }

    private Student loadStudentBySessionID(String sessionId) throws SQLException{
        String login = getSessionIdContainer().getUserLogin(sessionId);
        Student student = new StudentDAO().loadStudent(login);
        return student;
    }
}
