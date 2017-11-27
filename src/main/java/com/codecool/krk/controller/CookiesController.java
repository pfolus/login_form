package com.codecool.krk.controller;

import com.codecool.krk.dao.CookiesDao;
import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.util.UUID;

public class CookiesController {

    public CookiesController(){}

    public void createCookie(HttpExchange httpExchange, Integer userId) {
        String sessionId = createSessionId();

        httpExchange.getResponseHeaders().add("User-agent", "HTTPTool/1.0");
        httpExchange.getResponseHeaders().add("Set-cookie", "sessionId=" + sessionId + "; Max-Age=360");

        CookiesDao cookiesDao = new CookiesDao();
        cookiesDao.addCookieToDatabase(sessionId, userId);
    }

    private String createSessionId() {
        return UUID.randomUUID().toString();
    }

    public void removeCookie(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);

        cookieStr = "sessionId=\"\"; max-age=0; path=/";

        httpExchange.getResponseHeaders().add("Set-Cookie",cookieStr);

        CookiesDao cookiesDao = new CookiesDao();
        cookiesDao.removeCookieFromDatabase(cookie.getValue());
    }

    public HttpCookie getCookie(HttpExchange httpExchange) {
        HttpCookie cookie = null;

        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        if (cookieStr != null) {
            cookie = HttpCookie.parse(cookieStr).get(0);
        }

        return cookie;
    }

}
