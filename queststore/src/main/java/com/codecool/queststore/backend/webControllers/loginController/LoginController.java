package com.codecool.queststore.backend.webControllers.loginController;

import com.codecool.queststore.backend.dao.LoginDAO;
import com.codecool.queststore.backend.loginManager.LoginProcessHandler;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.net.HttpCookie;
import java.util.Map;
import java.util.UUID;

public class LoginController extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {

        String method = exchange.getRequestMethod();
        String cookieStr = exchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;
        String sessionId;

        if (cookieStr == null) {
            sessionId = generateSID();
            cookie = new HttpCookie("SID", sessionId);
            exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
        } else {
            sessionId = getSidFromCookieStr(cookieStr);
        }
        if(method.equals("GET") && !isLoggedIn(sessionId)){
            sendTemplateResponse(exchange, "login");
        }

        if(method.equals("GET") && isLoggedIn(sessionId)){
            redirectToLocation(exchange, getPermissions(sessionId));
        }

        if(method.equals("POST")){
            Map inputs = readFormData(exchange);
            String login = (String) inputs.get("login");
            String password = (String) inputs.get("password");

            LoginDAO loginDAO = new LoginDAO();
            String loginResult = new LoginProcessHandler(loginDAO).loginProcess(login, password);
            if (validLoginResult(loginResult)) {
                getSessionIdContainer().add(sessionId, login);
                redirectToLocation(exchange, "/" + loginResult);
            } else {
                redirectToLocation(exchange, "/login");
            }
        }
    }

    private String generateSID() {
        return UUID.randomUUID().toString();
    }

    private boolean validLoginResult(String loginResult) {
        return !loginResult.equalsIgnoreCase("invalid password");
    }
}
