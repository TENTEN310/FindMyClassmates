package com.hfad.classmates.objectClasses;

public class Materials {
    String fileType;
    String materialName;

    public Materials() {}

    public Materials(String fileType, String materialName) {
        this.fileType = fileType;
        this.materialName = materialName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
}
