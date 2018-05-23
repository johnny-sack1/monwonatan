package com.codecool.queststore.DAO;

import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArtifactDAO implements IStoreDAO {

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
    public void createEntity(String name, String description, int price) throws SQLException {
        String query = "INSERT INTO artifact (name, description, price) " +
                "VALUES (?, ?, ?);";
        PreparedStatement statement = c.prepareStatement(query);

        statement.setString(1, name);
        statement.setString(2, description);
        statement.setInt(3, price);
        SQLQueryHandler.getInstance().executeQuery(statement.toString());
    }

    public void updateEntity(int id, List<String> newData) throws SQLException {

        String query = "UPDATE artifact SET name = ?, description = ?, price = ? " +
                "WHERE id = ?";
        PreparedStatement statement = c.prepareStatement(query);

        statement.setString(1, newData.get(0));
        statement.setString(2, newData.get(1));
        statement.setString(3, newData.get(2));
        statement.setInt(4, id);
        SQLQueryHandler.getInstance().executeQuery(statement.toString());
    }

//    @Override
//    public void updateEntity(String name, List<String> newData) throws SQLException {
//
//    }
}
