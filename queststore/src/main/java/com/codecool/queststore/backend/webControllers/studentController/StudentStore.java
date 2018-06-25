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
            String artifactName = getArtifactName(exchange);
            buy(exchange, getSidFromCookieStr(exchange.getRequestHeaders().getFirst("Cookie")),
                    artifactName);
            redirectToLocation(exchange, "/student");
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

    public void buy(HttpExchange exchange, String sessionId, String artifactName) {

        updateBackpack(exchange, sessionId, artifactName);

    }

    private Student loadStudentBySessionID(String sessionId) throws SQLException{
        String login = getSessionIdContainer().getUserLogin(sessionId);
        Student student = new StudentDAO().loadStudent(login);
        return student;
    }

    private void updateStudent(Student student) {
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.updateStudent(student);
    }

}
