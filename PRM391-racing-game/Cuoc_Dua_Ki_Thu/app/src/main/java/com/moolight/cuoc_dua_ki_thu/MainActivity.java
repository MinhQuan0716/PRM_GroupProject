package com.moolight.cuoc_dua_ki_thu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private SeekBar racer1,racer2,racer3,racer4;
    private CheckBox cbRace1,cbRace2,cbRace3,cbRace4;
    private TextView tvBet,tvMoney,tvScore;
    private Button btnStart,btnGamble;

    private int score = 0;
    private double currentMoney = 1000.0;
    private int currentBet = 100;
    private int round = 1;
    private int gamble = 1;
    private int checkedCount = 0;
    private int accelerate = 3, speed = 5;// set for racing realness
    private int place; // 1st 2nd 3rd 4th
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetAllViews();
        updateGameStatus();

        CountDownTimer countDownTimer = new CountDownTimer(30000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                Random random = new Random(System.currentTimeMillis());
                speed += random.nextInt(accelerate);
                int one = random.nextInt(speed);
                int two = random.nextInt(speed);
                int three = random.nextInt(speed);
                int four = random.nextInt(speed);

                boolean finish1 = checkWinner(this, racer1, "ONE WIN", cbRace1,place);
                boolean finish2 = checkWinner(this, racer2, "TWO WIN", cbRace2,place);
                boolean finish3 = checkWinner(this, racer3, "THREE WIN", cbRace3,place);
                boolean finish4 = checkWinner(this, racer4, "FOUR WIN", cbRace4,place);

                if (!finish1) addProgress(racer1,one);
                if (!finish2) addProgress(racer2,two);
                if (!finish3) addProgress(racer3,three);
                if (!finish4) addProgress(racer4,four);

                if (finish1 && finish2 && finish3 && finish4) raceFinished(this);
            }

            private void addProgress(SeekBar racer, int increase) {
                int progress = racer.getProgress();
                int max = racer.getMax();

                if(progress+ increase<= max)
                    racer.setProgress(progress+ increase);
                else
                    racer.setProgress(max);
            }

            @Override
            public void onFinish() {

            }
        };
        cbRace1.setOnCheckedChangeListener(this);
        cbRace2.setOnCheckedChangeListener(this);
        cbRace3.setOnCheckedChangeListener(this);
        cbRace4.setOnCheckedChangeListener(this);

        btnStart.setOnClickListener(v -> {
            if(checkedCount>=1 && checkedCount<=3){
                racer1.setProgress(0);
                racer2.setProgress(0);
                racer3.setProgress(0);
                racer4.setProgress(0);
                countDownTimer.start();
                place=0;
                accelerate = 3; speed=5;
                btnStart.setVisibility(View.INVISIBLE);
            }else{
                Toast.makeText(this,"Please make bet from 1-3 Racer",Toast.LENGTH_SHORT).show();
            }
        });
        btnGamble.setOnClickListener(v->{
            gamble++;
            if(gamble>4) gamble=1;
            Toast.makeText(this,"Gamble x"+gamble,Toast.LENGTH_SHORT).show();
            tvBet.setText(currentBet*gamble);
        });
    }



    private void raceFinished(CountDownTimer countDownTimer) {
        countDownTimer.cancel();
        if(currentMoney <0) gameOverForm();
        setEnabledAllCheckBox(true);
        round+= 1;
        btnStart.setVisibility(View.VISIBLE);
        currentBet = 100+round*50;
        updateGameStatus();
    }

    private void gameOverForm()  {
        Toast.makeText(this, "GameOver! You are in Dept", Toast.LENGTH_SHORT).show();
        btnGamble.setEnabled(false);
        btnStart.setEnabled(false);
    try {
        Thread.sleep((long) 3000);
    }catch (Exception e){

    }
    }

    private boolean checkWinner(CountDownTimer countDownTimer, SeekBar racer, String message,CheckBox cbRace,int place) {
        boolean isFinish = racer.getProgress() >= racer.getMax();
        if (isFinish) {
            place++;

            //kiem tra dat cuoc
            /*  1 xe thắng trong 3 thì trừ tiền 2 xe thua và cộng lại cho khách 1.5 của 1 cược (1.5*100)
                1 xe thắng trong 2 thì trừ tiền 1 xe thua và cộng cho khac 1.7
                1 xe thắng trong 1 cược thì cộng trực tiếp vào tk 2.*/
            if (cbRace.isChecked() ) {
                double money = currentBet * getGamble();
                if(place<=checkedCount) {
                    double moneyGet = money * getCheckedMultiplier();
                    score += moneyGet * 10;
                    currentMoney += moneyGet;
                    Toast.makeText(MainActivity.this, message + ", You Won " + moneyGet + "$", Toast.LENGTH_SHORT).show();
                }else{
                    currentMoney -= money;

                }
            }

            tvScore.setText("Score: "+score);
        }
        return isFinish;
    }

    private Double getCheckedMultiplier() {
        Double multiplier = null;
        switch (checkedCount){
            case 1: multiplier = 2.0; break;
            case 2: multiplier = 1.75; break;
            case 3: multiplier = 1.5; break;
        }
        return multiplier;
    }

    private int getGamble() {
        return gamble;
    }

    private void updateGameStatus() {
        tvScore.setText("Score: "+ score);
        tvBet.setText("Current Bet: "+ currentBet +"$");
        tvMoney.setText("Money: "+ currentMoney+"$");
    }

    private void GetAllViews() {
        racer1 = (SeekBar) findViewById(R.id.sbRacer1);
        racer2 = (SeekBar) findViewById(R.id.sbRacer2);
        racer3 = (SeekBar) findViewById(R.id.sbRacer3);
        racer4 = (SeekBar) findViewById(R.id.sbRacer4);
        // there are no android:enable in main_activity
        racer1.setEnabled(false);
        racer2.setEnabled(false);
        racer3.setEnabled(false);
        racer4.setEnabled(false);

        cbRace1 = (CheckBox) findViewById(R.id.cbRace1);
        cbRace2 = (CheckBox) findViewById(R.id.cbRace2);
        cbRace3 = (CheckBox) findViewById(R.id.cbRace3);
        cbRace4 = (CheckBox) findViewById(R.id.cbRace4);

        tvBet = (TextView) findViewById(R.id.tvBet);
        tvMoney = (TextView) findViewById(R.id.tvMoney);
        tvScore = (TextView) findViewById(R.id.tvScore);

        btnStart = (Button) findViewById(R.id.btnStartRace);
        btnGamble = (Button) findViewById(R.id.btnGamble);

    }
    private void setEnabledAllCheckBox(boolean enabled) {
        cbRace1.setEnabled(enabled);
        cbRace2.setEnabled(enabled);
        cbRace3.setEnabled(enabled);
        cbRace4.setEnabled(enabled);
    }

    @Override
    public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
            if(!isChecked)  checkedCount--;
            else if(checkedCount < 3) checkedCount++;
            else {
                cb.setChecked(false);
                Toast.makeText(this, "Can't bet all 4", Toast.LENGTH_SHORT).show();
            }
    }
}