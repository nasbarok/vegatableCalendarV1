package com.nasbarok.vegatablecalendarv1.model;


import java.io.Serializable;

public class Classification implements Serializable {

    private int idTableClsDetails;
    private String name;
    private String nameLong;
    private String desc;


    public Classification(){}
    public Classification(int id,String name, String nameLong, String desc){
        this.idTableClsDetails = id;
        this.name= name;
        this.nameLong= nameLong;
        this.desc= desc;
    }

    public int getIdTableClsDetails() {
        return idTableClsDetails;
    }

    public void setIdTableClsDetails(int idTableClsDetails) {
        this.idTableClsDetails = idTableClsDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameLong() {
        return nameLong;
    }

    public void setNameLong(String nameLong) {
        this.nameLong = nameLong;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
