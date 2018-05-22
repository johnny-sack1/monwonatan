package com.codecool.queststore.Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminController {

    private Admin currentUser;
    public AdminDAO adminDAODAO;
    public StudentDAO studentDAO;

    public void createMentor(List<String> mentorData) throws SQLException {

    }

    public void createClass(List<String> classData) {

    }

    public void updateMentor(List<String> mentorData) {

    }

    public ResultSet getMentorSummary(String mentorLogin) {
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
