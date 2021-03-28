package com.nasbarok.vegatablecalendarv1.utils;

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

    //Output


    public SunAndMoon(double latitude, double longitude, ZonedDateTime dateTime){
        latitude = latitude;
        longitude = longitude;

                SunTimes times = SunTimes.compute()
                .on(dateTime)   // set a date
                .at(latitude, longitude)   // set a location
                .execute();     // get the results
        System.out.println("Sunrise: " + times.getRise());
        System.out.println("Sunset: " + times.getSet());
    }


}
