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
        System.out.println("Permissions : " + permissions);

        switch (permissions) {
            case "undefined":
                System.out.println("redir to login!");
                redirectToLocation(exchange, "login");
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


}
