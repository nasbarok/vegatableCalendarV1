package com.nasbarok.vegatablecalendarv1.model;

public class UserInformations {
    private String mails;
    private String startTime;
    private String endTime;

    public UserInformations(){}
    public UserInformations(String mails,String startTime,String endTime){
        this.mails = mails;
        this.startTime = startTime;
        this.endTime = endTime;
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
}
