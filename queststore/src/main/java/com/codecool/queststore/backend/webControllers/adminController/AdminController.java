package com.codecool.queststore.backend.webControllers.adminController;

import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class AdminController extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {

        String cookieStr = exchange.getRequestHeaders().getFirst("Cookie");
        String sessionId = "";
        if (cookieStr == null) {
            redirectToLocation(exchange, "/login");
        } else {
            sessionId = getSidFromCookieStr(cookieStr);
        }

        String permissions = getPermissions(sessionId);

        switch (permissions) {
            case "undefined":
                redirectToLocation(exchange, "/login");
                break;
            case "student":
                redirectToLocation(exchange, "student");
                break;
            case "mentor":
                redirectToLocation(exchange, "mentor");
                break;
            case "admin":
                adminRedirect(exchange);
                break;

            default:
                redirectToLocation(exchange, "logout");
        }
    }

    private void adminRedirect(HttpExchange exchange) {
        String[] uriParts = exchange.getRequestURI().toString().split("/");

        if (uriParts.length <= 2) {
            // "/admin"
            redirectToLocation(exchange, "admin/index");
        } else {

            // "/admin/(action)"
            int ACTION_I = 2;
            String action = uriParts[ACTION_I];

            if (action.equals("profile")) {
                new AdminProfile().handle(exchange);
            } else if (action.equals("mentors")) {
                new AdminEditMentors().handle(exchange);
            } else if (action.equals("classroom")) {
                new AdminManageClassrooms().handle(exchange);
            } else if (action.equals("index")) {
                new AdminIndex().handle(exchange);
            } else {
                redirectToLocation(exchange, "/login");
            }
        }
    }
}
