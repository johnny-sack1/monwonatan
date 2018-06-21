package com.codecool.queststore.backend.dao;

import com.codecool.queststore.backend.databaseConnection.SQLQueryHandler;
import com.codecool.queststore.backend.model.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MentorDAO {

    private final String TYPE = "mentor";

    public boolean createMentor(String firstName, String lastName, String login, String password,
                                int classId, String email, String address) {

        try {
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

            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean updateMentor(Mentor mentor) {

        String login = mentor.getLogin();
        String first_name = mentor.getFirstName();
        String last_name = mentor.getLastName();
        int classroom_id = mentor.getClassRoomID();
        String password = mentor.getPassword();
        String address = mentor.getAddress();
        String email = mentor.getEmail();
        return updateMentor(first_name, last_name, login, password, classroom_id, email, address);

    }

    public boolean updateMentor(String firstName, String lastName, String login, String password,
                                int classId, String email, String address) {

        try {
            String userTableQuery = "UPDATE user_type SET first_name = ?, last_name = ?, password = ?, " +
                    "classroom_id = ?, type = ? WHERE login = ?";
            String mentorTableQuery = "UPDATE mentor_type SET email = ?, address = ? WHERE login = ?";

            Connection c = SQLQueryHandler.getInstance().getConnection();

            PreparedStatement userStatement = c.prepareStatement(userTableQuery);
            userStatement.setString(1, firstName);
            userStatement.setString(2, lastName);
            userStatement.setString(3, password);
            userStatement.setInt(4, classId);
            userStatement.setString(5, TYPE);
            userStatement.setString(6, login);

            PreparedStatement mentorStatement = c.prepareStatement(mentorTableQuery);
            mentorStatement.setString(1, email);
            mentorStatement.setString(2, address);
            mentorStatement.setString(3, login);

            System.out.println(userStatement.toString());

            String query = userStatement.toString() + "; " + mentorStatement.toString() + ";";

            SQLQueryHandler.getInstance().executeQuery(query);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public Mentor loadMentor(String login) throws SQLException {
        String query = "SELECT * FROM user_type LEFT JOIN mentor_type ON (user_type.login = mentor_type.login) " +
                "WHERE mentor_type.login ILIKE ?";
        Connection c = SQLQueryHandler.getInstance().getConnection();

        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, login);
        query = statement.toString();

        ResultSet resultSet = SQLQueryHandler.getInstance().executeQuery(query);

        return extractAndCreate(resultSet);
    }

    public Mentor extractAndCreate(ResultSet resultSet) {
        int LOGIN_I = 1;
        int PASSWORD_I = 2;
        int FIRST_NAME_I = 3;
        int LAST_NAME_I = 4;
        int CLASSROOM_ID_I = 5;
        int EMAIL_I = 9;
        int ADDRESS_I = 10;

        try {
            resultSet.next();

            String firstName = resultSet.getString(FIRST_NAME_I);
            String lastName = resultSet.getString(LAST_NAME_I);
            String login = resultSet.getString(LOGIN_I);
            String password = resultSet.getString(PASSWORD_I);
            int classId = Integer.parseInt(resultSet.getString(CLASSROOM_ID_I));
            String email = resultSet.getString(EMAIL_I);
            String address = resultSet.getString(ADDRESS_I);
            return new Mentor(firstName, lastName, login, password, classId, TYPE, email, address);
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Mentor> loadAllMentors() {

        List<Mentor> allMentors = new ArrayList<>();
        String query = "SELECT login FROM mentor_type;";
        ResultSet logins = SQLQueryHandler.getInstance().executeQuery(query);

        try {
            while (logins.next()) {
                allMentors.add(loadMentor(logins.getString("login")));
            }
            return allMentors;

        } catch (SQLException e) {
            return null;
        }
    }
}
