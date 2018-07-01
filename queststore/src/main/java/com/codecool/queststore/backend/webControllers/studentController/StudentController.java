package com.codecool.queststore.backend.webControllers.studentController;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.codecool.queststore.backend.webControllers.AbstractHandler;

public class StudentController  extends AbstractHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {

        String cookieStr = exchange.getRequestHeaders().getFirst("Cookie");
        String sessionId = "";
        if (cookieStr == null) {
            redirectToLocation(exchange, "login");
        } else {
            sessionId = getSidFromCookieStr(cookieStr);
        }

        String permissions = getPermissions(sessionId);

        switch (permissions) {
            case "undefined":
                redirectToLocation(exchange, "/login");
                break;
            case "admin":
                redirectToLocation(exchange, "admin");
                break;
            case "mentor":
                redirectToLocation(exchange, "mentor");
                break;
            case "student":
                studentRedirect(exchange);
                break;

            default:
                redirectToLocation(exchange, "logout");
        }
    }

    private void studentRedirect(HttpExchange exchange) {
        String[] uriParts = exchange.getRequestURI().toString().split("/");

        if (uriParts.length <= 2) {
            // "/student"
            new StudentIndex().handle(exchange);
        } else {
            // "/student/(action)"
            int ACTION_I = 2;
            String action = uriParts[ACTION_I];

            if (action.equals("profile")) {
                new StudentProfile().handle(exchange);
            } else if (action.equals("backpack")) {
                new StudentBackpack().handle(exchange);
            } else if (action.equals("store")) {
                new StudentStore().handle(exchange);
            } else {
                redirectToLocation(exchange, "/login");
            }
        }
    }
}
