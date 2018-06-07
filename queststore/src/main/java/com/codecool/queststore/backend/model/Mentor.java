package com.codecool.queststore.backend.model;

public class Mentor extends  User {

    private String email;
    private String address;


    public Mentor(String firstName, String lastName, String login, String password,
                  int classRoomID, String userType, String email, String address) {

        super(firstName, lastName, login, password, classRoomID, userType);
        this.email = email;
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
