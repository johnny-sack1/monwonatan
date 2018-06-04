package com.codecool.queststore.backend.model;

public class Admin extends User {

    private String email;

    public Admin(String firstName, String lastName, String login, String password,
                 int classRoomID, String userType, String email) {

        super(firstName, lastName, login, password, classRoomID, userType);
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
