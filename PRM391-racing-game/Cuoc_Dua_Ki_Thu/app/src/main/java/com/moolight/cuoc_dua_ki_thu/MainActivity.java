package com.moolight.cuoc_dua_ki_thu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;

import com.google.android.material.slider.Slider;

public class MainActivity extends AppCompatActivity {
    SeekBar racer1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        racer1 = (SeekBar) findViewById(R.id.sRacer1);

        racer1.setThumb(getDrawable(R.drawable.round_button));
    }
}