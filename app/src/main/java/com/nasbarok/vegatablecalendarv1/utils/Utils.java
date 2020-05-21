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

    public int[] splitTimeValue(String value){
        String[] strings = value.split("h");
        int[] ints = new int[2];
        ints[0] = Integer.valueOf(strings[0]);
        ints[1] = Integer.valueOf(strings[1]);
        return ints;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
