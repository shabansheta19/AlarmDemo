package com.example.shaban.alarmdemo.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shaban.alarmdemo.model.Alarm;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "alarmInfo";
    // Contacts table name
    private static final String TABLE_ALARMS = "alarms";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_HOUR = "hour";
    private static final String KEY_MINUTE= "minute";
    private static final String KEY_TONE = "tone";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ALARMS + "("
        + KEY_ID + " INTEGER,"
        + KEY_TITLE + " TEXT,"
        + KEY_HOUR + " INTEGER,"
        + KEY_MINUTE + " INTEGER,"
        + KEY_TONE + " INTEGER"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);
        // Creating tables again
        onCreate(db);
    }

    // Adding new alarm
    public void addAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, alarm.getAlarmId());
        values.put(KEY_TITLE, alarm.getAlarmTitle());
        values.put(KEY_HOUR, alarm.getHour());
        values.put(KEY_MINUTE, alarm.getMinute());
        values.put(KEY_TONE, alarm.getTone());

        // Inserting Row
        db.insert(TABLE_ALARMS, null, values);
        db.close(); // Closing database connection
    }

    // Getting one alarm
    public Alarm getAlarm(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ALARMS, new String[] { KEY_ID,
                        KEY_TITLE, KEY_HOUR, KEY_MINUTE, KEY_TONE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Alarm alarm = new Alarm();
        alarm.setAlarmId(Integer.parseInt(cursor.getString(0)));
        alarm.setAlarmTitle(cursor.getString(1));
        alarm.setHour(Integer.parseInt(cursor.getString(2)));
        alarm.setMinute(Integer.parseInt(cursor.getString(3)));
        alarm.setTone(Integer.parseInt(cursor.getString(4)));

        // return alarm
        return alarm;
    }

    // Getting All Alarms
    public List<Alarm> getAllAlarms() {
        List<Alarm> shopList = new ArrayList<Alarm>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ALARMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Alarm alarm = new Alarm();
                alarm.setAlarmId(Integer.parseInt(cursor.getString(0)));
                alarm.setAlarmTitle(cursor.getString(1));
                alarm.setHour(Integer.parseInt(cursor.getString(2)));
                alarm.setMinute(Integer.parseInt(cursor.getString(3)));
                alarm.setTone(Integer.parseInt(cursor.getString(4)));

                // Adding contact to list
                shopList.add(alarm);
            } while (cursor.moveToNext());
        }
        // return contact list
        return shopList;
    }

    // Updating a alarm
    public int updateAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, alarm.getAlarmId());
        values.put(KEY_TITLE, alarm.getAlarmTitle());
        values.put(KEY_HOUR, alarm.getHour());
        values.put(KEY_MINUTE, alarm.getMinute());
        values.put(KEY_TONE, alarm.getTone());
        // updating row
        return db.update(TABLE_ALARMS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(alarm.getAlarmId())});
    }

    // Deleting a alarm
    public void deleteAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARMS, KEY_ID + " = ?",
                new String[] { String.valueOf(alarm.getAlarmId()) });
        db.close();
    }
}