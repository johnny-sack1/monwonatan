package com.codecool.queststore.backend.webControllers.mentorController;

import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;

public class MentorController extends AbstractHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String cookieStr = exchange.getRequestHeaders().getFirst("Cookie");
        String sid = getSidFromCookieStr(cookieStr);
        String userLogin = getSessionIdContainer().getUserLogin(sid);

        if (!isLoggedIn(sid)) {
            redirectToLocation(exchange, "login");
        }

        String permissions = getPermissions(userLogin);

        if (!permissions.equals("mentor")) {
            switch (permissions) {
                case "student":
                    redirectToLocation(exchange, "student");
                case "admin":
                    redirectToLocation(exchange, "admin");
            }
        }

        String[] uriElements = exchange.getRequestURI().toString().split("/");

        if (uriElements.length == 2) {
            String name = "Jonatan Maczynski";
            sendTemplateResponseWithName(exchange, "mentor_index", name);
        }
        else {
            switch (uriElements[2]) {
                case "profile":
                    sendTemplateResponse(exchange, "mentor_profile");
                case "quest_manager":
                    sendTemplateResponse(exchange, "quest_manager");
                case "student_edition":
                    sendTemplateResponse(exchange, "student_edition");
                case "store":
                    sendTemplateResponse(exchange, "mentor_store");
            }
        }
    }

    private void sendTemplateResponseWithName(HttpExchange exchange, String templateName, String name) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.jtwig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("name", name);
        String response = template.render(model);
        sendResponse(exchange, response);
    }
}
