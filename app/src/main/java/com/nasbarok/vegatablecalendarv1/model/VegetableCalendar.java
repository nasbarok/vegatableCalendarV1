package com.nasbarok.vegatablecalendarv1.model;



public class VegetableCalendar {

    private int vegetableCalendarId;
    private String vegetableCalendarName;
    private String vegetableCalendarSowing;
    private String vegetableCalendarPlantations;
    private String vegetableCalendarHarvest;

    public VegetableCalendar(){}
    public VegetableCalendar(int id, String name, String sowing, String plantations, String harvest){
        this.vegetableCalendarId = id;
        this.vegetableCalendarName = name;
        this.vegetableCalendarSowing = sowing;
        this.vegetableCalendarPlantations = plantations;
        this.vegetableCalendarHarvest = harvest;
    }

    public int getVegetableCalendarId() {
        return vegetableCalendarId;
    }

    public void setVegetableCalendarId(int vegetableCalendarId) {
        this.vegetableCalendarId = vegetableCalendarId;
    }

    public String getVegetableCalendarName() {
        return vegetableCalendarName;
    }

    public void setVegetableCalendarName(String vegetableCalendarName) {
        this.vegetableCalendarName = vegetableCalendarName;
    }

    public String getVegetableCalendarSowing() {
        return vegetableCalendarSowing;
    }

    public void setVegetableCalendarSowing(String vegetableCalendarSowing) {
        this.vegetableCalendarSowing = vegetableCalendarSowing;
    }

    public String getVegetableCalendarPlantations() {
        return vegetableCalendarPlantations;
    }

    public void setVegetableCalendarPlantations(String vegetableCalendarPlantations) {
        this.vegetableCalendarPlantations = vegetableCalendarPlantations;
    }

    public String getVegetableCalendarHarvest() {
        return vegetableCalendarHarvest;
    }

    public void setVegetableCalendarHarvest(String vegetableCalendarHarvest) {
        this.vegetableCalendarHarvest = vegetableCalendarHarvest;
    }
}
