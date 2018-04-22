package com.example.shaban.alarmdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.shaban.alarmdemo.DataBase.DBHandler;
import com.example.shaban.alarmdemo.adapter.AlarmListAdapter;
import com.example.shaban.alarmdemo.model.Alarm;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView rvAlarm;
    FloatingActionButton fabAddAlarm;
    private AlarmListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static DBHandler db;

    private List<Alarm> mAlarms;
    private AlertDialog.Builder alertDialog;
    private final int DIALOG_OVERLAY_PERMISSION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHandler(this);

        rvAlarm = (RecyclerView)findViewById(R.id.rvAlarm);
        fabAddAlarm = (FloatingActionButton)findViewById(R.id.fabAddAlarm);

        loadUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == DIALOG_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(Settings.canDrawOverlays(this)) {

                } else {
                    finish();
                }
            }
        }
    }

    public void checkDrawOverlayPermission() {
        /** check if we already  have permission to draw over other apps */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                /** if not construct intent to request permission */
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                /** request permission via start activity for result */
                startActivityForResult(intent, DIALOG_OVERLAY_PERMISSION);
            }
        }
    }

    /**
     * Load UI elements of this activity
     */
    private void loadUI() {
        checkDrawOverlayPermission();
        rvAlarm.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        rvAlarm.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAlarms = new ArrayList<>();
        mAdapter = new AlarmListAdapter(mAlarms);

        rvAlarm.setAdapter(mAdapter);

        fabAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddAlarmActivity.class));
            }
        });
    }

    /**
     * load alarm data from database
     */

    private void loadData() {
        try {
            mAlarms = db.getAllAlarms();
            mAdapter.update(mAlarms);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
