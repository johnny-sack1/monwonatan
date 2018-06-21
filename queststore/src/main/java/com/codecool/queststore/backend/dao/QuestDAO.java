package com.codecool.queststore.backend.dao;

import com.codecool.queststore.backend.databaseConnection.SQLQueryHandler;
import com.codecool.queststore.backend.model.Quest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestDAO {

    public boolean createQuest(String name, String description, int value) {
        try {
            Connection c = SQLQueryHandler.getInstance().getConnection();
            String query = "INSERT INTO quest (name, description, value) " +
                    "VALUES (?, ?, ?);";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setString(1, name);
            statement.setString(2, description);
            statement.setInt(3, value);
            SQLQueryHandler.getInstance().executeQuery(statement.toString());
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public Quest loadQuest(int id) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();

        String query = "SELECT * FROM quest WHERE quest_id = ?";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = SQLQueryHandler.getInstance().executeQuery(statement.toString());
        resultSet.next();
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        int value = resultSet.getInt("value");

        return new Quest(id, name, description, value);
    }

    public Quest loadQuest(String name) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();

        String query = "SELECT * FROM quest WHERE name = ?";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, name);
        ResultSet resultSet = SQLQueryHandler.getInstance().executeQuery(statement.toString());
        resultSet.next();
        int id = resultSet.getInt("quest_id");
        String description = resultSet.getString("description");
        int value = resultSet.getInt("value");

        return new Quest(id, name, description, value);
    }

    public boolean updateQuest(Quest quest) {
        int id = quest.getId();
        String name = quest.getName();
        String description = quest.getDescription();
        int value = quest.getValue();

        return updateQuest(id, name, description, value);
    }

    public boolean updateQuest(int id, String name, String description, int value) {
        try {
            Connection c = SQLQueryHandler.getInstance().getConnection();
            String query = "UPDATE quest SET name = ?, description = ?, value = ? " +
                    "WHERE quest_id = ?";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setString(1, name);
            statement.setString(2,description);
            statement.setInt(3, value);
            statement.setInt(4, id);
            SQLQueryHandler.getInstance().executeQuery(statement.toString());

            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public List<Quest> loadAllQuests() {
        List<Quest> allQuests = new ArrayList<>();

        String query = "SELECT quest_id FROM quest;";
        ResultSet ids = SQLQueryHandler.getInstance().executeQuery(query);
        try {
            while (ids.next()) {
                allQuests.add(loadQuest(ids.getInt("quest_id")));
            }
            return allQuests;
        }
        catch (SQLException e) {
            return null;
        }
    }
}
