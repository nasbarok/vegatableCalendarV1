package com.nasbarok.vegatablecalendarv1.model;


import java.io.Serializable;

public class Classification implements Serializable {

    private int idTableClsDetails;
    private String longitude;
    private String latitude;
    private String classification;


    public Classification(){}
    public Classification(String longitude, String latitude, String classification){
        this.longitude = longitude;
        this.latitude= latitude;
        this.classification= classification;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
