package com.codecool.krk.dao;

import com.codecool.krk.controller.DatabaseConnection;
import com.codecool.krk.model.UserModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsersDao {

    protected ArrayList<UserModel> itemsList = new ArrayList<>();

    public UsersDao() {
        readFromDatabase();
    }

    private void readFromDatabase(){

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users ;");

            while (resultSet.next()) {
                Integer user_id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");

                this.itemsList.add(
                        new UserModel(user_id, name, surname, login,
                                password));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public UserModel getUserById(Integer studentId){

        UserModel userModel = null;
        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM users WHERE id = " + studentId + ";");

            while (resultSet.next()) {

                Integer user_id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");

                return new UserModel(user_id, name, surname, login,
                        password);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return userModel;
    }


    public static String getUserType(Integer id) {
        String type = null;

        try {
            Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT type FROM users WHERE id = " + id + ";");

            if (resultSet.next()) {
                type = resultSet.getString("type");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return type;
    }
}
