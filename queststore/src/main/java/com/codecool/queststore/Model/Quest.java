package com.codecool.queststore.Model;

public class Quest {
    private String name;
    private String description;
    private int value;
    private boolean isAvailableForGroups;

    public Quest(String name, String description, int value, boolean isAvailableForGroups) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.isAvailableForGroups = isAvailableForGroups;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isAvailableForGroups() {
        return isAvailableForGroups;
    }

    public void setAvailableForGroups(boolean availableForGroups) {
        isAvailableForGroups = availableForGroups;
    }
}
