package com.codecool.queststore.backend.loginManager;

import com.codecool.queststore.backend.dao.LoginDAO;

public class LoginProcessHandler {

    private LoginDAO loginDAO;

    public LoginProcessHandler(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    public String loginProcess(String login, String password) {
        PasswordManager passwordManager = new PasswordManager();
        try {
            String hashedPassword = loginDAO.getPasswordBy(login);
            if (passwordManager.validatePassword(password, hashedPassword)) {
                return loginDAO.getTypeBy(login);
            }
        } catch (Exception e) {
            return "invalid password";
        }
        return null;
    }
}
