package com.codecool.queststore.Controller;

public class UserController {

    private LoginDAO loginDAO;

    public UserController() {
        this.loginDAO = new LoginDAO();
    }

    public String loginProcess(String login, String password) {
        PasswordManager passwordManager = new PasswordManager();
        String hashedPassword = loginDAO.getPasswordBy(login);
        if (passwordManager.isValidInput(password, hashedPassword)) {
            return loginDAO.getTypeBy(login);
        }
        return "invalid password";
    }

    private void handleUserOfType(String userType) {

    }
}
