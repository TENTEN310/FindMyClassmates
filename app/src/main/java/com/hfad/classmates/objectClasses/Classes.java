package com.hfad.classmates.objectClasses;

public class Classes {
    String abv;
    String description;
    String name;
    String professor;
    String term;
    int units;

    public Classes() {}

    public Classes(String description, String name, String professor, String term, int units, String abv) {
        this.description = description;
        this.name = name;
        this.professor = professor;
        this.term = term;
        this.units = units;
        this.abv = abv;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
