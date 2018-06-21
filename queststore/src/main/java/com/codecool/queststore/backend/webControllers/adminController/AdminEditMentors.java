package com.codecool.queststore.backend.webControllers.adminController;

import com.codecool.queststore.backend.dao.ClassroomDAO;
import com.codecool.queststore.backend.dao.MentorDAO;
import com.codecool.queststore.backend.model.Classroom;
import com.codecool.queststore.backend.model.Mentor;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;
import java.util.Map;

public class AdminEditMentors extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String[] uriParts = exchange.getRequestURI().toString().split("/");

        if (uriParts.length == 3) {
            // "/admin/mentors"
            sendTemplateResponseAllMentors(exchange, "admin-manage-mentors");
        } else {

            // "/admin/classroom/(action)"
            int ACTION_I = 3;
            String action = uriParts[ACTION_I];

            if (action.equals("edit")) {
                editMentor(exchange);
            } else if (action.equals("delete")) {
                deleteMentor(exchange);
            } else if (action.equals("add")) {
                createMentor(exchange);
            } else {
                redirectToLocation(exchange, "/login");
            }
        }
    }

    private void editMentor(HttpExchange exchange) {}

    private void deleteMentor(HttpExchange exchange) {}

    private void createMentor(HttpExchange exchange) {
        String method = exchange.getRequestMethod();

        if(method.equalsIgnoreCase("GET")) {
            sendMentorCreationPage(exchange);
        } else if (method.equalsIgnoreCase("POST")) {
            submitMentorCreationPage(exchange);
        } else {
            redirectToLocation(exchange, "/admin/index");
        }
    }

    private void sendMentorCreationPage(HttpExchange exchange) {
        sendTemplateResponseClassrooms(exchange, "admin-create-mentor");
    }

    private void submitMentorCreationPage(HttpExchange exchange) {
        Map<String, String> inputs = readFormData(exchange);
        String firstName = inputs.get("firstname");
        String lastName = inputs.get("lastname");
        String email = inputs.get("email");
        String address = inputs.get("address");
        int classroomId = Integer.parseInt(inputs.get("classroom"));
        String login = inputs.get("login");
        String password = inputs.get("password1");
        String repeatedPassword = inputs.get("password2");

        if (password.equals(repeatedPassword)) {
            new MentorDAO().createMentor(firstName, lastName, login, password, classroomId, email, address);
        }

        redirectToLocation(exchange, "/admin/mentors");
    }

    private void sendTemplateResponseClassrooms(HttpExchange exchange, String templateName) {
        List<Classroom> classrooms = new ClassroomDAO().loadAllClassrooms();
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("classrooms", classrooms);
        String response = template.render(model);
        sendResponse(exchange, response);
    }

    private void sendTemplateResponseAllMentors(HttpExchange exchange, String templateName) {
        List<Mentor> mentors = new MentorDAO().loadAllMentors();

        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("mentors", mentors);
        String response = template.render(model);
        sendResponse(exchange, response);
    }
}
