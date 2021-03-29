package com.nasbarok.vegatablecalendarv1.model;

import com.nasbarok.vegatablecalendarv1.utils.SunAndMoon;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class DayStats {
    private String day;
    private int dayNumber;
    private String month;
    private String year;
    private Date currentDay ; // date, time and timezone of calculation

    //status sun
    private ZonedDateTime sunRise;
    private ZonedDateTime sunSet;
    private ZonedDateTime sunNadir;
    private ZonedDateTime sunNoon;
    private double sunAltitude;
    private double sunAzimuth;
    private double sunDistance;
    private double sunParallacticAngle;

    //status moon
    private double moonAltitude;
    private double moonAzimuth;
    private double moonDistance;
    private double moonParallacticAngle;
    private double moonIlluminationAngle;
    private double moonIlluminationFraction;
    private double moonIlluminationPhase;
    private double moonPhaseDistance;
    private ZonedDateTime moonPhaseTime;
    private boolean moonPhaseIsMicroMoon;
    private boolean moonPhaseIsSuperMoon;

    public DayStats(){}

    public DayStats(SunAndMoon sunAndMoon,int dayNumber, Date currentDate){
        dayNumber = dayNumber;
        currentDay = currentDate;
        sunRise = sunAndMoon.getSunRise();
        sunSet = sunAndMoon.getSunSet();
        sunNadir = sunAndMoon.getSunNadir();
        sunNoon = sunAndMoon.getSunNoon();
        sunAltitude = sunAndMoon.getSunAltitude();
        sunAzimuth = sunAndMoon.getSunAzimuth();
        sunDistance = sunAndMoon.getSunDistance();
        sunParallacticAngle = sunAndMoon.getSunParallacticAngle();
        moonAltitude = sunAndMoon.getMoonAltitude();
        moonAzimuth = sunAndMoon.getMoonAzimuth();
        moonDistance  = sunAndMoon.getMoonDistance();
        moonParallacticAngle = sunAndMoon.getMoonParallacticAngle();
        moonIlluminationAngle = sunAndMoon.getMoonIlluminationAngle();
        moonIlluminationFraction = sunAndMoon.getMoonIlluminationFraction();
        moonIlluminationPhase = sunAndMoon.getMoonIlluminationPhase();
        moonPhaseDistance = sunAndMoon.getMoonPhaseDistance();
        moonPhaseTime = sunAndMoon.getMoonPhaseTime();
        moonPhaseIsMicroMoon = sunAndMoon.isMoonPhaseIsMicroMoon();
        moonPhaseIsSuperMoon = sunAndMoon.isMoonPhaseIsMicroMoon();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Date getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Date currentDay) {
        this.currentDay = currentDay;
    }

    public ZonedDateTime getSunRise() {
        return sunRise;
    }

    public void setSunRise(ZonedDateTime sunRise) {
        this.sunRise = sunRise;
    }

    public ZonedDateTime getSunSet() {
        return sunSet;
    }

    public void setSunSet(ZonedDateTime sunSet) {
        this.sunSet = sunSet;
    }

    public ZonedDateTime getSunNadir() {
        return sunNadir;
    }

    public void setSunNadir(ZonedDateTime sunNadir) {
        this.sunNadir = sunNadir;
    }

    public ZonedDateTime getSunNoon() {
        return sunNoon;
    }

    public void setSunNoon(ZonedDateTime sunNoon) {
        this.sunNoon = sunNoon;
    }

    public double getSunAltitude() {
        return sunAltitude;
    }

    public void setSunAltitude(double sunAltitude) {
        this.sunAltitude = sunAltitude;
    }

    public double getSunAzimuth() {
        return sunAzimuth;
    }

    public void setSunAzimuth(double sunAzimuth) {
        this.sunAzimuth = sunAzimuth;
    }

    public double getSunDistance() {
        return sunDistance;
    }

    public void setSunDistance(double sunDistance) {
        this.sunDistance = sunDistance;
    }

    public double getSunParallacticAngle() {
        return sunParallacticAngle;
    }

    public void setSunParallacticAngle(double sunParallacticAngle) {
        this.sunParallacticAngle = sunParallacticAngle;
    }

    public double getMoonAltitude() {
        return moonAltitude;
    }

    public void setMoonAltitude(double moonAltitude) {
        this.moonAltitude = moonAltitude;
    }

    public double getMoonAzimuth() {
        return moonAzimuth;
    }

    public void setMoonAzimuth(double moonAzimuth) {
        this.moonAzimuth = moonAzimuth;
    }

    public double getMoonDistance() {
        return moonDistance;
    }

    public void setMoonDistance(double moonDistance) {
        this.moonDistance = moonDistance;
    }

    public double getMoonParallacticAngle() {
        return moonParallacticAngle;
    }

    public void setMoonParallacticAngle(double moonParallacticAngle) {
        this.moonParallacticAngle = moonParallacticAngle;
    }

    public double getMoonIlluminationAngle() {
        return moonIlluminationAngle;
    }

    public void setMoonIlluminationAngle(double moonIlluminationAngle) {
        this.moonIlluminationAngle = moonIlluminationAngle;
    }

    public double getMoonIlluminationFraction() {
        return moonIlluminationFraction;
    }

    public void setMoonIlluminationFraction(double moonIlluminationFraction) {
        this.moonIlluminationFraction = moonIlluminationFraction;
    }

    public double getMoonIlluminationPhase() {
        return moonIlluminationPhase;
    }

    public void setMoonIlluminationPhase(double moonIlluminationPhase) {
        this.moonIlluminationPhase = moonIlluminationPhase;
    }

    public double getMoonPhaseDistance() {
        return moonPhaseDistance;
    }

    public void setMoonPhaseDistance(double moonPhaseDistance) {
        this.moonPhaseDistance = moonPhaseDistance;
    }

    public ZonedDateTime getMoonPhaseTime() {
        return moonPhaseTime;
    }

    public void setMoonPhaseTime(ZonedDateTime moonPhaseTime) {
        this.moonPhaseTime = moonPhaseTime;
    }

    public boolean isMoonPhaseIsMicroMoon() {
        return moonPhaseIsMicroMoon;
    }

    public void setMoonPhaseIsMicroMoon(boolean moonPhaseIsMicroMoon) {
        this.moonPhaseIsMicroMoon = moonPhaseIsMicroMoon;
    }

    public boolean isMoonPhaseIsSuperMoon() {
        return moonPhaseIsSuperMoon;
    }

    public void setMoonPhaseIsSuperMoon(boolean moonPhaseIsSuperMoon) {
        this.moonPhaseIsSuperMoon = moonPhaseIsSuperMoon;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }
}
