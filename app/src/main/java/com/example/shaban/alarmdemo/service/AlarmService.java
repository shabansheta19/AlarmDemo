package com.example.shaban.alarmdemo.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.shaban.alarmdemo.AlarmActivity;
import com.example.shaban.alarmdemo.helper.AlarmManager;
import com.example.shaban.alarmdemo.helper.Constant;
import com.example.shaban.alarmdemo.model.Alarm;


public class AlarmService extends IntentService {
    private Alarm alarm;

    public AlarmService() {
        super("AlarmService");
    }


    public static void startAlarmService(Context context, Alarm alarm) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(Constant.START_ALARM);
        intent.putExtra(Constant.ALARM, alarm);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (action.equals(Constant.START_ALARM)) {
                alarm = intent.getParcelableExtra(Constant.ALARM);
                handleStartAlarm();
            }
        }
    }


    private void handleStartAlarm() {
        try {
            Intent intent = new Intent(this, AlarmActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.ALARM, alarm);
            startActivity(intent);

            AlarmManager.getInstance(this).setAlarm(alarm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
