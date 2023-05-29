package com.moolight.cuoc_dua_ki_thu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private SeekBar racer1,racer2,racer3,racer4;
    private CheckBox cbRace1,cbRace2,cbRace3,cbRace4;
    private TextView tvBet,tvMoney,tvScore,tvGamble;
    private Button btnStart,btnGamble;

    private int score = 0;
    private double currentMoney = 1000.0;
    private int currentBet = 100;
    private int round = 1;
    private int gamble = 1;
    private int checkedCount = 0;
    private int accelerate = 3, speed = 5;// set for racing realness
    private int place; // 1st 2nd 3rd 4th
    // check for car finished
    private boolean[] finishes ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetAllViews();
        updateGameStatus();

        CountDownTimer countDownTimer = new CountDownTimer(300000000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                Random random = new Random(System.currentTimeMillis());
                speed += random.nextInt(accelerate);
                int one = random.nextInt(speed);
                int two = random.nextInt(speed);
                int three = random.nextInt(speed);
                int four = random.nextInt(speed);

                if(!finishes[0]) finishes[0] = checkWinner(this, racer1, "ONE WIN", cbRace1);
                if(!finishes[1]) finishes[1] = checkWinner(this, racer2, "TWO WIN", cbRace2);
                if(!finishes[2]) finishes[2] = checkWinner(this, racer3, "THREE WIN", cbRace3);
                if(!finishes[3]) finishes[3] = checkWinner(this, racer4, "FOUR WIN", cbRace4);

                 addProgress(racer1,one);
                 addProgress(racer2,two);
                 addProgress(racer3,three);
                 addProgress(racer4,four);
                // if all Finished end the race
                if (finishes[0] && finishes[1] && finishes[2] && finishes[3]) {
                    this.cancel();
                    this.onFinish();
                }
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
                btnStart.setVisibility(View.VISIBLE);
                setEnabledAllCheckBox(true);
                round+= 1;
                currentBet = 100+round*50;
                updateGameStatus();
                if(currentMoney < currentBet) gameOverForm();
            }
        };
        cbRace1.setOnCheckedChangeListener(this);
        cbRace2.setOnCheckedChangeListener(this);
        cbRace3.setOnCheckedChangeListener(this);
        cbRace4.setOnCheckedChangeListener(this);

        btnStart.setOnClickListener(v -> {
            startRace(countDownTimer);
        });

        btnGamble.setOnClickListener(v->{
            setGambleUp();
        });
    }

    private void startRace(CountDownTimer countDownTimer) {
        if(checkedCount>=1 && checkedCount<=3){
            racer1.setProgress(0);
            racer2.setProgress(0);
            racer3.setProgress(0);
            racer4.setProgress(0);
            finishes = new boolean[4];
            place=0;
            accelerate = 3; speed=5;
            btnStart.setVisibility(View.INVISIBLE);
            countDownTimer.start();
        }else{
            Toast.makeText(this,"Please make bet from 1-3 Racer",Toast.LENGTH_SHORT).show();
        }
    }

    private void setGambleUp() {
        gamble++;
        if(currentMoney < currentBet*gamble|| gamble>4) gamble=1;
        Toast.makeText(this,"Gamble x"+gamble,Toast.LENGTH_SHORT).show();
        tvBet.setText("Current Bet: "+ currentBet*gamble +"$");
        tvGamble.setText(gamble+"x");
    }

    private void gameOverForm()  {
        updateGameStatus();
        btnGamble.setEnabled(false);
        btnStart.setEnabled(false);

        try {
            Toast.makeText(this, "GameOver! " +(currentMoney<0?"You are in Dept":""), Toast.LENGTH_SHORT).show();
            Thread.sleep((long) 3000);
            Intent intent = new Intent(this, GameOverActivity.class);
            intent.putExtra("score",score);
            startActivity(intent);
            finish();

        }catch (Exception e){

        }
    }

    private boolean checkWinner(CountDownTimer countDownTimer, SeekBar racer, String message,CheckBox cbRace) {
        boolean isFinish = racer.getProgress() >= racer.getMax();// check if racer hit finish line
        if (isFinish) {
            // prevent finisher finish again
            place++;//1st 2nd 3rd 4th should not be 5
            /*  1 xe thắng trong 3 thì trừ tiền 2 xe thua và cộng lại cho khách 1.5 của 6 7 8

            //kiem tra dat cuoc1 cược (1.5*100)
                1 xe thắng trong 2 thì trừ tiền 1 xe thua và cộng cho khac 1.75
                1 xe thắng trong 1 cược thì cộng trực tiếp vào tk 2.*/
            if (cbRace.isChecked() ) {
                double money = currentBet * getGamble();
                if(place==1) {
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
        tvBet.setText("Current Bet: "+ currentBet*gamble +"$");
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
        tvGamble = (TextView) findViewById(R.id.tvGamble);

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