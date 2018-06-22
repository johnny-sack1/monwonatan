package com.codecool.queststore.backend.webControllers.mentorController;

import com.codecool.queststore.backend.dao.ArtifactDAO;
import com.codecool.queststore.backend.model.Artifact;
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

public class MentorStore extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        if (method.equalsIgnoreCase("GET")) {
            sendTemplateResponseWithTable(exchange, "mentorstore");
        } else if (method.equalsIgnoreCase("POST")) {
            try {
                updateArtifact(exchange);
            }
            catch (SQLException e) {
                e.printStackTrace();
                redirectToLocation(exchange, "/mentor/index");
            }
        }
    }

    private void sendTemplateResponseWithTable(HttpExchange exchange, String templateName) {
        List<Artifact> artifacts = new ArtifactDAO().loadAllArtifacts();
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("artifacts", artifacts);
        String response = template.render(model);
        sendResponse(exchange, response);
    }

    public void updateArtifact(HttpExchange exchange) throws SQLException{
        Map inputs = readArtifactData(exchange);

        int id = Integer.parseInt((String) inputs.get("submit"));
        String name = (String) inputs.get("name");
        String desc = (String) inputs.get("desc");
        int price = Integer.parseInt((String) inputs.get("price"));
        boolean isAvailableForGroups = Boolean.parseBoolean((String) inputs.get("groups"));

        Artifact artifact = new ArtifactDAO().loadArtifact(id);
        artifact.setName(name);
        artifact.setDescription(desc);
        artifact.setPrice(price);
        artifact.setAvailableForGroups(isAvailableForGroups);

        new ArtifactDAO().updateArtifact(artifact);
        redirectToLocation(exchange, "/mentor/store");
    }

    public Map<String, String> readArtifactData(HttpExchange exchange) {
        String formData = "";

        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            formData = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseArtifactData(formData);
    }

    public Map<String, String> parseArtifactData(String formData) {
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
