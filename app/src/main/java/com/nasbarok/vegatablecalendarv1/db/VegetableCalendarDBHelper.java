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
    public static final String COLUMN_NAME1 = "vegetable_calendar_name";
    public static final String COLUMN_NAME2 = "vegetable_calendar_sowing";
    public static final String COLUMN_NAME3 = "vegetable_calendar_plantations";
    public static final String COLUMN_NAME4 = "vegetable_calendar_harvest";
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
                "INTEGER PRIMARYKEY," + COLUMN_NAME1 + " TEXT , " + COLUMN_NAME2 + " TEXT , " + COLUMN_NAME3 + " TEXT ," + COLUMN_NAME4 + " TEXT )";
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
                            .format("insert into "+TABLE_NAME+"("+COLUMN_NAME1+", "+COLUMN_NAME2+", "+COLUMN_NAME3+", "+COLUMN_NAME4+") values(\"%s\", \"%s\", \"%s\", \"%s\")",
                                    values[0], values[1], values[2],values[3]);
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
                vegetableCalendar.setVegetableCalendarHarvest(c.getString(2));
                vegetableCalendar.setVegetableCalendarPlantations(c.getString(3));
                vegetableCalendar.setVegetableCalendarSowing(c.getString(4));

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
