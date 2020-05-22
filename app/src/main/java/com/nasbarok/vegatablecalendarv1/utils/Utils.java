package com.nasbarok.vegatablecalendarv1.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Switch;
import android.widget.Toast;

import com.nasbarok.vegatablecalendarv1.model.VegetableCalendar;

import java.util.Calendar;

public class Utils {

    public Calendar setCalendar(VegetableCalendar vegetableCalendar, String typeOfNotification){
        Calendar calendar = Calendar.getInstance();
        Boolean monthSetted = false;
            Month january = Month.JANUARY;
            if(vegetableCalendar.getVegetableCalendarJanuary().contains(typeOfNotification)&&!vegetableCalendar.getVegetableCalendarJanuary().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,january.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,1);
                monthSetted=true;
            }
            if(vegetableCalendar.getVegetableCalendarJanuary().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,january.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,15);
                monthSetted=true;
            }
            Month february = Month.FEBRUARY;
            if(vegetableCalendar.getVegetableCalendarFebruary().contains(typeOfNotification)&&!vegetableCalendar.getVegetableCalendarFebruary().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,february.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,1);
                monthSetted=true;
            }
            if(vegetableCalendar.getVegetableCalendarFebruary().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,february.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,15);
                monthSetted=true;
            }
            Month march = Month.MARCH;
            if(vegetableCalendar.getVegetableCalendarMarch().contains(typeOfNotification)&&!vegetableCalendar.getVegetableCalendarMarch().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,march.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,1);
                monthSetted=true;
            }
            if(vegetableCalendar.getVegetableCalendarMarch().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,march.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,15);
                monthSetted=true;
            }
            Month april = Month.APRIL;
            if(vegetableCalendar.getVegetableCalendarApril().contains(typeOfNotification)&&!vegetableCalendar.getVegetableCalendarApril().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,april.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,1);
                monthSetted=true;
            }
            if(vegetableCalendar.getVegetableCalendarApril().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,april.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,15);
                monthSetted=true;
            }
            Month may =  Month.MAY;
            if(vegetableCalendar.getVegetableCalendarMay().contains(typeOfNotification)&&!vegetableCalendar.getVegetableCalendarMay().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,may.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,1);
                monthSetted=true;
            }
            if(vegetableCalendar.getVegetableCalendarMay().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,may.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,15);
                monthSetted=true;
            }
            Month jun = Month.JUN;
            if(vegetableCalendar.getVegetableCalendarJune().contains(typeOfNotification)&&!vegetableCalendar.getVegetableCalendarJune().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,jun.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,1);
                monthSetted=true;
            }
            if(vegetableCalendar.getVegetableCalendarJune().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,jun.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,15);
                monthSetted=true;
            }
            Month july = Month.JULY;
            if(vegetableCalendar.getVegetableCalendarJuly().contains(typeOfNotification)&&!vegetableCalendar.getVegetableCalendarJuly().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,july.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,1);
                monthSetted=true;
            }
            if(vegetableCalendar.getVegetableCalendarJuly().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,july.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,15);
                monthSetted=true;
            }
            Month august = Month.AUGUST;
            if(vegetableCalendar.getVegetableCalendarAugust().contains(typeOfNotification)&&!vegetableCalendar.getVegetableCalendarAugust().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,august.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,1);
                monthSetted=true;
            }
            if(vegetableCalendar.getVegetableCalendarAugust().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,august.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,15);
                monthSetted=true;
            }
            Month september = Month.SEPTEMBER;
            if(vegetableCalendar.getVegetableCalendarSeptember().contains(typeOfNotification)&&!vegetableCalendar.getVegetableCalendarSeptember().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,september.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,1);
                monthSetted=true;
            }
            if(vegetableCalendar.getVegetableCalendarSeptember().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,september.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,15);
                monthSetted=true;
            }
            Month october = Month.OCTOBER;
            if(vegetableCalendar.getVegetableCalendarOctober().contains(typeOfNotification)&&!vegetableCalendar.getVegetableCalendarOctober().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,october.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,1);
                monthSetted=true;
            }
            if(vegetableCalendar.getVegetableCalendarOctober().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,october.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,15);
                monthSetted=true;
            }
            Month november = Month.NOVEMBER;
            if(vegetableCalendar.getVegetableCalendarNovember().contains(typeOfNotification)&&!vegetableCalendar.getVegetableCalendarNovember().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,november.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,1);
                monthSetted=true;
            }
            if(vegetableCalendar.getVegetableCalendarNovember().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,november.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,15);
                monthSetted=true;
            }
            Month december = Month.DECEMBER;
            if(vegetableCalendar.getVegetableCalendarDecember().contains(typeOfNotification)&&!vegetableCalendar.getVegetableCalendarDecember().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,december.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,1);
                monthSetted=true;
            }
            if(vegetableCalendar.getVegetableCalendarDecember().contains(typeOfNotification+"AE")&&!monthSetted){
                calendar.set(Calendar.MONTH,december.ordinal());
                calendar.set(Calendar.DAY_OF_MONTH,15);
                monthSetted=true;
            }

            if(!testIfDateExpired(calendar)||!monthSetted){
                return calendar;
            }else{
                calendar.set(Calendar.YEAR,Calendar.getInstance().get(Calendar.YEAR)+1);
                return calendar;
            }

    }

    public Boolean testIfDateExpired(Calendar calendarToTest){
        Calendar now = Calendar.getInstance();
        if(now.after(calendarToTest)){
            return true;
        }
        return false;
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

    public boolean getSwitchCheckedByDate(String dateNotify) {
        if(dateNotify == null||dateNotify.equals("")||dateNotify.equals("false")){
            return false;
        }
        String[] strings = dateNotify.split(":");
        int year = Integer.valueOf(strings[0]);
        int month = Integer.valueOf(strings[1]);
        int dayOfMonth = Integer.valueOf(strings[2]);
        Calendar calendarNotify = Calendar.getInstance();
        calendarNotify.set(Calendar.YEAR,year);
        calendarNotify.set(Calendar.MONTH,month);
        calendarNotify.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        return !testIfDateExpired(calendarNotify);
    }
}
