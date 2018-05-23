package com.codecool.queststore.Controller;

import com.codecool.queststore.Model.Mentor;
import com.codecool.queststore.Model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MentorController {

    private Mentor currentUser;
    public MentorDAO mentorDAO;
    public StudentDAO studentDAO;

    public void createStudent(List<String> studentData) throws SQLException {

    }

    public void createQuest(List<String> questData) {

    }

    public void updateQuest(int ID, List<String> questData) {

    }

    public void createArtifact(List<String> artifactData) {

    }

    public void updateArtifact(int ID, List<String> artifactData) {

    }

    public void updateStudentCoins(String studentLogin, int questID) {

    }

    public Student getStudentSummary(String studentLogin) {

        ResultSet resultSet = studentDAO.loadUser(studentLogin);
        Student currentStudent;

        try {

            while (resultSet.next()) {

                String login =  resultSet.getString(EColumnNumber.LOGIN.indexForDatabase()).toLowerCase();
                String first_name = resultSet.getString(EColumnNumber.FIRST_NAME.indexForDatabase()).toLowerCase();
                String last_name = resultSet.getString(EColumnNumber.LAST_NAME.indexForDatabase()).toLowerCase();
                int classroom_id = resultSet.getInt(EColumnNumber.CLASSROOM.indexForDatabase());
                String type = resultSet.getString(EColumnNumber.TYPE.indexForDatabase()).toLowerCase();
                int coins = resultSet.getInt(EColumnNumber.COINS.indexForDatabase());

                currentStudent = new Student(login, first_name, last_name, classroom_id, type, coins);
                return currentStudent;
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}
