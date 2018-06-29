package com.codecool.queststore.backend.model;

public class ExpLvl {
    private final int INDEX;
    private int coolcoinsNeeded;
    private String description;

    public ExpLvl(int index, int coolcoinsNeeded, String description) {
        this.INDEX = index;
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
