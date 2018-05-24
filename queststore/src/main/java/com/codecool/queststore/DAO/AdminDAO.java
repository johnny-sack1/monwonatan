package com.codecool.queststore.DAO;

import com.codecool.queststore.DatabaseConnection.SQLQueryHandler;
import com.codecool.queststore.Model.Admin;
import com.codecool.queststore.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO extends UserDAO {

    private final String TYPE = "admin";

    @Override
    public ResultSet loadUser(String login) throws SQLException{
        String query = "SELECT * FROM user_type LEFT JOIN admin_type ON (user_type.login = admin_type.login) " +
                "WHERE admin_type.login = ?;";
        Connection c = SQLQueryHandler.getInstance().getConnection();

        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, login);
        query = statement.toString();

        return SQLQueryHandler.getInstance().executeQuery(query);
    }

    @Override
    public void updateUser(User user) throws SQLException {
        Admin admin = (Admin) user;
        String firstName = admin.getFirstName();
        String lastName = admin.getLastName();
        String login = admin.getLogin();
        String password = admin.getPassword();
        int classId = admin.getClassRoomID();
        String email = admin.getEmail();
        updateUser(firstName, lastName, login, password, classId, email);
    }

    public void updateUser(String firstName, String lastName, String login, String password,
                           int classId, String email) throws SQLException {

        String userTableQuery = "UPDATE user_type SET first_name = ?, last_name = ?, password = ?, " +
                "classroom_id = ?, type = ?) WHERE login = ?";
        String adminTableQuery = "UPDATE admin_type SET email = ? WHERE login = ?";

        Connection c = SQLQueryHandler.getInstance().getConnection();

        PreparedStatement userStatement = c.prepareStatement(userTableQuery);
        userStatement.setString(1, firstName);
        userStatement.setString(2, lastName);
        userStatement.setString(3, password);
        userStatement.setInt(4, classId);
        userStatement.setString(5, TYPE);
        userStatement.setString(6, login);

        PreparedStatement adminStatement = c.prepareStatement(adminTableQuery);
        adminStatement.setString(1, email);
        adminStatement.setString(2, login);

        String query = userStatement.toString() + "; " + adminStatement.toString();

        SQLQueryHandler.getInstance().executeQuery(query);
    }

    public void createUser(String firstName, String lastName, String login, String password,
                           int classId, String email) throws SQLException {

        String userTableQuery = "INSERT INTO user_type (first_name, last_name, login, password, classroom_id, type) " +
                                "VALUES (?, ?, ?, ?, ?, ?) ";
        String adminTableQuery = "INSERT INTO admin_type (login, email) VALUES (?, ?);";

        Connection c = SQLQueryHandler.getInstance().getConnection();

        PreparedStatement userStatement = c.prepareStatement(userTableQuery);
        userStatement.setString(1, firstName);
        userStatement.setString(2, lastName);
        userStatement.setString(3, login);
        userStatement.setString(4, password);
        userStatement.setInt(5, classId);
        userStatement.setString(6, TYPE);

        PreparedStatement adminStatement = c.prepareStatement(adminTableQuery);
        adminStatement.setString(1, login);
        adminStatement.setString(2, email);

        String query = userStatement.toString() + "; " + adminStatement.toString();

        SQLQueryHandler.getInstance().executeQuery(query);
    }
}
