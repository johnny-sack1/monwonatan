package com.codecool.queststore.backend.dao;

import com.codecool.queststore.backend.databaseConnection.SQLQueryHandler;
import com.codecool.queststore.backend.model.Classroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassroomDAO {
    public void createClassroom(String name, String description) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();
        String query = "INSERT INTO classroom (name, description) " +
                       "VALUES (?, ?);";
        PreparedStatement statement = c.prepareStatement(query);
        statement.setString(1, name);
        statement.setString(2, description);
        SQLQueryHandler.getInstance().executeQuery(statement.toString());
    }

    public void updateClassroom(int id, List<String> newData) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();
        String query = "UPDATE classroom " +
                       "SET name = ?, description = ? " +
                       "WHERE classroom_id = ?;";
        PreparedStatement statement = c.prepareStatement(query);

        statement.setString(1, newData.get(0));
        statement.setString(2, newData.get(1));
        statement.setInt(3, id);
        SQLQueryHandler.getInstance().executeQuery(statement.toString());
    }

    public void updateClassroom(Classroom classroom) throws SQLException {
        Connection c = SQLQueryHandler.getInstance().getConnection();
        String query = "UPDATE classroom " +
                "SET name = ?, description = ? " +
                "WHERE classroom_id = ?;";
        PreparedStatement statement = c.prepareStatement(query);

        statement.setString(1, classroom.getName());
        statement.setString(2, classroom.getDescription());
        statement.setInt(3, classroom.getID());
        SQLQueryHandler.getInstance().executeQuery(statement.toString());
    }

    public Classroom loadClassroom(int id) throws SQLException {
        String query = "SELECT * FROM classroom " +
                "WHERE classroom_id = ?;";
        Connection c = SQLQueryHandler.getInstance().getConnection();

        PreparedStatement statement = c.prepareStatement(query);
        statement.setInt(1, id);
        query = statement.toString();

        ResultSet classroomData = SQLQueryHandler.getInstance().executeQuery(query);
        return extractAndCreate(classroomData);
    }

    private Classroom extractAndCreate(ResultSet classroomData) {
        int ID_I = 1;
        int NAME_I = 2;
        int DESCRIPTION_I = 3;

        try {
            classroomData.next();
            int id =  classroomData.getInt(ID_I);
            String name = classroomData.getString(NAME_I).toLowerCase();
            String description = classroomData.getString(DESCRIPTION_I).toLowerCase();
            return new Classroom(id, name, description);
        } catch (SQLException ex) {
            return null;
        }
    }

    public List<Classroom> loadAllClassrooms() {
        List<Classroom> allClassrooms = new ArrayList<>();

        String query = "SELECT classroom_id FROM classroom;";
        ResultSet ids = SQLQueryHandler.getInstance().executeQuery(query);
        try {
            while (ids.next()) {
                allClassrooms.add(loadClassroom(ids.getInt("classroom_id")));
            }
            return allClassrooms;
        } catch (SQLException e) {
            return null;
        }
    }
}
