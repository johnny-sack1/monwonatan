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
            String query = "INSERT INTO Experience_Level (description, requiredCoins) " +
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
}
