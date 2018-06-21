package com.codecool.queststore.backend.webControllers;

import com.codecool.queststore.backend.dao.LoginDAO;
import com.codecool.queststore.backend.session.SessionIdContainer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractHandler {
    private SessionIdContainer sessionIdContainer;

    public AbstractHandler() {
        this.sessionIdContainer = SessionIdContainer.getSessionIdContainer();
    }

    public SessionIdContainer getSessionIdContainer() {
        return sessionIdContainer;
    }

    public void redirectToLocation(HttpExchange exchange, String location) {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.add("Location", location);
        try {
            exchange.sendResponseHeaders(302, -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        exchange.close();
    }

    public void sendResponse(HttpExchange exchange, String response) {
        byte[] bytes = response.getBytes();
        try {
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendTemplateResponse(HttpExchange exchange, String templateName) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        String response = template.render(model);
        sendResponse(exchange, response);
    }

    public void sendTemplateResponseIndex(HttpExchange exchange, String templateName, String firstName, String lastName) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("firstname", firstName);
        model.with("lastname", lastName);
        String response = template.render(model);
        sendResponse(exchange, response);
    }

    public Map<String, String> readFormData(HttpExchange exchange) {
        String formData = "";

        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            formData = br.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }
        return parseFormData(formData);
    }

    public Map<String, String> parseFormData(String formData) {
        Map<String, String> inputs = new HashMap<>();
        String key;
        String value;
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            key = keyValue[0];
            try {
                value = URLDecoder.decode(keyValue[1], "UTF-8");
                inputs.put(key, value);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return inputs;
    }


    public boolean isLoggedIn(String sid) {
        return getSessionIdContainer().contains(sid);
    }

    public String getSidFromCookieStr(String cookieStr) {
        HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
        return cookie.toString().split("=")[1];
    }

    public String getPermissions(String sessionID) {
        String login = sessionIdContainer.getUserLogin(sessionID);
        try {
            return new LoginDAO().getTypeBy(login);
        } catch (SQLException e) {
            return "undefined";
        }
    }

    public String getLoginFromExchange(HttpExchange exchange) {
        String cookieStr = exchange.getRequestHeaders().getFirst("Cookie");
        String sessionId = getSidFromCookieStr(cookieStr);
        return getSessionIdContainer().getUserLogin(sessionId);
    }
}
