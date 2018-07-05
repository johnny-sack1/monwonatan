package com.codecool.queststore.backend.dao;

import com.codecool.queststore.backend.databaseConnection.SQLQueryHandler;
import com.codecool.queststore.backend.model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    private final String TYPE = "admin";

    public boolean createAdmin(String firstName, String lastName, String login, String password,
                           int classId, String email) {

        try {
            String userTableQuery = "INSERT INTO user_type (first_name, last_name, login, password, classroom_id, type) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";
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
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateAdmin(Admin admin) {
        String firstName = admin.getFirstName();
        String lastName = admin.getLastName();
        String login = admin.getLogin();
        String password = admin.getPassword();
        int classId = admin.getClassRoomID();
        String email = admin.getEmail();
        return updateAdmin(firstName, lastName, login, password, classId, email);
    }

    public boolean updateAdmin(String firstName, String lastName, String login, String password,
                           int classId, String email) {

        try {
            String userTableQuery = "UPDATE user_type SET first_name = ?, last_name = ?, password = ?, " +
                    "classroom_id = ?, type = ? WHERE login = ?;";
            String adminTableQuery = "UPDATE admin_type SET email = ? WHERE login = ?;";

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
            return true;
        } catch (SQLException e ) {
            return false;
        }
    }

    public Admin loadAdmin(String login) throws SQLException {
        String query = "SELECT * FROM user_type LEFT JOIN admin_type ON (user_type.login = admin_type.login) " +
                "WHERE admin_type.login = ?;";
        Connection c = SQLQueryHandler.getInstance().getConnection();

        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, login);
        query = statement.toString();

        ResultSet adminData = SQLQueryHandler.getInstance().executeQuery(query);
        return extractAndCreate(adminData);
    }

    private Admin extractAndCreate(ResultSet adminData) {
        int LOGIN_I = 1;
        int PASSWORD_I = 2;
        int FIRST_NAME_I = 3;
        int LAST_NAME_I = 4;
        int CLASSROOM_I = 5;
        int EMAIL_I = 8;

        try {
            adminData.next();
            String login =  adminData.getString(LOGIN_I);
            String first_name = adminData.getString(FIRST_NAME_I);
            String last_name = adminData.getString(LAST_NAME_I);
            String password = adminData.getString(PASSWORD_I);
            int classroom_id = adminData.getInt(CLASSROOM_I);
            String email = adminData.getString(EMAIL_I);
            return new Admin(first_name, last_name, login, password, classroom_id, TYPE, email);
        } catch (SQLException ex) {
            return null;
        }
    }

    public List<Admin> loadAllAdmins() {
        List<Admin> allAdmins = new ArrayList<>();

        String query = "SELECT login FROM admin_type;";
        ResultSet logins = SQLQueryHandler.getInstance().executeQuery(query);
        try {
            while (logins.next()) {
                allAdmins.add(loadAdmin(logins.getString("login")));
            }
            return allAdmins;
        } catch (SQLException e) {
            return null;
        }
    }
}
