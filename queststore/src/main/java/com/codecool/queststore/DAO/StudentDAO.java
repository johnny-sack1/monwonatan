package com.codecool.queststore.DAO;

import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;
import com.codecool.queststore.Model.Student;
import com.codecool.queststore.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void updateUser(User user) throws SQLException {
        Student student = (Student) user;
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String login = student.getLogin();
        String password = student.getPassword();
        int classId = student.getClassRoomID();
        int currentCoins = student.getCoolcoins();
        int totalCoins = student.getTotalCoins();
        updateUser(firstName, lastName, login, password, classId, currentCoins, totalCoins);
    }

    public void updateUser(String firstName, String lastName, String login, String password,
                           int classId, int currentCoins, int totalCoins) throws SQLException {

        String userTableQuery = "UPDATE user_type SET first_name = ?, last_name = ?, password = ?, " +
                "classroom_id = ?, type = ?) WHERE login = ?";
        String studentTableQuery = "UPDATE student_type SET coins_current = ?, coins_total = ? WHERE login = ?";

        Connection c = SQLQueryHandler.getInstance().getConnection();

        PreparedStatement userStatement = c.prepareStatement(userTableQuery);
        userStatement.setString(1, firstName);
        userStatement.setString(2, lastName);
        userStatement.setString(3, password);
        userStatement.setInt(4, classId);
        userStatement.setString(5, TYPE);
        userStatement.setString(6, login);

        PreparedStatement studentStatement = c.prepareStatement(studentTableQuery);
        studentStatement.setInt(1, currentCoins);
        studentStatement.setInt(2, totalCoins);
        userStatement.setString(3, login);

        String query = userStatement.toString() + "; " + studentStatement.toString();

        SQLQueryHandler.getInstance().executeQuery(query);
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

        PreparedStatement studentStatement = c.prepareStatement(studentTableQuery);
        studentStatement.setString(1, login);

        String query = userStatement.toString() + "; " + studentStatement.toString();

        SQLQueryHandler.getInstance().executeQuery(query);
    }

    public List<ResultSet> getStudentData(String login) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();
        String studentAndUserQuery = "SELECT * " +
                       "FROM user_type, student_type " +
                       "WHERE login = ?";
        PreparedStatement studentAndUserStatement = c.prepareStatement(studentAndUserQuery);
        studentAndUserStatement.setString(1, login);

        String backpackQuery = "SELECT artifact.name, artifact.description, backpack.status " +
                               "FROM backpack " +
                               "LEFT JOIN artifact ON backpack.artifact_id = artifact.artifact_id " +
                               "WHERE backpack.student_login = ?;";
        PreparedStatement backpackStatement = c.prepareStatement(backpackQuery);
        backpackStatement.setString(1, login);

        List<ResultSet> results = new ArrayList<>();
        ResultSet studentPersonalData = SQLQueryHandler.getInstance().executeQuery(studentAndUserStatement.toString());
        ResultSet artifactsData = SQLQueryHandler.getInstance().executeQuery(backpackStatement.toString());
        results.add(studentPersonalData);
        results.add(artifactsData);

        return results;
    }
}
