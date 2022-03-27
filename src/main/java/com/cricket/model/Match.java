package com.cricket.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Match {
    private int id;
    private int players;
    private int overs;
    private String description;
    private String result;
    private Team team1;
    private Team team2;
    private TeamScore team1Score;
    private TeamScore team2Score;

    public Team getBattingTeam() {

        return team1.isBatting() ? team1 :  team2;
    }

    public TeamScore getBattingTeamScore() {

        return team1.isBatting() ? team1Score : team2Score;
    }
}
