package com.codecool.queststore.Controller;

import com.codecool.queststore.DAO.AdminDAO;
import com.codecool.queststore.DAO.MentorDAO;
import com.codecool.queststore.Model.Admin;
import com.codecool.queststore.Model.Mentor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminController {

    private Admin currentUser;
    public AdminDAO adminDAO;
    public MentorDAO mentorDAO;

    public void createMentor(List<String> mentorData) throws SQLException {
        try {
            String firstName = mentorData.get(EColumnNumber.FIRST_NAME.index()).toLowerCase();
            String lastName = mentorData.get(EColumnNumber.LAST_NAME.index()).toLowerCase();
            String login = mentorData.get(EColumnNumber.LOGIN.index()).toLowerCase();
            String password = mentorData.get(EColumnNumber.PASSWORD.index()).toLowerCase();
            int classId = Integer.parseInt(mentorData.get(EColumnNumber.CLASSROOM.index()));
            String userType = mentorData.get(EColumnNumber.TYPE.index()).toLowerCase();
            String email = mentorData.get(EColumnNumber.EMAIL.index()).toLowerCase();
            String address = mentorData.get(EColumnNumber.ADDRESS.index()).toLowerCase();
            createMentor(firstName, lastName, login, password, classId, userType, email, address);
        } catch (Exception e) {
            // -> View - Show errors
        }
    }

    public void createMentor(String firstName, String lastName, String login, String password,
                            int classId, String userType, String email, String address) throws SQLException {
        mentorDAO.createUser(firstName, lastName, login, password, classId, email, address);
    }

    public void createClass(List<String> classData) {

    }

    public void updateMentor(List<String> mentorData) {

    }

    public Mentor getMentorSummary(String mentorLogin) {

        try {

            ResultSet resultSet = mentorDAO.loadUser(mentorLogin);
            Mentor currentMentor;

            while (resultSet.next()) {

                String login =  resultSet.getString(EColumnNumber.LOGIN.indexForDatabase()).toLowerCase();
                String first_name = resultSet.getString(EColumnNumber.FIRST_NAME.indexForDatabase()).toLowerCase();
                String last_name = resultSet.getString(EColumnNumber.LAST_NAME.indexForDatabase()).toLowerCase();
                String password = resultSet.getString(EColumnNumber.PASSWORD.indexForDatabase()).toLowerCase();
                int classroom_id = resultSet.getInt(EColumnNumber.CLASSROOM.indexForDatabase());
                String type = resultSet.getString(EColumnNumber.TYPE.indexForDatabase()).toLowerCase();
                String email = resultSet.getString(EColumnNumber.EMAIL.indexForDatabase()).toLowerCase();
                String address = resultSet.getString(EColumnNumber.ADDRESS.indexForDatabase()).toLowerCase();

                currentMentor = new Mentor(first_name, last_name, login, password, classroom_id, type, email, address);
                return currentMentor;
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public void createExpLevel(String expLevelName, int requiredCoins) {

    }

    public void updateExpLevel(String newExpLevelName, int currentRequiredCoins, int newRequiredCoins) {

    }

    public void updateExpLevel(String expLevelName, String newExpLevelName, int newRequiredCoins) {

    }

    public void deleteExpLevel(String expLevelName) {

    }

    public void deleteExpLevel(int requiredCoins) {

    }

}
