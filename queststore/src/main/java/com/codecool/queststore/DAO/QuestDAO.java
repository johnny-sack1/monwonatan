package com.codecool.queststore.DAO;

import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QuestDAO implements IStoreDAO {

    private Connection c = SQLQueryHandler.getInstance().getConnection();

//    @Override
//    public ResultSet loadEntity(int id) throws SQLException {
//        return null;
//    }

//    @Override
//    public ResultSet loadEntity(String name) throws SQLException {
//        return null;
//    }

    @Override
    public void createEntity(String name, String description, int value) throws SQLException {
        String query = "INSERT INTO quest (name, description, value) " +
                       "VALUES (?, ?, ?);";
        PreparedStatement statement = c.prepareStatement(query);

        statement.setString(1, name);
        statement.setString(2, description);
        statement.setInt(3, value);
        SQLQueryHandler.getInstance().executeQuery(statement.toString());

    }

    public void updateEntity (int id, List<String> newData) throws SQLException {
        String query = "UPDATE quest SET name = ?, description = ?, value = ? " +
                "WHERE id = ?";
        PreparedStatement statement = c.prepareStatement(query);

        statement.setString(1, newData.get(0));
        statement.setString(2, newData.get(1));
        statement.setInt(3, Integer.valueOf(newData.get(2)));
        statement.setInt(4, id);
        SQLQueryHandler.getInstance().executeQuery(statement.toString());
    }

    public void updateEntity(String name, List<String> newData) throws SQLException {
        String query = "UPDATE quest SET description = ?, value = ? " +
                "WHERE name = ?";
        PreparedStatement statement = c.prepareStatement(query);

        statement.setString(1, newData.get(0));
        statement.setInt(2, Integer.valueOf(newData.get(1)));
        statement.setString(3, name);
        SQLQueryHandler.getInstance().executeQuery(statement.toString());
    }
}
