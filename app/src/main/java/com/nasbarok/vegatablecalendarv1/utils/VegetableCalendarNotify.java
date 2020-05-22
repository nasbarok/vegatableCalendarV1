package com.nasbarok.vegatablecalendarv1.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;

import java.util.Calendar;

public class VegetableCalendarNotify {

    public void AddEvent(Context context, Calendar beginTime, Calendar endTime, String location, String description, String title, String contacts) {

        ContentResolver cr = context.getContentResolver();

        Intent intent = null;

        intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, description)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.Reminders.MINUTES, 0)
                .putExtra(Intent.EXTRA_EMAIL, contacts);
        context.startActivity(intent);
    }
}


