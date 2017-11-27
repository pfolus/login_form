package com.codecool.krk.model;

public class UserModel {

    private String name;
    private String surname;
    private String login;
    private String password;
    private Integer id;

    public UserModel(Integer id, String name, String surname,
                     String login, String password) {

        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}