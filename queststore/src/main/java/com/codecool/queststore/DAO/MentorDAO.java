package com.codecool.queststore.DAO;

import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MentorDAO extends UserDAO {

    private final String TYPE = "mentor";

    @Override
    public ResultSet loadUser(String login) throws SQLException {
        String query = "SELECT * FROM mentor_type WHERE login ILIKE ?";
        Connection c = SQLQueryHandler.getInstance().getConnection();

        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, login);
        query = statement.toString();

        return SQLQueryHandler.getInstance().executeQuery(query);
    }

    public void createUser(String firstName, String lastName, String login, String password,
                           int classId, String email, String address) throws SQLException {

        String userTableQuery = "INSERT INTO user_type (first_name, last_name, login, password, classroom_id, type) " +
                                "VALUES (?, ?, ?, ?, ?, ?) ";
        String mentorTableQuery = "INSERT INTO mentor_type (login, email, address) " +
                                  "VALUES (?, ?, ?);";

        Connection c = SQLQueryHandler.getInstance().getConnection();

        PreparedStatement userStatement = c.prepareStatement(userTableQuery);
        userStatement.setString(1, firstName);
        userStatement.setString(2, lastName);
        userStatement.setString(3, login);
        userStatement.setString(4, password);
        userStatement.setInt(5, classId);
        userStatement.setString(6, TYPE);

        PreparedStatement mentorStatement = c.prepareStatement(mentorTableQuery);
        mentorStatement.setString(1, login);
        mentorStatement.setString(2, email);
        mentorStatement.setString(3, address);

        String query = userStatement.toString() + "; " + mentorStatement.toString();

        SQLQueryHandler.getInstance().executeQuery(query);
    }
}
