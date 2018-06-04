package com.codecool.queststore.backend.dao;

import com.codecool.queststore.backend.databaseConnection.SQLQueryHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ClassroomDAO {
    public void createClassroom(String name, String description) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();
        String query = "INSERT INTO classroom (name, description) " +
                       "VALUES (?, ?);";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, name);
        statement.setString(2, description);
        SQLQueryHandler.getInstance().executeQuery(statement.toString());
    }

    public void updateClassroom(int id, List<String> newData) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();
        String query = "UPDATE classroom " +
                       "SET name = ?, description = ? " +
                       "WHERE classroom_id = ?;";
        PreparedStatement statement = c.prepareStatement(query);

        statement.setString(1, newData.get(0));
        statement.setString(2, newData.get(1));
        statement.setInt(3, id);
        SQLQueryHandler.getInstance().executeQuery(statement.toString());
    }
}
