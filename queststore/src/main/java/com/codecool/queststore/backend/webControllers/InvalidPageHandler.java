package com.codecool.queststore.backend.webControllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class InvalidPageHandler extends AbstractHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        redirectToLocation(exchange,"/login");
    }
}
