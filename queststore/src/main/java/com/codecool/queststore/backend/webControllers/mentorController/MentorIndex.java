package com.codecool.queststore.backend.webControllers.mentorController;

import com.codecool.queststore.backend.dao.MentorDAO;
import com.codecool.queststore.backend.model.Mentor;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.sql.SQLException;

public class MentorIndex extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try {
            String login = getLoginFromExchange(exchange);
            Mentor mentor = new MentorDAO().loadMentor(login);
            sendTemplateResponseIndex(exchange, "mentorindex", mentor.getFirstName(), mentor.getLastName());
        }
        catch (SQLException e) {
            redirectToLocation(exchange, "logout");
        }

    }
}
