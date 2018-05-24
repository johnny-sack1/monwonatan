package com.codecool.queststore.DAO;

import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
}
