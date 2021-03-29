package com.nasbarok.vegatablecalendarv1.utils;

import org.shredzone.commons.suncalc.MoonIllumination;
import org.shredzone.commons.suncalc.MoonPhase;
import org.shredzone.commons.suncalc.MoonPosition;
import org.shredzone.commons.suncalc.SunPosition;
import org.shredzone.commons.suncalc.SunTimes;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class SunAndMoon {
    //Latitude. Valid values range from [-90, 90]
    private double latitude;
    //Longitude. Valid values range from [-180, 180]
    private double longitude;
    //Date used for calculations.
    private Calendar calendar;
    private Date startDate;
    private ZonedDateTime dateTime ; // date, time and timezone of calculation
    private ZonedDateTime sunRise;
    private ZonedDateTime sunSet;
    //soleil completement opposé
    private ZonedDateTime sunNadir;
    //soleil au zenith
    private ZonedDateTime sunNoon;
    private double sunAltitude;
    private double sunAzimuth;
    private double sunDistance;
    private double sunParallacticAngle;
    private double moonAltitude;
    private double moonAzimuth;
    private double moonDistance;
    private double moonParallacticAngle;
    /*
            Constant	Description	Angle
            NEW_MOON	Moon is not illuminated (new moon). This is the default.	0°
            FIRST_QUARTER	Half of the waxing moon is illuminated.	90°
            FULL_MOON	Moon is fully illuminated.	180°
            LAST_QUARTER	Half of the waning moon is illuminated.	270°
     */
    private double moonIlluminationAngle;
    private double moonIlluminationFraction;
    private double moonIlluminationPhase;
    private double moonPhaseDistance;
    private ZonedDateTime moonPhaseTime;
    private boolean moonPhaseIsMicroMoon;
    private boolean moonPhaseIsSuperMoon;
    //Output


    public SunAndMoon(double latitude, double longitude, ZonedDateTime dateTime){
        latitude = latitude;
        longitude = longitude;

        SunTimes sunTimes = SunTimes.compute()
        .on(dateTime)   // set a date
        .at(latitude, longitude)   // set a location
        .execute();     // get the results
        sunRise = sunTimes.getRise();
        sunSet = sunTimes.getSet();
        sunNadir = sunTimes.getNadir();
        sunNoon = sunTimes.getNoon();

        SunPosition sunPosition = SunPosition.compute().on(dateTime).at(latitude,longitude).execute();
        sunAltitude = sunPosition.getAltitude();
        sunAzimuth = sunPosition.getAzimuth();
        sunParallacticAngle = sunPosition.getTrueAltitude();
        sunDistance = sunPosition.getDistance();

        MoonPosition moonPosition = MoonPosition.compute().on(dateTime).at(latitude,longitude).execute();
        moonAltitude = moonPosition.getAltitude();
        moonAzimuth = moonPosition.getAzimuth();
        moonDistance = moonPosition.getDistance();
        moonParallacticAngle = moonPosition.getParallacticAngle();

        MoonIllumination moonIllumination = MoonIllumination.compute().on(dateTime).execute();
        moonIlluminationAngle = moonIllumination.getAngle();
        moonIlluminationFraction = moonIllumination.getFraction();
        moonIlluminationPhase = moonIllumination.getPhase();

        MoonPhase moonPhase = MoonPhase.compute().on(dateTime).execute();
        moonPhaseDistance = moonPhase.getDistance();
        moonPhaseTime = moonPhase.getTime();
        moonPhaseIsMicroMoon = moonPhase.isMicroMoon();
        moonPhaseIsSuperMoon = moonPhase.isSuperMoon();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
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
}
