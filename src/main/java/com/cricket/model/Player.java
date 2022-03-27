package com.cricket.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private int id;
    private String name;
    private Team team;
    private PlayerScore playerScore;

    public Player(@NonNull String name) {
        this.name = name;
        playerScore = new PlayerScore();
    }

    public boolean isPlaying() {
       return  playerScore.isPlaying();
    }

    public void setPlaying(boolean playing) {
        playerScore.setPlaying(playing);
    }

    public void setOnStrike(boolean onStroke) {
        playerScore.setOnStrike(onStroke);
    }

    public boolean isOnStrike() {
        return playerScore.isOnStrike();
    }

    public void addScore(int runs) {
        playerScore.addScore(runs);
    }

    public void addOut() {
        playerScore.addOut();
    }
}
