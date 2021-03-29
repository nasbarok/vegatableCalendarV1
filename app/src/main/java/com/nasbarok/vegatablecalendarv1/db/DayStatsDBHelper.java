package com.nasbarok.vegatablecalendarv1.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nasbarok.vegatablecalendarv1.model.Classification;
import com.nasbarok.vegatablecalendarv1.model.DayStats;
import com.nasbarok.vegatablecalendarv1.model.MyVegetableGarden;
import com.nasbarok.vegatablecalendarv1.model.UserInformations;
import com.nasbarok.vegatablecalendarv1.model.VegetableCalendar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DayStatsDBHelper extends SQLiteOpenHelper {

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "365daysDatas.db";
    public static final String TABLE_NAME = "days_data";
    public static final String COLUMN1_ID_DAY = "day_data_id";
    public static final String COLUMN2_currentDay = "currentDay";
    public static final String COLUMN3_sunRise = "sunRise";
    public static final String COLUMN4_sunSet = "sunSet";
    public static final String COLUMN5_sunNadir = "sunNadir";
    public static final String COLUMN6_sunNoon = "sunNoon";
    public static final String COLUMN7_sunAltitude = "sunAltitude";
    public static final String COLUMN8_sunAzimuth = "sunAzimuth";
    public static final String COLUMN9_sunDistance = "sunDistance";
    public static final String COLUMN10_sunParallacticAngle = "sunParallacticAngle";
    public static final String COLUMN11_moonAltitude = "moonAltitude";
    public static final String COLUMN12_moonAzimuth = "moonAzimuth";
    public static final String COLUMN13_moonDistance = "moonDistance";
    public static final String COLUMN14_moonParallacticAngle = "moonParallacticAngle";
    public static final String COLUMN15_moonIlluminationAngle = "moonIlluminationAngle";
    public static final String COLUMN16_moonIlluminationFraction = "moonIlluminationFraction";
    public static final String COLUMN17_moonIlluminationPhase = "moonIlluminationPhase";
    public static final String COLUMN18_moonPhaseDistance = "moonPhaseDistance";
    public static final String COLUMN19_moonPhaseTime = "moonPhaseTime";
    public static final String COLUMN20_moonPhaseIsMicroMoon = "moonPhaseIsMicroMoon";
    public static final String COLUMN21_moonPhaseIsSuperMoon = "moonPhaseIsSuperMoon";

    private Context context;

    //initialize the database
    //constructor
    public DayStatsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public void createDB(List<DayStats> dayStats){
        //vegetable calendar database
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + COLUMN1_ID_DAY +
                " INTEGER , " + COLUMN2_currentDay + " TEXT , " + COLUMN3_sunRise + " TEXT , "
                + COLUMN4_sunSet + " TEXT ," + COLUMN5_sunNadir + " TEXT ," + COLUMN6_sunNoon + " TEXT ,"
                + COLUMN7_sunAltitude + " REAL ," + COLUMN8_sunAzimuth + " REAL ," + COLUMN9_sunDistance + " REAL ,"
                + COLUMN10_sunParallacticAngle + " REAL ," + COLUMN11_moonAltitude + " REAL ," + COLUMN12_moonAzimuth + " REAL ,"
                + COLUMN13_moonDistance + " REAL ," + COLUMN14_moonParallacticAngle + " REAL ," + COLUMN15_moonIlluminationAngle + " REAL ,"
                + COLUMN16_moonIlluminationFraction + " REAL ," + COLUMN17_moonIlluminationPhase + " REAL ," + COLUMN18_moonPhaseDistance + " REAL ,"
                + COLUMN19_moonPhaseTime + " TEXT ," + COLUMN20_moonPhaseIsMicroMoon + " INTEGER ,"+COLUMN21_moonPhaseIsSuperMoon+" INTEGER )";
        db.execSQL(CREATE_TABLE);


       for(DayStats stats:dayStats) {
            String insertCommand = String
                    .format("insert into "+TABLE_NAME+"("+COLUMN1_ID_DAY+", "+COLUMN2_currentDay+", "+COLUMN3_sunRise+", "+COLUMN4_sunSet+", "+COLUMN5_sunNadir+", "+COLUMN6_sunNoon+", "+COLUMN7_sunAltitude+", "+COLUMN8_sunAzimuth+", "+COLUMN9_sunDistance+", "+COLUMN10_sunParallacticAngle+", "+COLUMN11_moonAltitude+", "
                                    +COLUMN12_moonAzimuth+", "+COLUMN13_moonDistance+", "+COLUMN14_moonParallacticAngle+", "+COLUMN15_moonIlluminationAngle+", "+COLUMN16_moonIlluminationFraction+", "+COLUMN17_moonIlluminationPhase+", "
                                    +COLUMN18_moonPhaseDistance+", "+COLUMN19_moonPhaseTime+", "+COLUMN20_moonPhaseIsMicroMoon+", "+COLUMN21_moonPhaseIsSuperMoon+") values(\"%d\",\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\")",
                            stats.getDayNumber(), stats.getCurrentDay().toString(), stats.getSunRise().toString(),stats.getSunSet().toString(),stats.getSunNadir().toString(),stats.getSunNoon().toString(),stats.getSunAltitude(),stats.getSunAzimuth(),stats.getSunDistance(),stats.getSunParallacticAngle(),stats.getMoonAltitude(),
                            stats.getMoonAzimuth(),stats.getMoonDistance(),stats.getMoonParallacticAngle(),stats.getMoonIlluminationAngle(),stats.getMoonIlluminationFraction(),stats.getMoonIlluminationPhase()
                            , stats.getMoonPhaseDistance(),stats.getMoonPhaseTime(),booleanToInt(stats.isMoonPhaseIsMicroMoon()),booleanToInt(stats.isMoonPhaseIsSuperMoon()));
            db.execSQL(insertCommand);
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //UTILS
    public DayStats mapFromCursor(Cursor c){
        DayStats stats = new DayStats();/*
        vegetableCalendar.setVegetableCalendarId(c.getInt(0));
        vegetableCalendar.setVegetableCalendarName(c.getString(1));*/
        return stats;
    }

    public Classification mapFromCursorKoeppen(Cursor c){
        Classification classification = new Classification();
        classification.setIdTableClsDetails(c.getInt(0));
        classification.setName(c.getString(1));
        classification.setNameLong(c.getString(2));
        classification.setDesc(c.getString(3));
        return classification;
    }

    public int booleanToInt(Boolean bool){
        if(bool){
            return 1;
        }else{
            return 0;
        }
    }

    public String getNameFromCurrentLanguage(String frenchVegetableName, String englishVegetableName){
        if(Locale.getDefault().getDisplayLanguage().equals("french")){
            return frenchVegetableName;
        } else{
            return englishVegetableName;
        }
    }
}
