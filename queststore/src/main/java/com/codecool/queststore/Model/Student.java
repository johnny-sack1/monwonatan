package com.codecool.queststore.Model;

public class Student extends User {

    private int coolcoins;
    private int totalCoins;
    private ExpLvl expLvl;
    private Backpack backpack;
    public Student(String firstName, String lastName, String login, String password,
                   int classRoomID, String userType, int coolcoins, int totalCoins) {

        super(firstName, lastName, login, password, classRoomID, userType);
        this.coolcoins = coolcoins;
        this.totalCoins = totalCoins;
    }

    public ExpLvl getExpLvl() {
        return expLvl;
    }

    public void setExpLvl(ExpLvl expLvl) {
        this.expLvl = expLvl;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    public int getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(int totalCoins) {
        this.totalCoins = totalCoins;
    }

    public int getCoolcoins() {
        return this.coolcoins;
    }

    public void addCoins(int coinsQuantity) {
        this.coolcoins += coinsQuantity;
    }

    public void substractCoins(int coinsQuantity) {
        this.coolcoins -= coinsQuantity;
    }
}
