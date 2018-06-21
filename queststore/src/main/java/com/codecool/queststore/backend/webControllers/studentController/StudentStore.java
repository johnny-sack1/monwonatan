package com.codecool.queststore.backend.webControllers.studentController;

import com.codecool.queststore.backend.dao.BackpackDAO;
import com.codecool.queststore.backend.dao.StudentDAO;
import com.codecool.queststore.backend.dao.ArtifactDAO;
import com.codecool.queststore.backend.model.Artifact;
import com.codecool.queststore.backend.model.Backpack;
import com.codecool.queststore.backend.model.Student;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class StudentStore extends AbstractHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {

        if(exchange.getRequestMethod().equals("GET")) {
            sendTemplateResponseStore(exchange, "studentStore",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")));
        }
        else if (exchange.getRequestMethod().equals("POST")) {
            Map inputs = readFormData(exchange);
            String artifactName = (String) inputs.get("name");
            sendTemplateResponseStore(exchange, "studentStore",
                    getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")), artifactName);
        }
    }

    public void sendTemplateResponseStore(HttpExchange exchange, String templateName, String sessionId) {

        try {
            String login = getSessionIdContainer().getUserLogin(sessionId);
            Student student = new StudentDAO().loadStudent(login);

            JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
            JtwigModel model = JtwigModel.newModel();

            List<Artifact> artifactList = new ArtifactDAO().loadAllArtifacts();

            model.with("title", "Student store");
            model.with("items", artifactList);
            model.with("coins", student.getCoolcoins());
            String response = template.render(model);
            sendResponse(exchange, response);
        }
        catch (SQLException e) {
            redirectToLocation(exchange, "login");
        }
    }

    public void sendTemplateResponseStore(HttpExchange exchange, String templateName, String sessionId, String artifactName) {

        try {
            String login = getSessionIdContainer().getUserLogin(sessionId);
            StudentDAO studentDAO = new StudentDAO();
            BackpackDAO backpackDAO = new BackpackDAO();
            Student student = studentDAO.loadStudent(login);
            ArtifactDAO artifactDAO = new ArtifactDAO();
            Artifact artifact = artifactDAO.loadArtifact(artifactName);
            int price = artifact.getPrice();

            if (price < student.getCoolcoins()) {
                student.substractCoins(price);
                Backpack backpack = student.getBackpack();

                backpack.addToBackpack(artifact, "unused");
                backpackDAO.updateBackpack(backpack);
            }

            studentDAO.updateStudent(student);

            JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
            JtwigModel model = JtwigModel.newModel();

            List<Artifact> artifactList = new ArtifactDAO().loadAllArtifacts();

            model.with("title", "Student store");
            model.with("items", artifactList);
            model.with("coins", student.getCoolcoins());
            String response = template.render(model);
            sendResponse(exchange, response);
        }
        catch (SQLException e) {
            redirectToLocation(exchange, "login");
        }
    }
}