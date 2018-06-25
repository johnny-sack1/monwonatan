package com.codecool.queststore.backend.dao;

import com.codecool.queststore.backend.databaseConnection.SQLQueryHandler;
import com.codecool.queststore.backend.model.Artifact;
import com.codecool.queststore.backend.model.Backpack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BackpackDAO {

    public Backpack loadBackpack(String studentLogin) {
        Backpack backpack = new Backpack(studentLogin);
        ResultSet artifacts = getArtifactsOfStudent(studentLogin);
        try {
            while (artifacts.next()) {
                int artifactId = artifacts.getInt("artifact_id");
                String status = artifacts.getString("status");
                boolean availableForGroups = artifacts.getBoolean("available_for_groups");
                String name = artifacts.getString("name");
                String description = artifacts.getString("description");
                int price = artifacts.getInt("price");
                backpack.addToBackpack(new Artifact(artifactId, availableForGroups, name, description, price), status);
            }
        } catch (SQLException e) {
            return null;
        }
        return backpack;
    }

    private ResultSet getArtifactsOfStudent(String studentLogin) {
        String query = "SELECT * FROM backpack LEFT JOIN artifact ON (backpack.artifact_id = artifact.artifact_id) " +
                "WHERE backpack.student_login = ?";
        Connection c = SQLQueryHandler.getInstance().getConnection();

        try {
            PreparedStatement backpackStatement = c.prepareStatement(query);
            backpackStatement.setString(1, studentLogin);
            query = backpackStatement.toString();
            return SQLQueryHandler.getInstance().executeQuery(query);
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean updateBackpack(Backpack backpack) {
        if (removeBackpack(backpack))
        return addBackpack(backpack);
        return false;
    }

    private boolean removeBackpack(Backpack backpack) {
        String removeOldBackpackQuery = "DELETE FROM backpack WHERE student_login = ?;";
        Connection c = SQLQueryHandler.getInstance().getConnection();

        try {
            PreparedStatement backpackStatement = c.prepareStatement(removeOldBackpackQuery);
            backpackStatement.setString(1, backpack.getStudentLogin());
            removeOldBackpackQuery = backpackStatement.toString();
            SQLQueryHandler.getInstance().executeQuery(removeOldBackpackQuery);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    private boolean addBackpack(Backpack backpack) {
        String addBackpackQuery = "INSERT INTO backpack (student_login, artifact_id, status) VALUES (?, ?, ?);";
        Connection c = SQLQueryHandler.getInstance().getConnection();

        HashMap<Artifact, String> backpackContent = backpack.getStudentBackpack();
        try {
            for (Map.Entry<Artifact, String> entry : backpackContent.entrySet())
            {
                PreparedStatement backpackStatement = c.prepareStatement(addBackpackQuery);
                backpackStatement.setString(1, backpack.getStudentLogin());
                backpackStatement.setInt(2, entry.getKey().getArtifactId());
                backpackStatement.setString(3, (String) entry.getValue());
                SQLQueryHandler.getInstance().executeQuery(backpackStatement.toString());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
