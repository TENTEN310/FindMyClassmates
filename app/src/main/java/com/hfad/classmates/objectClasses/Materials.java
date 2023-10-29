package com.hfad.classmates.objectClasses;

public class Materials {
    String materialName, fileType;

    public Materials(String materialName, String fileType) {
        this.materialName = materialName;
        this.fileType = fileType;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
