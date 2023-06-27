package com.moolight.cuoc_dua_ki_thu.threads;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ShutdownThread implements Runnable {
    private AppCompatActivity appCompatActivity;
    private Intent intent;

    public ShutdownThread(AppCompatActivity appCompatActivity, Intent intent) {
        this.appCompatActivity = appCompatActivity;
        this.intent = intent;
    }

    public void run() {
        try {
            Thread.sleep(3000); // sleep for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        appCompatActivity.startActivity(intent); // call the finish function

        appCompatActivity.finish(); // call the finish function
    }
}
