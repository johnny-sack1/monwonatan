package com.codecool.queststore.backend.webControllers.studentController;

import com.codecool.queststore.backend.dao.StudentDAO;
import com.codecool.queststore.backend.dao.ArtifactDAO;
import com.codecool.queststore.backend.model.Artifact;
import com.codecool.queststore.backend.model.Student;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.sql.SQLException;
import java.util.List;

public class StudentStore extends AbstractHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        if(exchange.getRequestMethod().equals("GET")) {
            sendTemplateResponseStore(exchange, "studentStore",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
        }
        else if (exchange.getRequestMethod().equals("POST")) {
            //TODO student click buy button
        }
    }


}
