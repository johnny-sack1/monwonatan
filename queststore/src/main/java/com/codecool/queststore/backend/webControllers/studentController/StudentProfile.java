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

    private void updateStudent(Student student, HttpExchange exchange) {
        Map inputs = readStudentData(exchange);
        String firstName = (String) inputs.get("firstname");
        String lastName = (String) inputs.get("lastname");
        String password = (String) inputs.get("password");
        String password2 = (String) inputs.get("password2");

        if(password == null && password2 == null) {
            student.setFirstName(firstName);
            student.setLastName(lastName);

            new StudentDAO().updateStudent(student);
            redirectToLocation(exchange, "/student/profile");
        } else if (password.equals(password2)) {
            try {
                student.setPassword(new PasswordManager().generateStorngPasswordHash(password));
                student.setFirstName(firstName);
                student.setLastName(lastName);

                new StudentDAO().updateStudent(student);
                redirectToLocation(exchange, "/student/profile");
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                redirectToLocation(exchange, "/student");
            }
        } else {
            redirectToLocation(exchange, "/student");
        }
    }

    public Map<String, String> readStudentData(HttpExchange exchange) {
        String formData = "";

        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            formData = br.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }
        return parseStudentData(formData);
    }

    public Map<String, String> parseStudentData(String formData) {
        Map<String, String> inputs = new HashMap<>();
        String key;
        String value;
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            key = keyValue[0];
            if (key.contains("password")) {
                continue;
            }
            try {
                value = URLDecoder.decode(keyValue[1], "UTF-8");
                inputs.put(key, value);

            } catch (UnsupportedEncodingException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return inputs;
    }
}
