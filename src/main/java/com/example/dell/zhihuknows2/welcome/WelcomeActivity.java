package com.example.dell.zhihuknows2.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dell.zhihuknows2.MainActivity;
import com.example.dell.zhihuknows2.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dell on 2016/6/29.
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomelayout);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
        timer.schedule(timerTask, 3 * 1000);
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
