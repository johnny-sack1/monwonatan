package com.codecool.queststore.DAO;

import java.sql.ResultSet;
import java.util.List;

public interface IStoreDAO {
    ResultSet loadEntity(int id);
    ResultSet loadEntity(String name);
    void updateEntity(int id, List<String> newData);
    void updateEntity(String name, List<String> newData);
}
