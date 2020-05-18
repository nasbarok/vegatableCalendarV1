package com.nasbarok.vegatablecalendarv1.model;


import java.io.Serializable;

public class VegetableCalendar implements Serializable {

    private int vegetableCalendarId;
    private String vegetableCalendarName;
    private String vegetableCalendarJanuary;
    private String vegetableCalendarFebruary;
    private String vegetableCalendarMarch;
    private String vegetableCalendarApril;
    private String vegetableCalendarMay;
    private String vegetableCalendarJune;
    private String vegetableCalendarJuly;
    private String vegetableCalendarAugust;
    private String vegetableCalendarSeptember;
    private String vegetableCalendarOctober;
    private String vegetableCalendarNovember;
    private String vegetableCalendarDecember;


    public VegetableCalendar(){}
    public VegetableCalendar(int id, String name, String vegetableCalendarJanuary, String vegetableCalendarFebruary, String vegetableCalendarMarch, String vegetableCalendarApril,
                             String vegetableCalendarMay, String vegetableCalendarJune, String vegetableCalendarJuly, String vegetableCalendarAugust,
                                      String vegetableCalendarSeptember, String  vegetableCalendarOctober, String vegetableCalendarNovember , String vegetableCalendarDecember){
        this.vegetableCalendarId = id;
        this.vegetableCalendarName = name;
        this.vegetableCalendarJanuary= vegetableCalendarJanuary;
        this.vegetableCalendarFebruary= vegetableCalendarFebruary;
        this.vegetableCalendarMarch= vegetableCalendarMarch;
        this.vegetableCalendarApril= vegetableCalendarApril;
        this.vegetableCalendarMay= vegetableCalendarMay;
        this.vegetableCalendarJune= vegetableCalendarJune;
        this.vegetableCalendarJuly= vegetableCalendarJuly;
        this.vegetableCalendarAugust= vegetableCalendarAugust;
        this.vegetableCalendarSeptember= vegetableCalendarSeptember;
        this.vegetableCalendarOctober= vegetableCalendarOctober;
        this.vegetableCalendarNovember= vegetableCalendarNovember;
        this.vegetableCalendarDecember= vegetableCalendarDecember;
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

    public String getVegetableCalendarJanuary() {
        return vegetableCalendarJanuary;
    }

    public void setVegetableCalendarJanuary(String vegetableCalendarJanuary) {
        this.vegetableCalendarJanuary = vegetableCalendarJanuary;
    }

    public String getVegetableCalendarFebruary() {
        return vegetableCalendarFebruary;
    }

    public void setVegetableCalendarFebruary(String vegetableCalendarFebruary) {
        this.vegetableCalendarFebruary = vegetableCalendarFebruary;
    }

    public String getVegetableCalendarMarch() {
        return vegetableCalendarMarch;
    }

    public void setVegetableCalendarMarch(String vegetableCalendarMarch) {
        this.vegetableCalendarMarch = vegetableCalendarMarch;
    }

    public String getVegetableCalendarApril() {
        return vegetableCalendarApril;
    }

    public void setVegetableCalendarApril(String vegetableCalendarApril) {
        this.vegetableCalendarApril = vegetableCalendarApril;
    }

    public String getVegetableCalendarMay() {
        return vegetableCalendarMay;
    }

    public void setVegetableCalendarMay(String vegetableCalendarMay) {
        this.vegetableCalendarMay = vegetableCalendarMay;
    }

    public String getVegetableCalendarJune() {
        return vegetableCalendarJune;
    }

    public void setVegetableCalendarJune(String vegetableCalendarJune) {
        this.vegetableCalendarJune = vegetableCalendarJune;
    }

    public String getVegetableCalendarJuly() {
        return vegetableCalendarJuly;
    }

    public void setVegetableCalendarJuly(String vegetableCalendarJuly) {
        this.vegetableCalendarJuly = vegetableCalendarJuly;
    }

    public String getVegetableCalendarAugust() {
        return vegetableCalendarAugust;
    }

    public void setVegetableCalendarAugust(String vegetableCalendarAugust) {
        this.vegetableCalendarAugust = vegetableCalendarAugust;
    }

    public String getVegetableCalendarSeptember() {
        return vegetableCalendarSeptember;
    }

    public void setVegetableCalendarSeptember(String vegetableCalendarSeptember) {
        this.vegetableCalendarSeptember = vegetableCalendarSeptember;
    }

    public String getVegetableCalendarOctober() {
        return vegetableCalendarOctober;
    }

    public void setVegetableCalendarOctober(String vegetableCalendarOctober) {
        this.vegetableCalendarOctober = vegetableCalendarOctober;
    }

    public String getVegetableCalendarNovember() {
        return vegetableCalendarNovember;
    }

    public void setVegetableCalendarNovember(String vegetableCalendarNovember) {
        this.vegetableCalendarNovember = vegetableCalendarNovember;
    }

    public String getVegetableCalendarDecember() {
        return vegetableCalendarDecember;
    }

    public void setVegetableCalendarDecember(String vegetableCalendarDecember) {
        this.vegetableCalendarDecember = vegetableCalendarDecember;
    }
}
