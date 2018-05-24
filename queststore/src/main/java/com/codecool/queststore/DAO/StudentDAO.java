package com.codecool.queststore.DAO;

import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO extends UserDAO {

    private final String TYPE = "student";

    @Override
    public ResultSet loadUser(String login) throws SQLException {
        String query = "SELECT * FROM student_type WHERE login ILIKE ?";
        Connection c = SQLQueryHandler.getInstance().getConnection();

        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, login);
        query = statement.toString();


        return SQLQueryHandler.getInstance().executeQuery(query);
    }

    public void createUser(String firstName, String lastName, String login, String password,
                           int classId) throws SQLException {

        String userTableQuery = "INSERT INTO user_type (first_name, last_name, login, password, classroom_id, type) " +
                                "VALUES (?, ?, ?, ?, ?, ?) ";
        String studentTableQuery = "INSERT INTO student_type (login, coins_current, coins_total) " +
                "VALUES (?, default, default);";

        Connection c = SQLQueryHandler.getInstance().getConnection();

        PreparedStatement userStatement = c.prepareStatement(userTableQuery);
        userStatement.setString(1, firstName);
        userStatement.setString(2, lastName);
        userStatement.setString(3, login);
        userStatement.setString(4, password);
        userStatement.setInt(5, classId);
        userStatement.setString(6, TYPE);

        PreparedStatement mentorStatement = c.prepareStatement(studentTableQuery);
        mentorStatement.setString(1, login);

        String query = userStatement.toString() + "; " + mentorStatement.toString();

        SQLQueryHandler.getInstance().executeQuery(query);
    }

    public void updateStudentCoins(String login, int questID) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();
        String questQuery = "SELECT value FROM quest " +
                            "WHERE quest_id = ?;";

        PreparedStatement questStatement = c.prepareStatement(questQuery);
        questStatement.setInt(1, questID);
        ResultSet resultSet = executeQuery(questStatement.toString());
        resultSet.next();
        int gainedCoins = resultSet.getInt("value");

        String studentQuery = "UPDATE student_type " +
                       "SET coins_current = coins_current + ?, coins_total = coins_total + ? " +
                       "WHERE login = ?;";

        PreparedStatement studentStatement = c.prepareStatement(studentQuery);
        studentStatement.setInt(1, gainedCoins);
        studentStatement.setInt(2, gainedCoins);
        studentStatement.setString(3, login);
        executeQuery(studentStatement.toString());
    }
}
