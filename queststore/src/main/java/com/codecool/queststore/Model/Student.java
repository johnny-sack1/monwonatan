package com.codecool.queststore.Model;

public class Student extends User {

    private int coolcoins;

    public Student(String firstName, String lastName, String login, String password,
                   int classRoomID, String userType, int coolcoins) {

        super(firstName, lastName, login, password, classRoomID, userType);
        this.coolcoins = coolcoins;
    }

    public int getCoolcoins() {
        return this.coolcoins;
    }

    public void addCoins(int coinsQuantity) {
        this.coolcoins += coinsQuantity;
    }

    public void substractCoins(int coinsQuantity) {

    }
}
