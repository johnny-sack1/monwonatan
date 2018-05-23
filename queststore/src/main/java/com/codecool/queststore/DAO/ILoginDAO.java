package com.codecool.queststore.DAO;

import java.sql.ResultSet;

public interface ILoginDAO {
    ResultSet getPasswordBy(String login);
    ResultSet getTypeBy(String login);
}
