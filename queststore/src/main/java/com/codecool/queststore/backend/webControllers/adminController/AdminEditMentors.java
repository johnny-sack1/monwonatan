package com.codecool.queststore.backend.webControllers.adminController;

import com.codecool.queststore.backend.dao.ClassroomDAO;
import com.codecool.queststore.backend.dao.MentorDAO;
import com.codecool.queststore.backend.loginManager.PasswordManager;
import com.codecool.queststore.backend.model.Classroom;
import com.codecool.queststore.backend.model.Mentor;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
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

    private void editMentor(HttpExchange exchange) {
        String method = exchange.getRequestMethod();

        if(method.equalsIgnoreCase("GET")) {
            sendMentorEditPage(exchange);
        } else if (method.equalsIgnoreCase("POST")) {
            submitMentorEditPage(exchange);
        } else {
            redirectToLocation(exchange, "/admin/index");
        }
    }

    private void deleteMentor(HttpExchange exchange) {
        String method = exchange.getRequestMethod();

        if (method.equalsIgnoreCase("GET")) {
            sendMentorDeletionPage(exchange);
        } else if (method.equalsIgnoreCase("POST")) {
            submitMentorDeletionPage(exchange);
        } else {
            redirectToLocation(exchange, "/admin/index");
        }
    }

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

    private void sendMentorEditPage(HttpExchange exchange) {
        String mentorLogin = getMentorLogin(exchange);
        if (mentorLogin == null) {
            redirectToLocation(exchange, "/admin/classroom");
        }

        try {
            Mentor mentor = new MentorDAO().loadMentor(mentorLogin);
            sendTemplateResponseSingleMentor(exchange, "admin-edit-mentor", mentor);
        } catch (SQLException e) {
            // Error occurred in database
            redirectToLocation(exchange, "/admin");
        } catch (NullPointerException e) {
            // TODO
            // Classroom not found
            redirectToLocation(exchange, "/admin/mentors");
        }
    }

    private void sendMentorCreationPage(HttpExchange exchange) {
        sendTemplateResponseClassrooms(exchange, "admin-create-mentor");
    }

    private void sendMentorDeletionPage(HttpExchange exchange) {
        String mentorLogin = getMentorLogin(exchange);
        if (mentorLogin == null) {
            redirectToLocation(exchange, "/admin/classroom");
        }
        try {
            Mentor mentor = new MentorDAO().loadMentor(mentorLogin);
            sendTemplateResponseSingleMentor(exchange, "admin-delete-mentor", mentor);
        } catch (SQLException e) {
            // Error occurred in database
            redirectToLocation(exchange, "/admin");
        } catch (NullPointerException e) {
            // TODO
            // Classroom not found
            redirectToLocation(exchange, "/admin/mentors");
        }
    }

    private void submitMentorEditPage(HttpExchange exchange) {
        try {
            Map<String, String> inputs = readFormData(exchange);
            String login = getMentorLogin(exchange);
            String firstName = inputs.get("firstname");
            String lastName = inputs.get("lastname");
            String password = inputs.get("password1");
            String password2 = inputs.get("password2");
            String email = inputs.get("email");
            String address = inputs.get("address");
            Mentor mentor = new MentorDAO().loadMentor(login);
            if (password == null && password2 == null) {
                mentor.setFirstName(firstName);
                mentor.setLastName(lastName);
                mentor.setEmail(email);
                mentor.setAddress(address);
                new MentorDAO().updateMentor(mentor);
                redirectToLocation(exchange, "/admin/mentors");
            } else if (password.equals(password2)) {
                try {
                    mentor.setPassword(new PasswordManager().generateStorngPasswordHash(password));
                    mentor.setFirstName(firstName);
                    mentor.setLastName(lastName);
                    mentor.setEmail(email);
                    new MentorDAO().updateMentor(mentor);
                    redirectToLocation(exchange, "/admin/mentors");
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    redirectToLocation(exchange, "/admin/index");
                }
            } else {
                redirectToLocation(exchange, "/admin/index");
            }
        }catch (SQLException e) {
            redirectToLocation(exchange, "/admin");
        }
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

        try {
            if (password.equals(repeatedPassword)) {
                String hashedPassword = new PasswordManager().generateStorngPasswordHash(password);
                new MentorDAO().createMentor(firstName, lastName, login, hashedPassword, classroomId, email, address);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            redirectToLocation(exchange, "/admin/index");
        }

        redirectToLocation(exchange, "/admin/mentors");
    }

    private void submitMentorDeletionPage(HttpExchange exchange) {
        String mentorLogin = getMentorLogin(exchange);
        new MentorDAO().deleteMentor(mentorLogin);
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

    private void sendTemplateResponseSingleMentor(HttpExchange exchange, String templateName, Mentor mentor) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("firstname", mentor.getFirstName());
        model.with("lastname", mentor.getLastName());
        model.with("email", mentor.getEmail());
        model.with("address", mentor.getAddress());
        model.with("login", mentor.getLogin());
        String response = template.render(model);
        sendResponse(exchange, response);
    }

    private String getMentorLogin(HttpExchange exchange) {
        try {
            return exchange.getRequestURI().toString().split("/")[4];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
