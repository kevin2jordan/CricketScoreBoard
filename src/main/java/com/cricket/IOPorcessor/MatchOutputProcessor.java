package com.cricket.IOPorcessor;

import com.cricket.model.*;

import java.util.List;

public class MatchOutputProcessor {

    /**
     * Show Score Card for Team
     * @param match
     * @param teamNumber
     */
    public  void showScoreCard(Match match , int teamNumber) {
        Team team = null;
        TeamScore teamScore = null;
        if (teamNumber == 2) {
            team = match.getTeam2();
            teamScore = match.getTeam2Score();
        } else {
            team = match.getTeam1();
            teamScore = match.getTeam1Score();
        }

        List<Player> players = team.getPlayerList();
        System.out.println("\t");
        System.out.println("ScoreCard for Team "+ teamNumber + ":");
        System.out.println("Player Name  \t Score \t 4s \t 6s \t Balls");
        players.forEach(player -> {
            PlayerScore playerScore = player.getPlayerScore();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(player.getName());
            if (playerScore.isPlaying()) {
                stringBuilder.append('*');
            }
            stringBuilder.append("\t\t\t\t");
            stringBuilder.append(playerScore.getScore());
            stringBuilder.append("\t\t");
            stringBuilder.append(playerScore.getFours());
            stringBuilder.append("\t\t");
            stringBuilder.append(playerScore.getSixes());
            stringBuilder.append("\t\t");
            stringBuilder.append(playerScore.getBalls());
            stringBuilder.append("\t\t");
            System.out.println(stringBuilder);
        });

        System.out.println("Total: " + teamScore.getRuns() + "/" + teamScore.getWickets());
        if (teamScore.getBalls() == 0) {
            System.out.println("Overs: " + teamScore.getOvers());
        } else {
            System.out.println("Overs: " + teamScore.getOvers() + "." + teamScore.getBalls());
        }
        System.out.println();
    }

}
