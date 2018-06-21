package com.codecool.queststore.backend.webControllers.mentorController;

import com.codecool.queststore.backend.dao.ArtifactDAO;
import com.codecool.queststore.backend.model.Artifact;
import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;

public class MentorStore extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        sendTemplateResponseWithTable(exchange, "mentorstore");
    }

    private void sendTemplateResponseWithTable(HttpExchange exchange, String templateName) {
        List<Artifact> artifacts = new ArtifactDAO().loadAllArtifacts();
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("artifacts", artifacts);
        String response = template.render(model);
        sendResponse(exchange, response);
    }
}
