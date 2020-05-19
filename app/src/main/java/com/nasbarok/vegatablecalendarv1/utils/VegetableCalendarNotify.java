package com.nasbarok.vegatablecalendarv1.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.CalendarContract;

import java.util.Calendar;
import java.util.Date;

public class VegetableCalendarNotify {
    public void createNotifyCalendar(Context context, Calendar beginTime, Calendar endTime, String location, String description, String title){
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, description)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "na.ouahit@gmail.com");

        context.startActivity(intent);
    }
}
