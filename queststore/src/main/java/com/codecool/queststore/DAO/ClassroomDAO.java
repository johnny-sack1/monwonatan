package com.codecool.queststore.DAO;

import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ClassroomDAO {

    public boolean createClassroom(String name, String description) {

        try {
            Connection c = SQLQueryHandler.getInstance().getConnection();
            String query = "INSERT INTO classroom (name, description) " +
                    "VALUES (?, ?);";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, description);
            SQLQueryHandler.getInstance().executeQuery(statement.toString());

            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean updateClassroom(int id, List<String> newData) {

        try {
            Connection c = SQLQueryHandler.getInstance().getConnection();
            String query = "UPDATE classroom " +
                    "SET name = ?, description = ? " +
                    "WHERE classroom_id = ?;";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setString(1, newData.get(0));
            statement.setString(2, newData.get(1));
            statement.setInt(3, id);
            SQLQueryHandler.getInstance().executeQuery(statement.toString());

            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }
}
