package com.nasbarok.vegatablecalendarv1.model;

public class MyVegetableGarden {
    private int vegetableCalendarId;
    private String outdoorSeedingNotify;
    private String indoorSeedingNotify;
    private String transplationNotify;
    private String harvestNotify;

    public MyVegetableGarden(){}
    public MyVegetableGarden(int vegetableCalendarId,String outdoorSeedingNotify,String indoorSeedingNotify,String transplationNotify,String harvestNotify){
        this.vegetableCalendarId = vegetableCalendarId;
        this.outdoorSeedingNotify = outdoorSeedingNotify;
        this.indoorSeedingNotify = indoorSeedingNotify;
        this.transplationNotify = transplationNotify;
        this.harvestNotify = harvestNotify;
    }

    public int getVegetableCalendarId() {
        return vegetableCalendarId;
    }

    public void setVegetableCalendarId(int vegetableCalendarId) {
        this.vegetableCalendarId = vegetableCalendarId;
    }

    public String getOutdoorSeedingNotify() {
        return outdoorSeedingNotify;
    }

    public void setOutdoorSeedingNotify(String outdoorSeedingNotify) {
        this.outdoorSeedingNotify = outdoorSeedingNotify;
    }

    public String getIndoorSeedingNotify() {
        return indoorSeedingNotify;
    }

    public void setIndoorSeedingNotify(String indoorSeedingNotify) {
        this.indoorSeedingNotify = indoorSeedingNotify;
    }

    public String getTransplationNotify() {
        return transplationNotify;
    }

    public void setTransplationNotify(String transplationNotify) {
        this.transplationNotify = transplationNotify;
    }

    public String getHarvestNotify() {
        return harvestNotify;
    }

    public void setHarvestNotify(String harvestNotify) {
        this.harvestNotify = harvestNotify;
    }
}
