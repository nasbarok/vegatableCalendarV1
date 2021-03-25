package com.nasbarok.vegatablecalendarv1.model;

public class UserInformations {
    private String mails;
    private String startTime;
    private String endTime;
    private String city;
    private String climate;

    public UserInformations(){}
    public UserInformations(String mails,String startTime,String endTime,String city,String climate){
        this.mails = mails;
        this.startTime = startTime;
        this.endTime = endTime;
        this.city = city;
        this.climate = climate;
    }

    public String getMails() {
        return mails;
    }

    public void setMails(String mails) {
        this.mails = mails;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }
}
