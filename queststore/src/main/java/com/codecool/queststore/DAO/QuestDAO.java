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

//    @Override
//    public void updateEntity throws SQLException (int id, List<String> newData) {
//
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

//    @Override
//    public void updateEntity(String name, List<String> newData) throws SQLException {
//
//    }
}
