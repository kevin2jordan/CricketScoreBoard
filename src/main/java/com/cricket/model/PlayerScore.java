package com.cricket.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class PlayerScore {
    private int id;
    private int score;
    private int fours;
    private int sixes;
    private int balls;
    private boolean isout;
    private boolean isPlaying;
    private boolean isOnStrike;

    public void addScore(@NonNull int runs) {
        this.score = this.score + runs;
        this.balls = this.balls + 1;
        if (runs == 4) {
            this.fours++;
        }
        if (runs == 6) {
            this.sixes++;
        }
    }

    public void addOut() {
        this.isout = true;
        this.isPlaying = false;
        this.isOnStrike = false;
        this.balls++;
    }
}
