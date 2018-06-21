package com.codecool.queststore.backend.webControllers.mentorController;

import com.codecool.queststore.backend.dao.StudentDAO;
import com.codecool.queststore.backend.model.Student;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;

public class StudentEdition extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        sendTemplateResponseWithTable(exchange, "studentedition");
    }

    private void sendTemplateResponseWithTable(HttpExchange exchange, String templateName) {
        List<Student> students = new StudentDAO().loadAllStudents();
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("students", students);
        String response = template.render(model);
        sendResponse(exchange, response);
    }
}
