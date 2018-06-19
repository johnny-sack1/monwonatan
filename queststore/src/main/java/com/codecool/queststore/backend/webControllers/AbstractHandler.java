package com.codecool.queststore.backend.webControllers;

import com.codecool.queststore.backend.session.SessionIdContainer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractHandler {
    private SessionIdContainer sessionIdContainer;

    public AbstractHandler() {
        this.sessionIdContainer = SessionIdContainer.getSessionIdContainer();
    }

    public SessionIdContainer getSessionIdContainer() {
        return sessionIdContainer;
    }

    public void redirectToLocation(HttpExchange exchange, String location) {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.add("Location", location);
        try {
            exchange.sendResponseHeaders(302, -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        exchange.close();
    }

    public void sendResponse(HttpExchange exchange, String response) {
        byte[] bytes = response.getBytes();
        try {
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendTemplateResponse(HttpExchange exchange, String templateName) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.twig", templateName));
        JtwigModel model = JtwigModel.newModel();
        String response = template.render(model);
        sendResponse(exchange, response);
    }
}
