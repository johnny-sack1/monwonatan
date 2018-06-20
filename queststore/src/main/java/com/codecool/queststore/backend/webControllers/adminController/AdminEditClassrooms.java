package com.codecool.queststore.backend.webControllers.adminController;

import com.codecool.queststore.backend.dao.ClassroomDAO;
import com.codecool.queststore.backend.model.Classroom;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

public class AdminEditClassrooms extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String[] uriParts = exchange.getRequestURI().toString().split("/");

        if (uriParts.length == 3) {
            // "/admin/classroom"
            sendTemplateResponseClassrooms(exchange, "adminclassroom");
        } else {

            // "/admin/classroom/(action)"
            int ACTION_I = 3;
            String action = uriParts[ACTION_I];

            if (action.equals("edit")) {
                editClassroom(exchange);
            } else if (action.equals("delete")) {
                deleteClassroom(exchange);
            } else {
                redirectToLocation(exchange, "/login");
            }
        }
    }

    private void editClassroom(HttpExchange exchange) {
        String method = exchange.getRequestMethod();

        if(method.equalsIgnoreCase("GET")) {
            sendClassroomEditPage(exchange);
        } else if (method.equalsIgnoreCase("POST")) {
            submitClassroomEditPage(exchange);
        } else {
            redirectToLocation(exchange, "/admin/index");
        }

    }

    private void deleteClassroom(HttpExchange exchange) {
        String method = exchange.getRequestMethod();

        if(method.equalsIgnoreCase("GET")) {
            sendClassroomDeletePage(exchange);
        } else if (method.equalsIgnoreCase("POST")) {
            submitClassroomDeletePage(exchange);
        } else {
            redirectToLocation(exchange, "/admin/index");
        }

    }

    private void sendClassroomEditPage(HttpExchange exchange) {
        int classroomId = getClassId(exchange);
        try {
            Classroom classroom = new ClassroomDAO().loadClassroom(classroomId);
            sendTemplateResponseClass(exchange, "adminclassedit", classroom);
        } catch (SQLException e) {
            // Error occurred in database
            redirectToLocation(exchange, "/admin");
        } catch (NullPointerException e) {
            // TODO
            // Classroom not found
            redirectToLocation(exchange, "/admin/classroom");
        } catch (NumberFormatException | InputMismatchException e) {
            // TODO
            // Invalid input in browser (URI)
            redirectToLocation(exchange, "/admin/classroom");
        }
    }

    private void sendClassroomDeletePage(HttpExchange exchange) {
        int classroomId = getClassId(exchange);
        try {
            Classroom classroom = new ClassroomDAO().loadClassroom(classroomId);
            sendTemplateResponseClass(exchange, "adminclassdelete", classroom);
        } catch (SQLException e) {
            // Error occurred in database
            redirectToLocation(exchange, "/admin");
        } catch (NullPointerException e) {
            // TODO
            // Classroom not found
            redirectToLocation(exchange, "/admin/classroom");
        } catch (NumberFormatException | InputMismatchException e) {
            // TODO
            // Invalid input in browser (URI)
            redirectToLocation(exchange, "/admin/classroom");
        }
    }

    private void submitClassroomEditPage(HttpExchange exchange) {
        try {
            Map<String, String> inputs = readFormData(exchange);
            int id = getClassId(exchange);
            String name = inputs.get("name");
            String description = inputs.get("description");
            ClassroomDAO dao = new ClassroomDAO();
            Classroom classroom = dao.loadClassroom(id);
            classroom.setName(name);
            classroom.setDescription(description);
            dao.updateClassroom(classroom);
            redirectToLocation(exchange, "/admin/classroom");
        } catch (SQLException e) {
            redirectToLocation(exchange, "/admin/index");
        }
    }

    private void submitClassroomDeletePage(HttpExchange exchange) {
        int id = getClassId(exchange);
        ClassroomDAO dao = new ClassroomDAO();
        dao.deleteClassroom(id);
        redirectToLocation(exchange, "/admin/classroom");
    }

    private void sendTemplateResponseClassrooms(HttpExchange exchange, String templateName) {
        List<Classroom> classrooms = new ClassroomDAO().loadAllClassrooms();
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("classrooms", classrooms);
        String response = template.render(model);
        sendResponse(exchange, response);
    }

    private void sendTemplateResponseClass(HttpExchange exchange, String templateName, Classroom classroom) {

        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("id", classroom.getID());
        model.with("name", classroom.getName());
        model.with("description", classroom.getDescription());
        String response = template.render(model);
        sendResponse(exchange, response);
    }

    private int getClassId(HttpExchange exchange) {
        try {
            int id = Integer.parseInt(exchange.getRequestURI().toString().split("/")[4]);
            return id;
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }
}
