package com.codecool.krk.dao;

import com.codecool.krk.controller.DatabaseConnection;
import com.codecool.krk.model.PendingUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginDao {

    public PendingUser login(String login, String password) {
        PendingUser pendingUser = null;

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT type, id FROM users WHERE login = '" + login + "' AND password = '" + password + "';");

            if (resultSet.next()) {
                pendingUser = new PendingUser(resultSet.getInt("id"), resultSet.getString("type"));
            } else {
                throw new SQLException();
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("\nWrong data! Couldn't login.");
        }

        return pendingUser;
    }
}
