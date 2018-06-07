package com.codecool.queststore.backend.model;

public class ExpLvl {
    private int coolcoinsNeeded;
    private String description;

    public ExpLvl(int coolcoinsNeeded, String description) {
        this.coolcoinsNeeded = coolcoinsNeeded;
        this.description = description;
    }

    public int getCoolcoinsNeeded() {
        return coolcoinsNeeded;
    }

    public void setCoolcoinsNeeded(int coolcoinsNeeded) {
        this.coolcoinsNeeded = coolcoinsNeeded;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
