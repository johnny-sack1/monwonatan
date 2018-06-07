package com.codecool.queststore.backend.dao;

import com.codecool.queststore.backend.databaseConnection.SQLQueryHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    public String getPasswordBy(String login) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();
        String query = "SELECT password FROM user_type " +
                       "WHERE login = ?;";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, login);
        ResultSet resultSet = SQLQueryHandler.getInstance().executeQuery(statement.toString());
        resultSet.next();

        return resultSet.getString(1);
    }
    public String getTypeBy(String login) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();
        String query = "SELECT type FROM user_type " +
                       "WHERE login = ?;";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, login);
        ResultSet resultSet = SQLQueryHandler.getInstance().executeQuery(statement.toString());
        resultSet.next();

        return resultSet.getString(1);
    }
}
