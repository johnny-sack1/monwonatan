package com.codecool.queststore.backend.webControllers.mentorController;

import com.codecool.queststore.backend.dao.QuestDAO;
import com.codecool.queststore.backend.model.Quest;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.sql.SQLException;
import java.util.List;

public class QuestManager extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        sendTemplateResponseWithTable(exchange, "questmanager");
    }

    private void sendTemplateResponseWithTable(HttpExchange exchange, String templateName) {
        List<Quest> quests = new QuestDAO().loadAllQuests();
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("quests", quests);
        String response = template.render(model);
        sendResponse(exchange, response);
    }
}
