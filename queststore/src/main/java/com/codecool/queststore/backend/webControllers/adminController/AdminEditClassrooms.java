package com.codecool.queststore.backend.webControllers.adminController;

import com.codecool.queststore.backend.dao.ClassroomDAO;
import com.codecool.queststore.backend.model.Classroom;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;

public class AdminEditClassrooms extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        sendTemplateResponseClassrooms(exchange, "adminclassroom");
    }

    private void sendTemplateResponseClassrooms(HttpExchange exchange, String templateName) {
        List<Classroom> classrooms = new ClassroomDAO().loadAllClassrooms();
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("classrooms", classrooms);
        String response = template.render(model);
        sendResponse(exchange, response);
    }
}
