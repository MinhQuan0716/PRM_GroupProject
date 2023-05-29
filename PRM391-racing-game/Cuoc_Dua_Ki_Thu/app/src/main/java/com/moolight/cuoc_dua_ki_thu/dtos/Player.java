package com.moolight.cuoc_dua_ki_thu.dtos;

public class Player {
    public String name;
    public int score;

    public Player() {
    }

    @Override
    public String toString(){
        return name+"|"+score;
    }
}
