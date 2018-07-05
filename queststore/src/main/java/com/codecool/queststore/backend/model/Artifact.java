package com.codecool.queststore.backend.model;

public class Artifact {

    private final int artifactId;
    private boolean availableForGroups;
    private String name;
    private String description;
    private int price;
    private String status;

    public Artifact(int artifactId, boolean availableForGroups, String name, String description, int price) {
        this.artifactId = artifactId;
        this.availableForGroups = availableForGroups;
        this.name = name;
        this.description = description;
        this.price = price;
    }
//
//    public Artifact(int artifactId, int availableForGroups, String name, String description, int price) {
//        this.artifactId = artifactId;
//        this.availableForGroups = availableForGroups == 1;
//        this.name = name;
//        this.description = description;
//        this.price = price;
//    }

    public int getArtifactId() {
        return artifactId;
    }

    public boolean isAvailableForGroups() {
        return availableForGroups;
    }

    public void setAvailableForGroups(boolean availableForGroups) {
        this.availableForGroups = availableForGroups;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String artifact = "artifact:";
        artifact += "id:" + artifactId + "|group:" + availableForGroups + "|name:" + name + "|desc:" + description
                + "|price:" + price + "|status:" + status;

        return artifact;
    }
}