package com.hfad.classmates;

public class Class {
    private String name;
    private String professor;
    private String subject;
    private String description;

    public Class() {
        // Default constructor required for Firestore
    }

    public Class(String name, String professor, String subject, String description) {
        this.name = name;
        this.professor = professor;
        this.subject = subject;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getProfessor() {
        return professor;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }
}
