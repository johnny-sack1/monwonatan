package com.codecool.queststore.backend.webControllers.studentController;

import com.codecool.queststore.backend.dao.StudentDAO;
import com.codecool.queststore.backend.model.Student;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.sql.SQLException;
import java.util.Map;

public class StudentProfile extends AbstractHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        if(exchange.getRequestMethod().equals("GET")) {
            sendTemplateResponseProfile(exchange, "studentProfile",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
        }
        else if (exchange.getRequestMethod().equals("POST")) {
            Map inputs = readFormData(exchange);
            String firstName = (String) inputs.get("firstName");
            String lastName = (String) inputs.get("lastName");
            sendTemplateResponseProfile(exchange, "studentProfile",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")), firstName, lastName);
        }
    }

    //TODO if user wants to change password

    
}
