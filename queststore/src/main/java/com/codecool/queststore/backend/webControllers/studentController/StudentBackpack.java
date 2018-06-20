package com.codecool.queststore.backend.webControllers.studentController;

import com.codecool.queststore.backend.dao.StudentDAO;
import com.codecool.queststore.backend.model.Artifact;
import com.codecool.queststore.backend.model.Student;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentBackpack extends AbstractHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        if(exchange.getRequestMethod().equals("GET")) {
            sendTemplateResponseBackpack(exchange, "studentBackpack",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
        }
        else if (exchange.getRequestMethod().equals("POST")) {
            //TODO student click use button
        }
    }


}
