package com.codecool.queststore.backend.webControllers.adminController;

import com.codecool.queststore.backend.dao.AdminDAO;
import com.codecool.queststore.backend.model.Admin;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.sql.SQLException;

public class AdminController extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

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
        System.out.println(exchange.getRequestURI().toString());
        String[] uriParts = exchange.getRequestURI().toString().split("/");

        if (uriParts.length <= 2) {
            // "/admin"
            new AAdminIndex().handle(exchange);
        } else {
            // "/admin/(action)"
            int ACTION_I = 2;
            String action = uriParts[ACTION_I];

            if (action.equals("profile")) {
                new AAdminProfile().handle(exchange);
            } else if (action.equals("mentors")) {
                new AMentorsPage().handle(exchange);
            } else if (action.equals("classroom")) {
                new AClassroomPage().handle(exchange);
            } else if (action.equals("index")) {
                new AAdminIndex().handle(exchange);
            } else {
                redirectToLocation(exchange, "/login");
            }
        }
    }
}
