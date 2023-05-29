package com.moolight.cuoc_dua_ki_thu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.internal.ViewUtils;
import com.moolight.cuoc_dua_ki_thu.adapter.LeaderBoardAdapter;
import com.moolight.cuoc_dua_ki_thu.dtos.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class GameOverActivity extends AppCompatActivity {
    private static final String FILE_NAME = "leaderboard.txt";
    private Intent gameOverIntent;
    private LinkedList<Player> players;
    private Player newPlayer;
    private ListView lvLeaderBroad;
    private EditText etName;
    private Button btnRestart,btnSave;
    private LeaderBoardAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        gameOverIntent = this.getIntent();

        lvLeaderBroad = (ListView) findViewById(R.id.lvLeaderBoard);
        btnRestart = (Button) findViewById(R.id.btnRestartButton);
        btnSave = (Button) findViewById(R.id.btnSave);
        etName = (EditText) findViewById(R.id.etName);
        try {
            loadLeaderBoard();
        }catch(Exception ex){
            Intent intent = new Intent(this,StartScreenActivity.class);
            startActivity(intent);
            finish();
        }

        lvLeaderBroad.setVisibility(View.INVISIBLE);
        btnRestart.setVisibility(View.INVISIBLE);


        btnRestart.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnSave.setOnClickListener(v->{
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
            btnRestart.setVisibility(View.VISIBLE);
            lvLeaderBroad.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.INVISIBLE);
            etName.setVisibility(View.INVISIBLE);
            addNewPlayer();
        });
    }

    private void addNewPlayer() {
        Player newPlayer = getNewPlayer();
        if(players.size()>0){
            for (Player player: players) {
                if(player.score<=newPlayer.score) players.add(players.indexOf(player),newPlayer);
            }
        } else {
         players.add(newPlayer);
        }
        if(players.size()>10) players.removeLast();
        try{
            saveGameData();
        }catch(Exception ex){}
        adapter.notifyDataSetChanged();
    }

    private void loadLeaderBoard() {
        try {
            players = getGameData();
            adapter = new LeaderBoardAdapter(GameOverActivity.this,players);
            lvLeaderBroad.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }catch(Exception ex){

        }
    }

    private Player getNewPlayer() {
        newPlayer = new Player();
        newPlayer.score= gameOverIntent.getIntExtra("score",-1);
        newPlayer.name = etName.getText().toString();
        if(newPlayer.score == -1) {
            Toast.makeText(this, "Something Wrong", Toast.LENGTH_SHORT).show();
            return null;
        }
        return newPlayer;
    }

    private LinkedList<Player> getGameData() throws IOException {
        LinkedList<Player> players = new LinkedList<>();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            //check if file exist
            openFileOutput(FILE_NAME,MODE_APPEND).close();

            fis = openFileInput(FILE_NAME);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String details;
            while((details = br.readLine())!= null){
                String[] component = details.split("|");
                Player player = new Player();
                player.name = component[0];
                player.score = Integer.parseInt(component[1]);
                players.addLast(player);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null) fis.close();
            if(isr != null) isr.close();
            if(br != null) br.close();
        }
        return players;
    }

    private void saveGameData() throws IOException {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            for (Player player:players) {
                bw.write(player.toString());
                bw.newLine();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(fos != null) fos.close();
            if(osw != null) osw.close();
            if(bw != null) bw.close();
        }
    }
}

