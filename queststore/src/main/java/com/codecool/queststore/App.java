package com.codecool.queststore;

import com.codecool.queststore.DAO.IStoreDAO;
import com.codecool.queststore.DAO.QuestDAO;

import java.sql.SQLException;

public class App {
    public static void main( String args[] ) {
        IStoreDAO dao = new QuestDAO();
        try {
            dao.createEntity("quest", "new quest", 20);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}