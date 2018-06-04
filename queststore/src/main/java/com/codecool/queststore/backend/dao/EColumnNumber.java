package com.codecool.queststore.backend.dao;

public enum EColumnNumber {

    FIRST_NAME(0), LAST_NAME(1), LOGIN(2), PASSWORD(3), CLASSROOM(4), TYPE(5),
    EMAIL(6), COINS(6), ADDRESS(7);

    private int i;

    EColumnNumber(int index) {
        this.i = index;
    }

    public int index() {
        return this.i;
    }

    public int indexForDatabase() {
        return this.i + 1;
    }
}
