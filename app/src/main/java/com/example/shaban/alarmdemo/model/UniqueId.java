package com.example.shaban.alarmdemo.model;

import android.content.Context;

import com.example.shaban.alarmdemo.helper.AlarmSharedPreferences;

import java.util.concurrent.atomic.AtomicInteger;

public class UniqueId {
    private static AtomicInteger id;
    private static final String LAST_ALARM_ID = "com.example.shaban.alarmdemo.UniqueId.LAST_ALARM_ID";

    private UniqueId() {

    }

    public static int generate(Context context) {
        if(id == null) {
            id = new AtomicInteger(AlarmSharedPreferences.init(context).get(LAST_ALARM_ID, 0));
        }
        int uniqueId = id.incrementAndGet();
        AlarmSharedPreferences.init(context).store(LAST_ALARM_ID, uniqueId);

        return uniqueId;
    }
}
