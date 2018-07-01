package com.codecool.queststore.backend.webControllers.studentController;

import com.codecool.queststore.backend.dao.StudentDAO;
import com.codecool.queststore.backend.model.Student;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.sql.SQLException;

public class StudentIndex extends AbstractHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        try {
                String login = getLoginFromExchange(exchange);
                Student student = new StudentDAO().loadStudent(login);
                sendTemplateResponseIndex(exchange, "index", "Student Home", "Student's", student.getFirstName(), student.getLastName());
            } catch (SQLException e) {
                redirectToLocation(exchange, "logout");
        }
    }
}