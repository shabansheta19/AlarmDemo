package com.example.shaban.alarmdemo.helper;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.shaban.alarmdemo.MainActivity;
import com.example.shaban.alarmdemo.model.Alarm;
import com.example.shaban.alarmdemo.service.AlarmService;

import java.util.Calendar;

public class AlarmManager {

    private static AlarmManager instance;

    private Context context;

    private AlarmManager() {

    }

    public static AlarmManager getInstance(Context context) {
        if(instance == null) {
            instance = new AlarmManager();
        }

        instance.context = context;

        return instance;
    }


    private android.app.AlarmManager getAlarmManager() {
        return (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }


    private PendingIntent getAlarmIntent(Alarm alarm) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(Constant.START_ALARM);
        intent.putExtra(Constant.ALARM, alarm);

        return PendingIntent.getService(context, alarm.getAlarmId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public void setAlarm(Alarm alarm) {
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        alarmTime.set(Calendar.MINUTE, alarm.getMinute());
        alarmTime.set(Calendar.SECOND, 0);
        alarmTime.set(Calendar.MILLISECOND, 0);

        Calendar now = Calendar.getInstance();
        if(alarmTime.before(now)) {
            alarmTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        getAlarmManager().set(android.app.AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), getAlarmIntent(alarm));
    }


    public void cancelAlarm(Alarm alarm) {
        getAlarmManager().cancel(getAlarmIntent(alarm));
        MainActivity.db.deleteAlarm(alarm);
    }


}
