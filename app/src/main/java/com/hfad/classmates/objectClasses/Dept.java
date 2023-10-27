package com.hfad.classmates.objectClasses;

import java.util.List;

public class Dept {
    String departmentName;
    String abv;

    public Dept(){}

    public Dept(String departmentName, String abv) {
        this.departmentName = departmentName;
        this.abv = abv;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.departmentName = DepartmentName;
    }

}
