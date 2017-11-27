package com.codecool.krk.controller;

import com.codecool.krk.dao.CookiesDao;
import com.codecool.krk.dao.UsersDao;
import com.codecool.krk.model.UserModel;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class UserHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        CookiesController cookiesController = new CookiesController();
        HttpCookie cookie = cookiesController.getCookie(httpExchange);
        String userType = "";
        UserModel user = null;
        Integer userId = null;

        if (cookie != null) {
            CookiesDao cookiesDao = new CookiesDao();
            userId = cookiesDao.getUserIdBySessionId(cookie.getValue());

            UsersDao usersDao = new UsersDao();
            user = usersDao.getUserById(userId);
            userType = UsersDao.getUserType(userId);

        }

        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();

        if (path.equals("/user")) {

            index(httpExchange, userId);

        } else if (path.equals("/user/logout")) {

            cookiesController.removeCookie(httpExchange);
            redirect(httpExchange, "/");

        }
    }


    private void redirect(HttpExchange httpExchange, String location) throws IOException {
        httpExchange.getResponseHeaders().set("Location", location);
        httpExchange.sendResponseHeaders(302, -1);
    }

    private void index(HttpExchange httpExchange, Integer userId) throws IOException {
        JtwigModel model = JtwigModel.newModel();
        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/userpage.html.twig");

        UsersDao usersDao = new UsersDao();
        String name = usersDao.getUserById(userId).getName();
        String surname = usersDao.getUserById(userId).getSurname();

        model.with("name", name);
        model.with("surname", surname);

        String response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
        }
}





