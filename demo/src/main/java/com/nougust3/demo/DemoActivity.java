package com.nougust3.demo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nougust3.thinprogressbar.ThinProgressBar;

import java.util.TimerTask;

public class DemoActivity extends AppCompatActivity {

    private ThinProgressBar progressBar1;
    private ThinProgressBar progressBar2;
    private int interval = 500;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        progressBar1 = (ThinProgressBar) findViewById(R.id.progressBarOne);
        progressBar1.setMax(100);

        progressBar2 = (ThinProgressBar) findViewById(R.id.progressBarTwo);
        progressBar2.setMax(100);
        progressBar2.setColor(0x77ffff00);

        handler = new Handler();
        startProgress();
    }

    private void startProgress() {
        progressChecker.run();
    }

    private void updateProgress() {
        if(progressBar1.getProgress() == 100)
            progressBar1.setProgress(0);
        else
            progressBar1.setProgress(progressBar1.getProgress() + 1);

        if(progressBar2.getProgress() == 100)
            progressBar2.setProgress(0);
        else
            progressBar2.setProgress(progressBar2.getProgress() + 2);
    }

    Runnable progressChecker = new Runnable() {
        @Override
        public void run() {
            try {
                updateProgress();
            } finally {
                handler.postDelayed(progressChecker, interval);
            }
        }
    };
}
