package com.codecool.queststore.backend.webControllers.loginController;

import com.codecool.queststore.backend.webControllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Logout extends AbstractHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) {

        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        String sid = getSidFromCookieStr(cookieStr);

        getSessionIdContainer().remove(sid);
        httpExchange.getResponseHeaders().add("Set-Cookie", cookieStr + ";Max-Age=0");

        redirectToLocation(httpExchange,"/login");

    }
}
