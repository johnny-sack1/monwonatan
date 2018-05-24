package com.codecool.queststore.DAO;

import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    public ResultSet getPasswordBy(String login) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();
        String query = "SELECT password FROM user_type " +
                       "WHERE login = ?;";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, login);

        return SQLQueryHandler.getInstance().executeQuery(statement.toString());
    }
    public ResultSet getTypeBy(String login) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();
        String query = "SELECT type FROM user_type " +
                       "WHERE login = ?;";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, login);

        return SQLQueryHandler.getInstance().executeQuery(statement.toString());
    }
}
