package com.codecool.queststore.DAO;

import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;
import com.codecool.queststore.Model.Quest;
import com.codecool.queststore.Model.Student;

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

    public Quest loadQuest(String name) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();

        String query = "SELECT * FROM quest WHERE name = ?";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, name);
        ResultSet resultSet = SQLQueryHandler.getInstance().executeQuery(statement.toString());
        resultSet.next();
        String description = resultSet.getString("description");
        int value = resultSet.getInt("value");
        boolean isAvailableForGroups = resultSet.getBoolean("available_for_groups");

        return new Quest(name, description, value, isAvailableForGroups);
    }

    public boolean updateQuest(Quest quest) {
        String name = quest.getName();
        String description = quest.getDescription();
        int value = quest.getValue();
        boolean isAvailableForGroups = quest.isAvailableForGroups();

        return updateQuest(name, description, value, isAvailableForGroups);
    }

    public boolean updateQuest(String name, String description, int value, boolean isAvailableForGroups) {
        try {
            Connection c = SQLQueryHandler.getInstance().getConnection();
            String query = "UPDATE quest SET description = ?, value = ?, available_for_groups = ? " +
                    "WHERE name = ?";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setString(1,description);
            statement.setInt(2, value);
            statement.setBoolean(3, isAvailableForGroups);
            SQLQueryHandler.getInstance().executeQuery(statement.toString());

            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public List<Quest> loadAllQuests() {
        List<Quest> allQuests = new ArrayList<>();

        String query = "SELECT name FROM quest;";
        ResultSet names = SQLQueryHandler.getInstance().executeQuery(query);
        try {
            while (names.next()) {
                allQuests.add(loadQuest(names.getString("name")));
            }
            return allQuests;
        }
        catch (SQLException e) {
            return null;
        }
    }
}
