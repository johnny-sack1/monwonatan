package com.codecool.queststore.backend.webControllers.mentorController;

import com.codecool.queststore.backend.dao.MentorDAO;
import com.codecool.queststore.backend.loginManager.PasswordManager;
import com.codecool.queststore.backend.model.Mentor;
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

public class MentorProfile extends AbstractHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        if (method.equalsIgnoreCase("GET")) {
            sendTemplateResponseProfile(exchange, "mentorprofile",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
        }
        else if (method.equalsIgnoreCase("POST")) {
            try {
                Mentor mentor = loadMentorBySessionID(getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
                updateMentor(mentor, exchange);
            }
            catch (SQLException e) {
                redirectToLocation(exchange, "/mentor/index");
            }
        }
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

    private Mentor loadMentorBySessionID(String sessionID) throws SQLException {
        String login = getSessionIdContainer().getUserLogin(sessionID);
        Mentor mentor = new MentorDAO().loadMentor(login);
        return mentor;
    }

    private void updateMentor(Mentor mentor, HttpExchange exchange) {
        Map inputs = readMentorData(exchange);
        String firstName = (String) inputs.get("firstname");
        String lastName = (String) inputs.get("lastname");
        String password = (String) inputs.get("password");
        String password2 = (String) inputs.get("password2");
        String email = (String) inputs.get("email");
        String address = (String) inputs.get("address");

        if (password == null && password2 == null) {
            mentor.setFirstName(firstName);
            mentor.setLastName(lastName);
            mentor.setEmail(email);
            mentor.setAddress(address);
            new MentorDAO().updateMentor(mentor);
            redirectToLocation(exchange, "/mentor/profile");
        } else if (password.equals(password2)) {
            try {
                mentor.setPassword(new PasswordManager().generateStorngPasswordHash(password));
                mentor.setFirstName(firstName);
                mentor.setLastName(lastName);
                mentor.setEmail(email);
                mentor.setAddress(address);
                new MentorDAO().updateMentor(mentor);
                redirectToLocation(exchange, "/mentor/profile");
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                redirectToLocation(exchange, "/mentor/index");
            }
        } else {
            redirectToLocation(exchange, "/mentor/index");
        }
    }

    public Map<String, String> readMentorData(HttpExchange exchange) {
        String formData = "";

        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            formData = br.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }
        return parseMentorData(formData);
    }

    public Map<String, String> parseMentorData(String formData) {
        Map<String, String> inputs = new HashMap<>();
        String key;
        String value;
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            key = keyValue[0];
            /*if (key.contains("password")) {
                continue;
            }*/
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
