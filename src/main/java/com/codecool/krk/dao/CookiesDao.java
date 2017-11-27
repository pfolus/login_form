package com.codecool.krk.dao;

import com.codecool.krk.controller.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CookiesDao {

    public CookiesDao(){}

    public void addCookieToDatabase(String sessionId, Integer userId) {
        Statement stmt;

        try {
            Connection c = DatabaseConnection.getConnection();
            c.setAutoCommit(false);

            stmt = c.createStatement();

            stmt.executeUpdate("INSERT INTO cookies (session_id, user_id)"
                    + " VALUES ('" + sessionId + "', " + userId + ");");

            stmt.close();
            c.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void removeCookieFromDatabase(String sessionId) {
        Statement stmt;

        try {
            Connection c = DatabaseConnection.getConnection();
            c.setAutoCommit(false);

            stmt = c.createStatement();

            stmt.executeUpdate("DELETE FROM cookies WHERE session_id = '" + sessionId + "';");
            stmt.close();
            c.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public Integer getUserIdBySessionId(String sessionId) {
        Integer userId = null;

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT user_id FROM cookies WHERE session_id = '" + sessionId + "';");

            while (resultSet.next()) {
                userId = resultSet.getInt("user_id");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return userId;
    }

}
