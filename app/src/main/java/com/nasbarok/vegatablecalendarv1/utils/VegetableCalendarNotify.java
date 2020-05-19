package com.nasbarok.vegatablecalendarv1.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.CalendarContract;

import com.nasbarok.vegatablecalendarv1.R;

import java.util.Calendar;
import java.util.Date;

public class VegetableCalendarNotify {

    public void createNotifyCalendar(Context context, Calendar beginTime, Calendar endTime, String location, String description, String title){
        int argbValue = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            argbValue = Color.toArgb(context.getResources().getColor(R.color.table_color_iseeding));
        }
        Intent intent = null;

            intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, title)
                    .putExtra(CalendarContract.Events.DESCRIPTION, description)
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                    .putExtra(CalendarContract.Events.EVENT_COLOR_KEY, 89)
                    .putExtra(CalendarContract.Reminders.MINUTES, 1)
                    .putExtra(Intent.EXTRA_EMAIL, "blabla@gmail.com,blublu@gmail.com");

        context.startActivity(intent);
    }
}
