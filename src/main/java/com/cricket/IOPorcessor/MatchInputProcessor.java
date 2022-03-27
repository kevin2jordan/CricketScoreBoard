package com.cricket.IOPorcessor;

import com.cricket.model.Match;
import com.cricket.model.Player;
import com.cricket.model.Team;
import com.cricket.service.LiveMatchService;
import com.cricket.service.MatchService;
import lombok.NonNull;

import java.util.List;

import static com.cricket.util.Utility.acceptString;
import static com.cricket.util.Utility.isValidInput;

public class MatchInputProcessor {

    public MatchService matchService;
    public LiveMatchService liveMatchService;
    public MatchOutputProcessor matchOutputProcessor;

    public MatchInputProcessor() {
        matchService = new MatchService();
        liveMatchService = new LiveMatchService();
        matchOutputProcessor = new MatchOutputProcessor();
    }

    /**
     * Accept batsman name and order for Batting
     * @param match and team
     */
    public void takeBattingOrderForTeam(@NonNull Match match, @NonNull int team) {
        int playerCount = match.getPlayers();
        System.out.println("Batting Order for team " + team + " : ");
        for(int i=0; i< playerCount; i++) {
            String playerName = acceptString();
            matchService.addPlayerToTheTeam(match, team, playerName);
        }
    }

    /**
     * Function to Initialize the batting for a team i.e., set striker, and playing batsman
     * @param match and teamNumber
     */
    public void initializeBattingForTeam(@NonNull Match match, @NonNull int teamNumber) {
        Team team = null;
        if (teamNumber == 2) {
            team = match.getTeam2();
        } else {
            team = match.getTeam1();
        }
        team.setBatting(true);
        List<Player> playerList = team.getPlayerList();
        playerList.get(0).setPlaying(true);
        playerList.get(1).setPlaying(true);
        playerList.get(0).setOnStrike(true);
    }

    /**
     * Start bowling to the batting team , ball by ball
     * @param match
     */
    public void startBowlingForTeam(@NonNull Match match) {

        final int totalBalls = match.getOvers() * 6;
        for(int i =0 ; i < totalBalls; i++) {
            if (i % 6 == 0) {
                System.out.println("Over " + (i/6 + 1) + ":");
            }
            String ball = acceptString();
            if(!isValidInput(ball)) {
                System.out.println("Not a valid input ");
                System.out.println("Closing the application ");
                System.exit(0);
            }
            liveMatchService.processEvent(match, ball);
            if (!liveMatchService.isBallValid(ball)) {
                i--;
            }
            if (liveMatchService.isInningOver(match) || liveMatchService.isMatchOver(match)) {
                liveMatchService.afterInningsTask(match);
                return;
            }
            if((i+1) %6 == 0 && (i+1)/6 > 0) {
                if(match.getTeam1().isBatting()) {
                    matchOutputProcessor.showScoreCard(match, 1);
                } else {
                    matchOutputProcessor.showScoreCard(match, 2);
                }
            }
        }
    }
}
