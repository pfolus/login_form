package com.codecool.krk.model;

public class PendingUser {

    private Integer id;
    private String type;

    public PendingUser(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

}
