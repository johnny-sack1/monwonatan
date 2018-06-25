package com.codecool.queststore.backend.webControllers.studentController;

import com.codecool.queststore.backend.dao.BackpackDAO;
import com.codecool.queststore.backend.dao.StudentDAO;
import com.codecool.queststore.backend.model.Artifact;
import com.codecool.queststore.backend.model.Backpack;
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
            sendTemplateResponseBackpack(exchange, "backpack",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
        }
        else if (exchange.getRequestMethod().equals("POST")) {
            Map inputs = readFormData(exchange);
            int artifactName = Integer.parseInt((String) inputs.get("name"));
            sendTemplateResponseBackpack(exchange, "backpack",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")), artifactName);
        }
    }

    public void sendTemplateResponseBackpack(HttpExchange exchange, String templateName, String sessionId) {

        try {
            Student student = loadStudentBySessionID(sessionId);

            JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
            JtwigModel model = JtwigModel.newModel();

            List<Artifact> items = getMethod(student);

            model.with("title", "Student backpack");
            model.with("items", items);
            String response = template.render(model);
            sendResponse(exchange, response);
        }
        catch (SQLException e) {
            redirectToLocation(exchange, "login");
        }
    }

    public void sendTemplateResponseBackpack(HttpExchange exchange, String templateName, String sessionId, int artifactName) {

        try {

            Student student = loadStudentBySessionID(sessionId);

            JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
            JtwigModel model = JtwigModel.newModel();

            HashMap<Artifact, String> studentBackpack = student.getBackpack().getStudentBackpack();
            List<Artifact> items = new ArrayList<>();
            int counter = -1;
            
            for (Map.Entry<Artifact, String> entry : studentBackpack.entrySet())
            {
                counter++;

                Artifact artifact = entry.getKey();
                if (counter == artifactName) {
                    artifact.setStatus("pending");
                    studentBackpack.replace(artifact, "pending");
                }
                else {
                    artifact.setStatus(entry.getValue());
                }

                items.add(artifact);
            }
            Backpack backpack = student.getBackpack();
            backpack.setStudentBackpack(studentBackpack);
            BackpackDAO backpackDAO = new BackpackDAO();
            backpackDAO.updateBackpack(backpack);
            model.with("title", "Student backpack");
            model.with("items", items);
            String response = template.render(model);
            sendResponse(exchange, response);
        }
        catch (SQLException e) {
            redirectToLocation(exchange, "login");
        }
    }

    private Student loadStudentBySessionID(String sessionId) throws SQLException{
        String login = getSessionIdContainer().getUserLogin(sessionId);
        Student student = new StudentDAO().loadStudent(login);
        return student;
    }

    private List<Artifact> getMethod(Student student) {
        HashMap<Artifact, String> studentBackpack = student.getBackpack().getStudentBackpack();
        List<Artifact> items = new ArrayList<>();
        for (Map.Entry<Artifact, String> entry : studentBackpack.entrySet())
        {
            Artifact artifact = entry.getKey();
            artifact.setStatus(entry.getValue());
            items.add(artifact);
        }
        return items;
    }
}
