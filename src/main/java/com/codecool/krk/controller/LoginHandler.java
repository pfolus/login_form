package com.codecool.krk.controller;

import com.codecool.krk.dao.CookiesDao;
import com.codecool.krk.dao.LoginDao;
import com.codecool.krk.dao.UsersDao;
import com.codecool.krk.model.PendingUser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        CookiesController cookiesController = new CookiesController();
        HttpCookie cookie = cookiesController.getCookie(httpExchange);

        if (cookie != null) {
            redirect(httpExchange);
        }

        String method = httpExchange.getRequestMethod();

        if (method.equals("POST")) {
            PendingUser pendingUser = null;
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            pendingUser = findUser(formData);

            if (pendingUser != null) {
                cookiesController.createCookie(httpExchange, pendingUser.getId());
                redirect(httpExchange);
            }
        }



        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/index.html.twig");
        JtwigModel model = JtwigModel.newModel();
        String response = template.render(model);

        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private PendingUser findUser(String formData) throws UnsupportedEncodingException {
        LoginDao loginDao = new LoginDao();
        URLDecoder decoder = new URLDecoder();
        String login;
        String password;
        String[] pairs = formData.split("&");

        try {
            login = decoder.decode(pairs[0].split("=")[1], "UTF-8");
            password = decoder.decode(pairs[1].split("=")[1], "UTF-8");
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

        return loginDao.login(login, password);
    }

    private void redirect(HttpExchange httpExchange) throws IOException {
        String location = "/user";

        httpExchange.getResponseHeaders().set("Location", location);
        httpExchange.sendResponseHeaders(302,-1);
    }
}
