package com.example.shaban.alarmdemo.helper;

import android.content.Context;
import android.content.SharedPreferences;



public class AlarmSharedPreferences {

    private static AlarmSharedPreferences instance;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private AlarmSharedPreferences() {

    }


    public static AlarmSharedPreferences init(Context context) {
        if (instance == null) {
            instance = new AlarmSharedPreferences();
        }

        instance.pref = context.getSharedPreferences("Alarm", 0); // 0 - for private mode
        instance.editor = instance.pref.edit();

        return instance;
    }

    public void store(String key, String data) {
        editor.putString(key, data);
        editor.commit();
    }


    public void store(String key, int data) {
        editor.putInt(key, data);
        editor.commit();
    }


    public void store(String key, long data) {
        editor.putLong(key, data);
        editor.commit();
    }


    public void store(String key, float data) {
        editor.putFloat(key, data);
        editor.commit();
    }


    public void store(String key, boolean data) {
        editor.putBoolean(key, data);
        editor.commit();
    }


    public String get(String key, String defaultValue) {
        return pref.getString(key, defaultValue);
    }


    public int get(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }


    public long get(String key, long defaultValue) {
        return pref.getLong(key, defaultValue);
    }


    public float get(String key, float defaultValue) {
        return pref.getFloat(key, defaultValue);
    }


    public boolean get(String key, boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }


    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }
}
