package com.codecool.queststore.backend.webControllers.mentorController;

import com.codecool.queststore.backend.dao.ClassroomDAO;
import com.codecool.queststore.backend.dao.QuestDAO;
import com.codecool.queststore.backend.dao.StudentDAO;
import com.codecool.queststore.backend.model.Classroom;
import com.codecool.queststore.backend.model.Quest;
import com.codecool.queststore.backend.model.Student;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentEdition extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        if (method.equalsIgnoreCase("GET")) {
            sendTemplateResponseWithTable(exchange, "studentedition");
        } else if (method.equalsIgnoreCase("POST")) {
            try {
                updateStudent(exchange);
            }
            catch (SQLException e) {
                e.printStackTrace();
                redirectToLocation(exchange, "/mentor/index");
            }
        }
    }

    private void sendTemplateResponseWithTable(HttpExchange exchange, String templateName) {
        List<Student> students = new StudentDAO().loadAllStudents();
        List<Classroom> classrooms = new ClassroomDAO().loadAllClassrooms();
        List<Quest> quests = new QuestDAO().loadAllQuests();
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("students", students);
        model.with("classrooms", classrooms);
        model.with("quests", quests);
        String response = template.render(model);
        sendResponse(exchange, response);
    }

    public void updateStudent(HttpExchange exchange) throws SQLException{
        Map inputs = readStudentData(exchange);

        String login = (String) inputs.get("submit");
        String firstName = (String) inputs.get("firstname");
        String lastName = (String) inputs.get("lastname");
        int classroomID = Integer.parseInt((String) inputs.get("classroom"));

        Student student = new StudentDAO().loadStudent(login);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setClassRoomID(classroomID);

        new StudentDAO().updateStudent(student);
        redirectToLocation(exchange, "/mentor/student_edition");
    }

    public Map<String, String> readStudentData(HttpExchange exchange) {
        String formData = "";

        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            formData = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseStudentData(formData);
    }

    public Map<String, String> parseStudentData(String formData) {
        Map<String, String> inputs = new HashMap<>();
        String key;
        String value;
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            key = keyValue[0];
            try {
                value = URLDecoder.decode(keyValue[1], "UTF-8");
                inputs.put(key, value);

            } catch (UnsupportedEncodingException | IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return inputs;
    }
}
