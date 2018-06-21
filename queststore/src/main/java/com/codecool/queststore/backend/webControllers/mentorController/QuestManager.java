package com.codecool.queststore.backend.webControllers.mentorController;

import com.codecool.queststore.backend.dao.QuestDAO;
import com.codecool.queststore.backend.model.Quest;
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

public class QuestManager extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        if (method.equalsIgnoreCase("GET")) {
            sendTemplateResponseWithTable(exchange, "questmanager");
        } else if (method.equalsIgnoreCase("POST")) {
            try {
                updateQuest(exchange);
            }
            catch (SQLException e) {
                redirectToLocation(exchange, "/mentor/index");
            }
        }
    }

    private void sendTemplateResponseWithTable(HttpExchange exchange, String templateName) {
        List<Quest> quests = new QuestDAO().loadAllQuests();
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("quests", quests);
        String response = template.render(model);
        sendResponse(exchange, response);
    }

    public void updateQuest(HttpExchange exchange) throws SQLException{
        Map inputs = readQuestData(exchange);

        String oldName = (String) inputs.get("submit");
        String newName = (String) inputs.get("name");
        String description = (String) inputs.get("description");
        int value = Integer.parseInt((String) inputs.get("value"));

        Quest quest = new QuestDAO().loadQuest(oldName);
        quest.setName(newName);
        quest.setDescription(description);
        quest.setValue(value);

        new QuestDAO().updateQuest(quest);
        redirectToLocation(exchange, "/mentor/quest_manager");
    }

    public Map<String, String> readQuestData(HttpExchange exchange) {
        String formData = "";

        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            formData = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseQuestData(formData);
    }

    public Map<String, String> parseQuestData(String formData) {
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

