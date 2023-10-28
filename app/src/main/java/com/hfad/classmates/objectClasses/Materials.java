package com.hfad.classmates.objectClasses;

public class Materials {
    String abv;
    String fileType;
    String materialName;
    String storageLocation;

    public Materials() {}

    public Materials(String abv, String fileType, String materialName, String storageLocation) {
        this.abv = abv;
        this.fileType = fileType;
        this.materialName = materialName;
        this.storageLocation = storageLocation;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
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

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String materialName) {
        this.storageLocation = storageLocation;
    }
}
