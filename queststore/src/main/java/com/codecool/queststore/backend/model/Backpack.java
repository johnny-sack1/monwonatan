package com.codecool.queststore.backend.model;

import java.util.HashMap;

public class Backpack {

    private final String studentLogin;
    private HashMap<Artifact, String> studentBackpack;

    public Backpack(String studentLogin) {
        this.studentLogin = studentLogin;
        this.studentBackpack = new HashMap<Artifact, String>();
    }

    public HashMap<Artifact, String> getStudentBackpack() {
        return studentBackpack;
    }

    public void addToBackpack(Artifact artifact, String status) {
        studentBackpack.put(artifact, status);
    }

    public String getStudentLogin() {
        return studentLogin;
    }
}
