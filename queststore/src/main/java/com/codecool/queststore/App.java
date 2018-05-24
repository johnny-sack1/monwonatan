package com.codecool.queststore;

import com.codecool.queststore.DAO.*;

import java.sql.SQLException;

public class App {
    public static void main( String args[] ) {
        IStoreDAO questDAO = new QuestDAO();
        UserDAO studentDAO = new StudentDAO();
        try {
            questDAO.createEntity("quest1", "desc", 10);
            System.out.println(((QuestDAO) questDAO).getRewardByID(1));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}