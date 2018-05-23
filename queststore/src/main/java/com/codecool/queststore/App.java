package com.codecool.queststore;

import com.codecool.queststore.DAO.ArtifactDAO;
import com.codecool.queststore.DAO.IStoreDAO;
import com.codecool.queststore.DAO.QuestDAO;

import java.sql.SQLException;

public class App {
    public static void main( String args[] ) {
        IStoreDAO questDAO = new QuestDAO();
        IStoreDAO artifactDAO = new ArtifactDAO();
        try {
            questDAO.createEntity("quest", "new quest", 20);
            artifactDAO.createEntity("artifact", "new artifact", 19);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}