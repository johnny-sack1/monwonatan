package com.codecool.queststore;

import com.codecool.queststore.DAO.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main( String args[] ) {
        StudentDAO studentDAO = new StudentDAO();

        try {
            studentDAO.createStudent("John", "Doe", "john", "abc", 2);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}