package com.codecool.queststore;

import com.codecool.queststore.backend.dao.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main( String args[] ) {
        ClassroomDAO classroomDAO = new ClassroomDAO();
        try {
            classroomDAO.createClassroom("a", "desc");
            List<String> newData = new ArrayList<>();
            newData.add("b");
            newData.add("desc2");
            classroomDAO.updateClassroom(1, newData);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}