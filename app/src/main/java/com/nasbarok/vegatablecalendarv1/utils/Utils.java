package com.nasbarok.vegatablecalendarv1.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Calendar;

public class Utils {

    public Calendar setCalendar(String month,String monthValue, String type){
     return null;
    }

    public String setTimeFromDate(){
        Calendar beginTime = Calendar.getInstance();
        return null;
    }
    public String setTimeFromString(){
        return null;
    }

    public void notifyToast(String msg, Context context){
        Toast toast = Toast.makeText(context, " "+ msg+ " " , Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}
