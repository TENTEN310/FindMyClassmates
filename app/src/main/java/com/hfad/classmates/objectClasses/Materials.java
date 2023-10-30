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
    public String getFileType() {
        return fileType;
    }
    public String getFilePath() {
        return filePath;
    }
}
