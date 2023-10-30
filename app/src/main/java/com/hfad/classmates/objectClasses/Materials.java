package com.hfad.classmates.objectClasses;

public class Materials {
    String materialName, fileType, filePath;

    public Materials(String materialName, String fileType, String filePath) {
        this.materialName = materialName;
        this.fileType = fileType;
        this.filePath = filePath;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.fileType = filePath;
    }
}
