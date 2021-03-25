package com.nasbarok.vegatablecalendarv1.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

public class VegetableCalendarDBHelper extends SQLiteOpenHelper {

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vegetableCalendarDB.db";
    public static final String TABLE_NAME = "vegetable_calendar";
    public static final String COLUMN_ID_VEGETABLE_CALENDAR = "vegetable_calendar_id";
    public static final String COLUMN_NAME1 = "vegetableCalendarName";
    public static final String COLUMN_NAME2 = "vegetableCalendarJanuary";
    public static final String COLUMN_NAME3 = "vegetableCalendarFebruary";
    public static final String COLUMN_NAME4 = "vegetableCalendarMarch";
    public static final String COLUMN_NAME5 = "vegetableCalendarApril";
    public static final String COLUMN_NAME6 = "vegetableCalendarMay";
    public static final String COLUMN_NAME7 = "vegetableCalendarJune";
    public static final String COLUMN_NAME8 = "vegetableCalendarJuly";
    public static final String COLUMN_NAME9 = "vegetableCalendarAugust";
    public static final String COLUMN_NAME10 = "vegetableCalendarSeptember";
    public static final String COLUMN_NAME11 = "vegetableCalendarOctober";
    public static final String COLUMN_NAME12 = "vegetableCalendarNovember";
    public static final String COLUMN_NAME13 = "vegetableCalendarDecember";
    public static final String TABLE_NAME_VEGETABLE_GARDEN = "my_vegetable_garden";
    public static final String COLUMN_VEGETABLE_GARDEN_NAME1 = "outdoorSeedingNotify";
    public static final String COLUMN_VEGETABLE_GARDEN_NAME2 = "indoorSeedingNotify";
    public static final String COLUMN_VEGETABLE_GARDEN_NAME3 = "transplationNotify";
    public static final String COLUMN_VEGETABLE_GARDEN_NAME4 = "harvestNotify";
    public static final String TABLE_NAME_USER_INFORMATION = "user_informations";
    public static final String COLUMN_USER_INFORMATION_ID = "user_info_id";
    public static final String COLUMN_USER_INFORMATION_NAME1 = "mails";
    public static final String COLUMN_USER_INFORMATION_NAME2 = "startTime";
    public static final String COLUMN_USER_INFORMATION_NAME3 = "endTime";
    public static final String COLUMN_USER_INFORMATION_CITY = "city";
    public static final String COLUMN_USER_INFORMATION_CLIMATE = "climate";

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
        //vegetable calendar database
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + COLUMN_ID_VEGETABLE_CALENDAR +
                " INTEGER , " + COLUMN_NAME1 + " TEXT , " + COLUMN_NAME2 + " TEXT , " + COLUMN_NAME3 + " TEXT ," + COLUMN_NAME4 + " TEXT ," + COLUMN_NAME5 + " TEXT ," + COLUMN_NAME6 + " TEXT ," + COLUMN_NAME7 + " TEXT ," + COLUMN_NAME8 + " TEXT ," + COLUMN_NAME9 + " TEXT ," + COLUMN_NAME10 + " TEXT ," + COLUMN_NAME11 + " TEXT ," + COLUMN_NAME12 + " TEXT ," + COLUMN_NAME13 + " TEXT )";
        db.execSQL(CREATE_TABLE);
        if(getVegetableCalendars().size()==0){
            try {
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String line;
                String[] values;
                while ((line = bufferedReader.readLine()) != null) {
                    values = line.split(",");
                    String vegetableName = getNameFromCurrentLanguage(values[1],values[2]);
                    String insertCommand = String
                            .format("insert into "+TABLE_NAME+"("+COLUMN_ID_VEGETABLE_CALENDAR+", "+COLUMN_NAME1+", "+COLUMN_NAME2+", "+COLUMN_NAME3+", "+COLUMN_NAME4+", "+COLUMN_NAME5+", "+COLUMN_NAME6+", "+COLUMN_NAME7+", "+COLUMN_NAME8+", "+COLUMN_NAME9+", "+COLUMN_NAME10+", "+COLUMN_NAME11+", "+COLUMN_NAME12+", "+COLUMN_NAME13+") values(\"%d\",\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\")",
                                    Integer.parseInt(values[0]), vegetableName, values[3],values[4],values[5],values[6],values[7],values[8],values[9],values[10],values[11],values[12],values[13],values[14]);
                    db.execSQL(insertCommand);
                }

            } catch (IOException e) {
                Log.e("TBCAE", "Failed to open data input file");
                e.printStackTrace();
            }
        }
        //My vegetable garden local
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_VEGETABLE_GARDEN + "( " + COLUMN_ID_VEGETABLE_CALENDAR + " INTEGER, " + COLUMN_VEGETABLE_GARDEN_NAME1+" TEXT, "+ COLUMN_VEGETABLE_GARDEN_NAME2+" TEXT, "+ COLUMN_VEGETABLE_GARDEN_NAME3+" TEXT, "+ COLUMN_VEGETABLE_GARDEN_NAME4+" TEXT )";
        db.execSQL(CREATE_TABLE);

        //My user informations
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USER_INFORMATION + "( " + COLUMN_USER_INFORMATION_ID + " INTEGER, " + COLUMN_USER_INFORMATION_NAME1 + " TEXT, " + COLUMN_USER_INFORMATION_NAME2+" TEXT," + COLUMN_USER_INFORMATION_NAME3+" TEXT," + COLUMN_USER_INFORMATION_CITY+" TEXT, "+ COLUMN_USER_INFORMATION_CLIMATE+" TEXT )";
        db.execSQL(CREATE_TABLE);


        if(!userInformationExist()){
            String insertCommand = String
                    .format("insert into "+TABLE_NAME_USER_INFORMATION+"( " + COLUMN_USER_INFORMATION_ID + ", "+COLUMN_USER_INFORMATION_NAME1+", "+COLUMN_USER_INFORMATION_NAME2+", "+COLUMN_USER_INFORMATION_NAME3+", "+COLUMN_USER_INFORMATION_CITY+", "+COLUMN_USER_INFORMATION_CLIMATE+") values(\"%d\",\"%s\", \"%s\", \"%s\", \"%s\", \"%s\")",
                           1,"","10h30","17h30","","");
            db.execSQL(insertCommand);
        }

        db.close();
    }

    public boolean userInformationExist(){
        String query = "Select * FROM " + TABLE_NAME_USER_INFORMATION + " WHERE " + COLUMN_USER_INFORMATION_ID + " = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UserInformations userInformations = new UserInformations();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            userInformations.setMails(cursor.getString(0));
            userInformations.setStartTime(cursor.getString(1));
            userInformations.setEndTime(cursor.getString(2));
            cursor.close();
        } else {
            userInformations = null;
        }
        return userInformations!=null;
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
                VegetableCalendar vegetableCalendar;
                vegetableCalendar = mapFromCursor(c);
                // Adding user to the list
                vegetableCalendarList.add(vegetableCalendar);
            }while(c.moveToNext());
        }
        c.close();
        return vegetableCalendarList;
    }

    public VegetableCalendar getVegetableCalendarByName(String vegetableCalendarName) {

        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME1 + " LIKE " + "'%" + vegetableCalendarName + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        VegetableCalendar vegetableCalendar;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            vegetableCalendar = mapFromCursor(cursor);
            cursor.close();
        } else {
            vegetableCalendar = null;
        }
        db.close();
        return vegetableCalendar;
    }

    public VegetableCalendar findVegetableCalendarById(Integer vegetableCalendarId) {

        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID_VEGETABLE_CALENDAR + " = " +vegetableCalendarId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        VegetableCalendar vegetableCalendar;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            vegetableCalendar = mapFromCursor(cursor);
            cursor.close();
        } else {
            vegetableCalendar = null;
        }
        db.close();
        return vegetableCalendar;
    }

    public List <VegetableCalendar> findVegetableCalendar(String vegetableCalendarName) {
        List <VegetableCalendar> vegetableCalendarList=new ArrayList<VegetableCalendar>();
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME1 + " LIKE " + "'%" + vegetableCalendarName + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        // looping through all rows and adding to the list
        if(c.moveToFirst()){
            do{
                VegetableCalendar vegetableCalendar;
                vegetableCalendar = mapFromCursor(c);
                // Adding user to the list
                vegetableCalendarList.add(vegetableCalendar);
            }while(c.moveToNext());
        }
        c.close();
        return vegetableCalendarList;
    }

    public String addVegetableToMyVegetableGarden(VegetableCalendar vegetableCalendar) {
        if(!vegetableExistInMyVegetableGarden(vegetableCalendar)){
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID_VEGETABLE_CALENDAR, vegetableCalendar.getVegetableCalendarId());
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_NAME_VEGETABLE_GARDEN, null, values);
            db.close();
            return "OK";
        }else{
            return "KO";
        }

    }

    public String removeVegetableToMyVegetableGarden(MyVegetableGarden myVegetableGarden) {
        if(myVegetableGarden!=null){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME_VEGETABLE_GARDEN,COLUMN_ID_VEGETABLE_CALENDAR+ " = "+myVegetableGarden.getVegetableCalendarId(),null);
            db.close();
            return "OK";
        }else{
            return "KO";
        }

    }

    public String saveVegetableNotificationToMyVegetableGarden(MyVegetableGarden myVegetableGarden) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID_VEGETABLE_CALENDAR, myVegetableGarden.getVegetableCalendarId());
            values.put(COLUMN_VEGETABLE_GARDEN_NAME1, myVegetableGarden.getIndoorSeedingNotify());
            values.put(COLUMN_VEGETABLE_GARDEN_NAME2, myVegetableGarden.getOutdoorSeedingNotify());
            values.put(COLUMN_VEGETABLE_GARDEN_NAME3, myVegetableGarden.getTransplationNotify());
            values.put(COLUMN_VEGETABLE_GARDEN_NAME4, myVegetableGarden.getHarvestNotify());

            SQLiteDatabase db = this.getWritableDatabase();
            db.update(TABLE_NAME_VEGETABLE_GARDEN,values,COLUMN_ID_VEGETABLE_CALENDAR+ " = "+myVegetableGarden.getVegetableCalendarId(),null);
            db.close();
            return "OK";

    }

    public List<Integer> getMyVegetableGarden(){
        List <Integer> myVegetableGardenList=new ArrayList<Integer>();

        // Select All Query
        String selectQuery="SELECT * FROM " + TABLE_NAME_VEGETABLE_GARDEN;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery(selectQuery, null);

        // looping through all rows and adding to the list
        if(c.moveToFirst()){
            do{
                // Adding user to the list
                myVegetableGardenList.add(c.getInt(0));
            }while(c.moveToNext());
        }
        c.close();

        db.close();
        return myVegetableGardenList;
    }

    public MyVegetableGarden getMyVegetableGardenNotify(int vegetalNumber){
        MyVegetableGarden myVegetableGarden = new MyVegetableGarden();
        // Select All Query
        String selectQuery="SELECT * FROM " + TABLE_NAME_VEGETABLE_GARDEN + " WHERE "+ COLUMN_ID_VEGETABLE_CALENDAR+" = "+vegetalNumber;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery(selectQuery, null);

        // looping through all rows and adding to the list
        if(c.moveToFirst()){
            do{
                myVegetableGarden.setVegetableCalendarId(c.getInt(0));
                myVegetableGarden.setIndoorSeedingNotify(c.getString(1));
                myVegetableGarden.setOutdoorSeedingNotify(c.getString(2));
                myVegetableGarden.setTransplationNotify(c.getString(3));
                myVegetableGarden.setHarvestNotify(c.getString(4));
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return myVegetableGarden;
    }

    public boolean vegetableExistInMyVegetableGarden(VegetableCalendar vegetableCalendar){
        List <Integer> myVegetableGardenList=getMyVegetableGarden();
        return myVegetableGardenList.contains(vegetableCalendar.getVegetableCalendarId());
    }

    public List<VegetableCalendar> fillMyVegetableGarden(){
        List<Integer> myVegetableList = getMyVegetableGarden();
        List<VegetableCalendar> myVegetableCaldendarList = new ArrayList<VegetableCalendar>();
        for (Integer vegetableCalendarId : myVegetableList) {
            myVegetableCaldendarList.add(findVegetableCalendarById(vegetableCalendarId));
        }
        return myVegetableCaldendarList;
    }

    public UserInformations getUserInformations(){
        UserInformations userInformations = new UserInformations();
        // Select All Query
        String selectQuery="SELECT * FROM " + TABLE_NAME_USER_INFORMATION +" WHERE " + COLUMN_USER_INFORMATION_ID + " = 1 ";

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery(selectQuery, null);

        // looping through all rows and adding to the list
        if(c.moveToFirst()){
            do{
                userInformations.setMails(c.getString(1));
                userInformations.setStartTime(c.getString(2));
                userInformations.setEndTime(c.getString(3));
                userInformations.setCity(c.getString(4));
                userInformations.setClimate(c.getString(5));
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return userInformations;
    }

    public String saveUserInformations(UserInformations userInformations) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_INFORMATION_NAME1, userInformations.getMails());
        values.put(COLUMN_USER_INFORMATION_NAME2, userInformations.getStartTime());
        values.put(COLUMN_USER_INFORMATION_NAME3, userInformations.getEndTime());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME_USER_INFORMATION,values,COLUMN_USER_INFORMATION_ID+ " = 1 ",null);
        db.close();
        return "OK";

    }

    //UTILS
    public VegetableCalendar mapFromCursor(Cursor c){
        VegetableCalendar vegetableCalendar = new VegetableCalendar();
        vegetableCalendar.setVegetableCalendarId(c.getInt(0));
        vegetableCalendar.setVegetableCalendarName(c.getString(1));
        vegetableCalendar.setVegetableCalendarJanuary(c.getString(2));
        vegetableCalendar.setVegetableCalendarFebruary(c.getString(3));
        vegetableCalendar.setVegetableCalendarMarch(c.getString(4));
        vegetableCalendar.setVegetableCalendarApril(c.getString(5));
        vegetableCalendar.setVegetableCalendarMay(c.getString(6));
        vegetableCalendar.setVegetableCalendarJune(c.getString(7));
        vegetableCalendar.setVegetableCalendarJuly(c.getString(8));
        vegetableCalendar.setVegetableCalendarAugust(c.getString(9));
        vegetableCalendar.setVegetableCalendarSeptember(c.getString(10));
        vegetableCalendar.setVegetableCalendarOctober(c.getString(11));
        vegetableCalendar.setVegetableCalendarNovember(c.getString(12));
        vegetableCalendar.setVegetableCalendarDecember(c.getString(13));
        return vegetableCalendar;
    }

    public String getNameFromCurrentLanguage(String frenchVegetableName, String englishVegetableName){
        if(Locale.getDefault().getLanguage().equals("fr")){
            return frenchVegetableName;
        } else{
            return englishVegetableName;
        }
    }
}
