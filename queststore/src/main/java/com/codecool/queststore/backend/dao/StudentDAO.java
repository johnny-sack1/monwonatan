package com.codecool.queststore.backend.dao;

import com.codecool.queststore.backend.databaseConnection.SQLQueryHandler;
import com.codecool.queststore.backend.model.ExpLvl;
import com.codecool.queststore.backend.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StudentDAO {

    private final String TYPE = "student";

    public boolean createStudent(String firstName, String lastName, String login, String password,
                                 int classId) {
        try {
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
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public Student loadStudent(String login) throws SQLException {

        String firstName="";
        String lastName="";
        String password="";
        int classroomID=0;
        int coins_current=0;
        int coins_total=0;

        Connection c = SQLQueryHandler.getInstance().getConnection();

        String userQuery = "SELECT * FROM user_type WHERE login = ?";
        PreparedStatement userStatement = c.prepareStatement(userQuery);
        userStatement.setString(1, login);
        ResultSet userResultSet = SQLQueryHandler.getInstance().executeQuery(userStatement.toString());
        while (userResultSet.next()) {
            firstName = userResultSet.getString("first_name");
            lastName = userResultSet.getString("last_name");
            password = userResultSet.getString("password");
            classroomID = userResultSet.getInt("classroom_id");
        }

        String studentQuery = "SELECT coins_current, coins_total FROM student_type " +
                "WHERE login = ?";
        PreparedStatement studentStatement = c.prepareStatement(studentQuery);
        studentStatement.setString(1, login);
        ResultSet studentResultSet = SQLQueryHandler.getInstance().executeQuery(studentStatement.toString());
        while (studentResultSet.next()) {
            coins_current = studentResultSet.getInt("coins_current");
            coins_total = studentResultSet.getInt("coins_total");
        }

        Student student = new Student(firstName, lastName, login, password, classroomID, TYPE, coins_current, coins_total);

        return createAllStudentData(student, login);
    }
    public boolean updateStudent(Student student) {
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String login = student.getLogin();
        String password = student.getPassword();
        int classId = student.getClassRoomID();
        int currentCoins = student.getCoolcoins();
        int totalCoins = student.getTotalCoins();

        return updateStudent(firstName, lastName, login, password, classId, currentCoins, totalCoins);
    }

    public boolean updateStudent(String firstName, String lastName, String login, String password,
                                 int classId, int currentCoins, int totalCoins) {
        try {
            String userTableQuery = "UPDATE user_type SET first_name = ?, last_name = ?, password = ?, " +
                    "classroom_id = ?, type = ? WHERE login = ?";

            String studentTableQuery = "UPDATE student_type SET coins_current = ?, coins_total = ? WHERE login = ?;";

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
            studentStatement.setString(3, login);

            String query = userStatement.toString() + "; " + studentStatement.toString();

            SQLQueryHandler.getInstance().executeQuery(query);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public List<Student> loadAllStudents() {
        List<Student> allStudents = new ArrayList<>();

        String query = "SELECT login FROM student_type";
        ResultSet logins = SQLQueryHandler.getInstance().executeQuery(query);
        try {
            while (logins.next()) {
                allStudents.add(loadStudent(logins.getString("login")));
            }
            return allStudents;
        }
        catch (SQLException e) {
            return null;
        }
    }

    public Student createAllStudentData(Student student, String login) {
        BackpackDAO backpackDAO = new BackpackDAO();
        ExpLevelDAO expLevelDAO = new ExpLevelDAO();
        student.setBackpack(backpackDAO.loadBackpack(login));
        Map<String, Integer> expLevels = expLevelDAO.loadAllExpLevel();
        StudentDAO.ValueComparator comparator = new StudentDAO.ValueComparator(expLevels);
        Map<String, Integer> sortedExpLevels = new TreeMap<>(comparator);
        sortedExpLevels.putAll(expLevels);

        for (Map.Entry<String, Integer> entry : sortedExpLevels.entrySet()) {
            if (student.getCoolcoins() >= entry.getValue()) {
                ExpLvl expLvl = new ExpLvl(entry.getValue(), entry.getKey());
                student.setExpLvl(expLvl);
            }
        }
        return student;
    }

    class ValueComparator implements Comparator<String> {
        Map<String, Integer> base;

        public ValueComparator(Map<String, Integer> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (base.get(a) <= base.get(b)) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}