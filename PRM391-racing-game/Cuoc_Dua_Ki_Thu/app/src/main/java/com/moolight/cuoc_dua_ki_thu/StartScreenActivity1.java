package com.moolight.cuoc_dua_ki_thu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StartScreenActivity1 extends AppCompatActivity {

    Button btnPlayButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen1);
        btnPlayButton = findViewById(R.id.btnPlayButton);

        btnPlayButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}