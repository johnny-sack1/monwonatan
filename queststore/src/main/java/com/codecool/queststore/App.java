package com.codecool.queststore;

import com.codecool.queststore.DAO.*;

import java.sql.SQLException;

public class App {
    public static void main( String args[] ) {
        IStoreDAO questDAO = new QuestDAO();
        UserDAO studentDAO = new StudentDAO();
        try {
            ((StudentDAO) studentDAO).createUser("John", "Doe", "john", "john", 1);
            questDAO.createEntity("quest1", "new quest", 20);
            questDAO.createEntity("quest2", "new quest", 45);
            ((StudentDAO) studentDAO).updateStudentCoins("john", 2);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}