package com.codecool.queststore.backend.webControllers.adminController;

import com.codecool.queststore.backend.dao.AdminDAO;
import com.codecool.queststore.backend.model.Admin;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.sql.SQLException;

public class AAdminIndex extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try {
            String login = getLoginFromExchange(exchange);
            Admin admin = new AdminDAO().loadAdmin(login);
            sendTemplateResponseIndex(exchange, "adminindex", admin.getFirstName(), admin.getLastName());
        } catch (SQLException e) {
            redirectToLocation(exchange, "logout");
        }

    }
}
