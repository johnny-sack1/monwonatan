package com.codecool.queststore.DAO;

import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDAO {

    public abstract ResultSet loadUser(String login) throws SQLException;
    ResultSet executeQuery(String query) {
        return SQLQueryHandler.getInstance().executeQuery(query);
    }
}
