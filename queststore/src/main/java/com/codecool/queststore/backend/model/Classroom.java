package com.codecool.queststore.backend.model;

public class Classroom {

    private final int id;
    private String name;
    private String description;

    public Classroom(int ID, String name, String description) {
        this.id = ID;
        this.name = name;
        this.description = description;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
