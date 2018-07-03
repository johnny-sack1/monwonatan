package com.codecool.queststore.backend.webControllers.mentorController;

import com.codecool.queststore.backend.dao.QuestDAO;
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

        if (!isLoggedIn(sid)) {
            redirectToLocation(exchange, "/login");
        }

        String permissions = getPermissions(sid);

        switch (permissions) {
            case "student":
                redirectToLocation(exchange, "student");
                break;
            case "admin":
                redirectToLocation(exchange, "admin");
                break;
            case "mentor":
                mentorRedirect(exchange);
                break;
            default:
                redirectToLocation(exchange, "/logout");
        }
    }

    private void mentorRedirect(HttpExchange exchange) {
        String[] uriElements = exchange.getRequestURI().toString().split("/");

        if (uriElements.length == 2) {
            new MentorIndex().handle(exchange);
        }
        else {
            switch (uriElements[2]) {
                case "index":
                    new MentorIndex().handle(exchange);
                    break;
                case "profile":
                    new MentorProfile().handle(exchange);
                    break;
                case "quest_manager":
                    new QuestManager(new QuestDAO()).handle(exchange);
                    break;
                case "student_edition":
                    new StudentEdition().handle(exchange);
                    break;
                case "store":
                    new MentorStore().handle(exchange);
                    break;
                default:
                    redirectToLocation(exchange, "/mentor/index");
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
