package com.codecool.queststore.DAO;

import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;
import com.codecool.queststore.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDAO {

    public abstract ResultSet loadUser(String login) throws SQLException;
    void executeQuery(String query) {
        SQLQueryHandler.getInstance().executeQuery(query);
    }
    public abstract void updateUser(User user) throws SQLException;
}
