package com.codecool.queststore.DAO;

import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;

import java.sql.ResultSet;

public abstract class UserDAO {

    public abstract ResultSet loadUser(String login);
    public void executeQuery(String query) {
        SQLQueryHandler.getInstance().executeQuery(query);
    }
}
