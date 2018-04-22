package com.example.shaban.alarmdemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shaban.alarmdemo.helper.Constant;
import com.example.shaban.alarmdemo.model.Alarm;


public class AlarmActivity extends AppCompatActivity {

    TextView tvTitle;
    ImageButton btnClose;
    MediaPlayer mediaPlayer;
    Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        btnClose = (ImageButton)findViewById(R.id.btnClose);

        loadUI();
        loadData();
    }

    /**
     * load ui elements
     */
    private void loadUI() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                finish();
            }
        });
    }

    /**
     * load data from database
     */

    private void loadData() {
        try {
            alarm = getIntent().getParcelableExtra(Constant.ALARM);
            tvTitle.setText(alarm.getAlarmTitle());

            startAlarm();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * start alarm and play it sound
     */
    private void startAlarm() {
        // set max volume
        final AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM), 0);

        int tone;
        switch (alarm.getTone()) {
            case 1:
                tone = R.raw.foghorn;
                break;
            case 2:
                tone = R.raw.loud_alarm_clock_buzzer;
                break;
            case 3:
                tone = R.raw.railroad_crossing_bell;
                break;
            case 4:
                tone = R.raw.tornado_siren;
                break;
            default:
                tone = R.raw.tornado_siren;
                break;
        }

        // start the sound
        mediaPlayer = MediaPlayer.create(this, tone);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
}
