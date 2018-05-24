package com.codecool.queststore.DAO;

import com.codecool.queststore.Controller.EColumnNumber;
import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;
import com.codecool.queststore.Model.Mentor;

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
                    "classroom_id = ?, type = ?) WHERE login = ?";
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

            String query = userStatement.toString() + "; " + mentorStatement.toString();

            SQLQueryHandler.getInstance().executeQuery(query);
            return true;
        }
        catch (SQLException e) {
            return false;
        }

    }
}
