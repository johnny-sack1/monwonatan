package com.codecool.queststore.backend.dao;

import com.codecool.queststore.backend.databaseConnection.SQLQueryHandler;
import com.codecool.queststore.backend.model.ExpLvl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ExpLevelDAO {

    public boolean createExpLevel(String description, int requiredCoins) {

        try {
            Connection c = SQLQueryHandler.getInstance().getConnection();
            String query = "INSERT INTO Experience_Level (description, required_coins) " +
                    "VALUES (?, ?);";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, description);
            statement.setInt(2, requiredCoins);
            SQLQueryHandler.getInstance().executeQuery(statement.toString());

            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean updateExpLevel(int expLevelID, String description, int requiredCoins) {

        try {
            Connection c = SQLQueryHandler.getInstance().getConnection();
            String query = "UPDATE Experience_Level " +
                    "SET description =?, required_coins = ? " +
                    "WHERE experience_level_id = ?;";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setString(1, description);
            statement.setInt(2, requiredCoins);
            statement.setInt(3, expLevelID);
            SQLQueryHandler.getInstance().executeQuery(statement.toString());

            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteExpLevel(int expLevelID) {

        try {
            Connection c = SQLQueryHandler.getInstance().getConnection();
            String query = "DELETE FROM Experience_Level WHERE experience_level_id = ?;";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setInt(1, expLevelID);
            SQLQueryHandler.getInstance().executeQuery(statement.toString());

            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public ExpLvl loadExpLevel(int expLevelID) {

        try {
            Connection c = SQLQueryHandler.getInstance().getConnection();
            String query = "SELECT * " +
                    "FROM Experience_Level WHERE experience_level_id = ?;";

            PreparedStatement statement = c.prepareStatement(query);

            statement.setInt(1, expLevelID);
            ResultSet resultSet = SQLQueryHandler.getInstance().executeQuery(statement.toString());

            String expLevelDescription = "";
            int expLevelRequiredCoins = 0;

            while (resultSet.next()) {
                expLevelDescription = resultSet.getString("description");
                expLevelRequiredCoins = resultSet.getInt("required_coins");
            }
            return new ExpLvl(expLevelID ,expLevelRequiredCoins, expLevelDescription);
        }
        catch (SQLException e) {
            return null;
        }
    }

    public Map<String, Integer> loadAllExpLevel() {

        Map<String, Integer> expLevels = new HashMap<>();

        try {
            String query = "SELECT * FROM Experience_Level;";
            ResultSet resultSet = SQLQueryHandler.getInstance().executeQuery(query);

            while (resultSet.next()) {
                String expLevelDescription = resultSet.getString("description");
                Integer requiredCoins = resultSet.getInt("required_coins");
                expLevels.put(expLevelDescription, requiredCoins);
            }
            return expLevels;
        }
        catch (SQLException e) {
            return null;
        }
    }
}
