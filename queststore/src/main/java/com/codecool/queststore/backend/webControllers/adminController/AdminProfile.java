package com.codecool.queststore.backend.webControllers.adminController;

import com.codecool.queststore.backend.dao.AdminDAO;
import com.codecool.queststore.backend.loginManager.PasswordManager;
import com.codecool.queststore.backend.model.Admin;
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


public class AdminProfile extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        if (method.equalsIgnoreCase("GET")) {
            sendTemplateResponseProfile(exchange, "adminprofile",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
        }

        if (method.equalsIgnoreCase("POST")) {
            try {
                Admin admin = loadAdminBySessionID(getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
                updateAdmin(admin, exchange);
            } catch (SQLException e) {
                redirectToLocation(exchange, "/admin/index");
            }
        }
    }

    private void sendTemplateResponseProfile(HttpExchange exchange, String templateName, String sessionId) {
        try {
            Admin admin = loadAdminBySessionID(sessionId);
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

    private Admin loadAdminBySessionID(String sessionId) throws SQLException{
        String login = getSessionIdContainer().getUserLogin(sessionId);
        Admin admin = new AdminDAO().loadAdmin(login);
        return admin;
    }

    private void updateAdmin(Admin admin, HttpExchange exchange) {
        Map inputs = readAdminData(exchange);
        String firstName = (String) inputs.get("firstname");
        String lastName = (String) inputs.get("lastname");
        String password = (String) inputs.get("password");
        String password2 = (String) inputs.get("password2");
        String email = (String) inputs.get("email");

        if(password == null && password2 == null) {
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            admin.setEmail(email);
            new AdminDAO().updateAdmin(admin);
            redirectToLocation(exchange, "/admin/profile");
        } else if (password.equals(password2)) {
            try {
                admin.setPassword(new PasswordManager().generateStorngPasswordHash(password));
                admin.setFirstName(firstName);
                admin.setLastName(lastName);
                admin.setEmail(email);
                new AdminDAO().updateAdmin(admin);
                redirectToLocation(exchange, "/admin/profile");
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                redirectToLocation(exchange, "/admin/index");
            }
        } else {
            redirectToLocation(exchange, "/admin/index");
        }
    }


    public Map<String, String> readAdminData(HttpExchange exchange) {
        String formData = "";

        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            formData = br.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }
        return parseAdminData(formData);
    }

    public Map<String, String> parseAdminData(String formData) {
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
