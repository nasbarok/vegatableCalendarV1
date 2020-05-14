package com.nasbarok.vegatablecalendarv1.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nasbarok.vegatablecalendarv1.model.VegetableCalendar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VegetableCalendarDBHelper extends SQLiteOpenHelper {

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vegetableCalendarDB.db";
    public static final String TABLE_NAME = "vegetable_calendar";
    public static final String COLUMN_ID = "vegetable_calendar_id";
    public static final String COLUMN_NAME1 = "vegetableCalendarJanuary";
    public static final String COLUMN_NAME2 = "vegetableCalendarFebruary";
    public static final String COLUMN_NAME3 = "vegetableCalendarMarch";
    public static final String COLUMN_NAME4 = "vegetableCalendarApril";
    public static final String COLUMN_NAME5 = "vegetableCalendarMay";
    public static final String COLUMN_NAME6 = "vegetableCalendarJune";
    public static final String COLUMN_NAME7 = "vegetableCalendarJuly";
    public static final String COLUMN_NAME8 = "vegetableCalendarAugust";
    public static final String COLUMN_NAME9 = "vegetableCalendarSeptember";
    public static final String COLUMN_NAME10 = "vegetableCalendarOctober";
    public static final String COLUMN_NAME11 = "vegetableCalendarNovember";
    public static final String COLUMN_NAME12 = "vegetableCalendarDecember";

    private Context context;

    //initialize the database
   /* public VegetableCalendarDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }*/

    //constructor
    public VegetableCalendarDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public void createDB(InputStream inputStream){
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + COLUMN_ID +
                "INTEGER PRIMARYKEY," + COLUMN_NAME1 + " TEXT , " + COLUMN_NAME2 + " TEXT , " + COLUMN_NAME3 + " TEXT ," + COLUMN_NAME4 + " TEXT ," + COLUMN_NAME5 + " TEXT ," + COLUMN_NAME6 + " TEXT ," + COLUMN_NAME7 + " TEXT ," + COLUMN_NAME8 + " TEXT ," + COLUMN_NAME9 + " TEXT ," + COLUMN_NAME10 + " TEXT ," + COLUMN_NAME11 + " TEXT ," + COLUMN_NAME12 + " TEXT )";
        db.execSQL(CREATE_TABLE);
        if(getVegetableCalendars().size()==0){
            try {
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String line;
                String[] values;
                while ((line = bufferedReader.readLine()) != null) {
                    values = line.split(",");
                    String insertCommand = String
                            .format("insert into "+TABLE_NAME+"("+COLUMN_NAME1+", "+COLUMN_NAME2+", "+COLUMN_NAME3+", "+COLUMN_NAME4+", "+COLUMN_NAME5+", "+COLUMN_NAME6+", "+COLUMN_NAME7+", "+COLUMN_NAME8+", "+COLUMN_NAME9+", "+COLUMN_NAME10+", "+COLUMN_NAME11+", "+COLUMN_NAME12+") values(\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\")",
                                    values[0], values[1], values[2],values[3],values[4],values[5],values[6],values[7],values[8],values[9],values[10],values[11]);
                    db.execSQL(insertCommand);
                }

            } catch (IOException e) {
                Log.e("TBCAE", "Failed to open data input file");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String loadHandler() {
        String result = "";
        String query = "Select*FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public List<VegetableCalendar> getVegetableCalendars(){
        List <VegetableCalendar> vegetableCalendarList=new ArrayList<VegetableCalendar>();

        // Select All Query
        String selectQuery="SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery(selectQuery, null);

        // looping through all rows and adding to the list
        if(c.moveToFirst()){
            do{
                VegetableCalendar vegetableCalendar=new VegetableCalendar();
                vegetableCalendar.setVegetableCalendarId(c.getInt(0));
                vegetableCalendar.setVegetableCalendarName(c.getString(1));
                vegetableCalendar.setVegetableCalendarName(c.getString(2));
                vegetableCalendar.setVegetableCalendarJanuary(c.getString(3));
                vegetableCalendar.setVegetableCalendarFebruary(c.getString(4));
                vegetableCalendar.setVegetableCalendarMarch(c.getString(5));
                vegetableCalendar.setVegetableCalendarApril(c.getString(6));
                vegetableCalendar.setVegetableCalendarMay(c.getString(7));
                vegetableCalendar.setVegetableCalendarJune(c.getString(8));
                vegetableCalendar.setVegetableCalendarJuly(c.getString(9));
                vegetableCalendar.setVegetableCalendarAugust(c.getString(10));
                vegetableCalendar.setVegetableCalendarSeptember(c.getString(11));
                vegetableCalendar.setVegetableCalendarOctober(c.getString(12));
                // Adding user to the list
                vegetableCalendarList.add(vegetableCalendar);
            }while(c.moveToNext());
        }
        c.close();
        return vegetableCalendarList;
    }

    public void addHandler(VegetableCalendar vegetableCalendar) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, vegetableCalendar.getVegetableCalendarId());
        values.put(COLUMN_NAME1, vegetableCalendar.getVegetableCalendarName());
        //..
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public VegetableCalendar findHandler(String vegetableCalendarName) {

        String query = "Select * FROM " + TABLE_NAME + "WHERE" + COLUMN_NAME1 + " = " + "'" + vegetableCalendarName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        VegetableCalendar vegetableCalendar = new VegetableCalendar();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            vegetableCalendar.setVegetableCalendarId(Integer.parseInt(cursor.getString(0)));
            vegetableCalendar.setVegetableCalendarName(cursor.getString(1));
            cursor.close();
        } else {
            vegetableCalendar = null;
        }
        db.close();
        return vegetableCalendar;
    }
}
