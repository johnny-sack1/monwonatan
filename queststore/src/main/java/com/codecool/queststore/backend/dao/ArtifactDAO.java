package com.codecool.queststore.backend.dao;

import com.codecool.queststore.backend.databaseConnection.SQLQueryHandler;
import com.codecool.queststore.backend.model.Artifact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDAO {

    public boolean createArtifact(boolean availableForGroups, String name, String description, int price) {
        try {
            Connection c = SQLQueryHandler.getInstance().getConnection();
            String query = "INSERT INTO artifact (available_for_groups, name, description, price) " +
                    "VALUES (?, ?, ?, ?);";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setBoolean(1, availableForGroups);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setInt(4, price);
            SQLQueryHandler.getInstance().executeQuery(statement.toString());
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public Artifact loadArtifact(int id) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();

        String query = "SELECT * FROM artifact WHERE artifact_id = ?";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = SQLQueryHandler.getInstance().executeQuery(statement.toString());
        resultSet.next();
        boolean availableForGroups = resultSet.getBoolean("available_for_groups");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        int value = resultSet.getInt("price");

        return new Artifact(id, availableForGroups, name, description, value);
    }

    public Artifact loadArtifact(String artifactName) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();

        String query = "SELECT * FROM artifact WHERE name = ?";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, artifactName);
        ResultSet resultSet = SQLQueryHandler.getInstance().executeQuery(statement.toString());
        resultSet.next();
        boolean availableForGroups = resultSet.getBoolean("available_for_groups");
        int id = resultSet.getInt("artifact_id");
        String description = resultSet.getString("description");
        int value = resultSet.getInt("price");

        return new Artifact(id, availableForGroups, artifactName, description, value);
    }

    public boolean updateArtifact(Artifact artifact) {
        int id = artifact.getArtifactId();
        boolean availableForGroups = artifact.isAvailableForGroups();
        String name = artifact.getName();
        String description = artifact.getDescription();
        int price = artifact.getPrice();

        return updateArtifact(id, availableForGroups, name, description, price);
    }

    public boolean updateArtifact(int id, boolean availableForGroups, String name, String description, int value) {
        try {
            Connection c = SQLQueryHandler.getInstance().getConnection();
            String query = "UPDATE artifact SET available_for_groups = ?, name = ?, description = ?, value = ? " +
                    "WHERE artifact_id = ?";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setBoolean(1, availableForGroups);
            statement.setString(2, name);
            statement.setString(3,description);
            statement.setInt(4, value);
            statement.setInt(5, id);
            SQLQueryHandler.getInstance().executeQuery(statement.toString());

            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public List<Artifact> loadAllArtifacts() {
        List<Artifact> allArtifacts = new ArrayList<>();

        String query = "SELECT artifact_id FROM artifact;";
        ResultSet ids = SQLQueryHandler.getInstance().executeQuery(query);
        try {
            while (ids.next()) {
                allArtifacts.add(loadArtifact(ids.getInt("artifact_id")));
            }
            return allArtifacts;
        }
        catch (SQLException e) {
            return null;
        }
    }
}
