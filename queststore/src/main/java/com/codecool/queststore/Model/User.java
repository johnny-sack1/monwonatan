package com.codecool.queststore.Model;

public abstract class User {

    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private int classRoomID;
    private UserType userType;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return this.login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getClassRoomID() {
        return this.classRoomID;
    }

    public void setClassRoomID(int classRoomID) {
        this.classRoomID = classRoomID;
    }
}
