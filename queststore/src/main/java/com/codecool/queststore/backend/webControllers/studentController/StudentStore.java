package com.codecool.queststore.backend.webControllers.studentController;

import com.codecool.queststore.backend.dao.BackpackDAO;
import com.codecool.queststore.backend.dao.StudentDAO;
import com.codecool.queststore.backend.dao.ArtifactDAO;
import com.codecool.queststore.backend.databaseConnection.SQLQueryHandler;
import com.codecool.queststore.backend.model.Artifact;
import com.codecool.queststore.backend.model.Backpack;
import com.codecool.queststore.backend.model.Student;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class StudentStore extends AbstractHandler implements HttpHandler {
    SQLQueryHandler sqlQueryHandler = SQLQueryHandler.getInstance();
    Connection c = SQLQueryHandler.getInstance().getConnection();

    private StudentDAO studentDAO = new StudentDAO();
    private BackpackDAO backpackDAO = new BackpackDAO();
    private ArtifactDAO artifactDAO = new ArtifactDAO(c, sqlQueryHandler);

    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void setArtifactDAO(ArtifactDAO artifactDAO) {
        this.artifactDAO = artifactDAO;
    }

    public void setBackpackDAO(BackpackDAO backpackDAO) {
        this.backpackDAO = backpackDAO;
    }

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
            redirectToLocation(exchange, "/student/store");
        }
    }

    public void sendTemplateResponseStore(HttpExchange exchange, String templateName, String sessionId) {

        try {
            String login = getSessionIdContainer().getUserLogin(sessionId);
            Student student = new StudentDAO().loadStudent(login);

            JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
            JtwigModel model = JtwigModel.newModel();

            List<Artifact> artifactList = new ArtifactDAO(c, sqlQueryHandler).loadAllArtifacts();

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
        try {
            Student student = loadStudentBySessionID(sessionId);
            Artifact artifact = artifactDAO.loadArtifact(artifactName);
            int price = artifact.getPrice();

            if (price < student.getCoolcoins()) {
                student.substractCoins(price);
                Backpack backpack = student.getBackpack();

                backpack.addToBackpack(artifact, "unused");
                backpackDAO.updateBackpack(backpack);
            }
            updateStudent(student);
        }
        catch (SQLException e) {
            redirectToLocation(exchange, "login");
        }

    }

    private Student loadStudentBySessionID(String sessionId) throws SQLException{
        String login = getSessionIdContainer().getUserLogin(sessionId);
        Student student = studentDAO.loadStudent(login);
        return student;
    }

    private void updateStudent(Student student) {
        studentDAO.updateStudent(student);
    }

//    private void updateBackpack(HttpExchange exchange, String sessionId, String artifactName) {
//        try {
//            BackpackDAO backpackDAO = new BackpackDAO();
//            Student student = loadStudentBySessionID(sessionId);
//            ArtifactDAO artifactDAO = new ArtifactDAO();
//            Artifact artifact = artifactDAO.loadArtifact(artifactName);
//            int price = artifact.getPrice();
//
//            if (price < student.getCoolcoins()) {
//                student.substractCoins(price);
//                Backpack backpack = student.getBackpack();
//
//                backpack.addToBackpack(artifact, "unused");
//                backpackDAO.updateBackpack(backpack);
//            }
//            updateStudent(student);
//        }
//        catch (SQLException e) {
//            redirectToLocation(exchange, "login");
//        }
//    }

    public String getArtifactName(HttpExchange exchange) {
        Map inputs = readFormData(exchange);
        String artifactName = (String) inputs.get("name");
        return artifactName;
    }
}
