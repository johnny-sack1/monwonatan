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
import java.util.*;

public class StudentBackpack extends AbstractHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {

        if(exchange.getRequestMethod().equals("GET")) {
            sendTemplateResponseBackpack(exchange, "backpack",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
        }
        else if (exchange.getRequestMethod().equals("POST")) {
            Map inputs = readFormData(exchange);
            int artifactNumber = Integer.parseInt((String) inputs.get("name"));
            updateStudent(exchange,
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")), artifactNumber);
            redirectToLocation(exchange, "/student");
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

    public void updateStudent(HttpExchange exchange, String sessionId, int artifactNumber) {

        try {

            Student student = loadStudentBySessionID(sessionId);
            postMethod(student, artifactNumber);

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

        Map<Artifact, String> sortedMap = sortMap(studentBackpack);
        for (Map.Entry<Artifact, String> entry : sortedMap.entrySet())
        {
            Artifact artifact = entry.getKey();
            artifact.setStatus(entry.getValue());
            items.add(artifact);
        }
        return items;
    }

    private void postMethod(Student student, int artifactNumber) {
        HashMap<Artifact, String> studentBackpack = student.getBackpack().getStudentBackpack();
        List<Artifact> items = new ArrayList<>();

        Map<Artifact, String> sortedMap = sortMap(studentBackpack);
        int counter = -1;

        for (Map.Entry<Artifact, String> entry : sortedMap.entrySet())
        {
            counter++;

            Artifact artifact = entry.getKey();
            if (counter == artifactNumber) {
                artifact.setStatus("pending");
                studentBackpack.replace(artifact, "pending");
            }
            else {
                artifact.setStatus(entry.getValue());
            }

            items.add(artifact);
        }
        updateBackpack(student, studentBackpack);
    }

    private void updateBackpack(Student student, HashMap<Artifact, String> studentBackpack) {
        Backpack backpack = student.getBackpack();
        backpack.setStudentBackpack(studentBackpack);
        BackpackDAO backpackDAO = new BackpackDAO();
        backpackDAO.updateBackpack(backpack);
    }

    private Map<Artifact, String> sortMapByStatus(Map<Artifact, String> studentBackpack, String status) {
        Map<Artifact, String> sordedMap = new LinkedHashMap<>();
        for (Map.Entry<Artifact, String> entry : studentBackpack.entrySet())
        {
            Artifact artifact = entry.getKey();
            if (entry.getValue().equals(status)) {
                sordedMap.put(artifact, status);
            }
        }
        return sordedMap;
    }

    private Map<Artifact, String> sortMap(Map<Artifact, String> studentBackpack) {
        Map<Artifact, String> sortedMap = sortMapByStatus(studentBackpack, "unused");
        sortedMap.putAll(sortMapByStatus(studentBackpack, "pending"));
        sortedMap.putAll(sortMapByStatus(studentBackpack, "done"));
        return sortedMap;
    }
}
