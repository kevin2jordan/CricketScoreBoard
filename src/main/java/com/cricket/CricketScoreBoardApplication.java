package com.cricket;

import com.cricket.model.Match;
import com.cricket.service.LiveMatchService;
import com.cricket.IOPorcessor.MatchInputProcessor;
import com.cricket.service.MatchService;
import com.cricket.IOPorcessor.MatchOutputProcessor;

import static com.cricket.util.Utility.acceptInt;

public class CricketScoreBoardApplication {

    private static MatchService matchService;
    private static LiveMatchService liveMatchService;
    private static MatchOutputProcessor matchOutputProcessor;
    private static MatchInputProcessor matchInputProcessor;

    public static void main(String[] args) throws Exception {
        initializeAllServices();
        Match match = initializeMatchWithGivenInput();
        startInningFortheTeam(match, 1);
        match.getTeam1().setBatting(false);
        startInningFortheTeam(match, 2);
        System.out.println("Result: " + liveMatchService.calculateResult(match));
    }

    private static void startInningFortheTeam(Match match, int teamNumber) throws Exception {
        matchInputProcessor.takeBattingOrderForTeam(match, teamNumber);
        matchInputProcessor.initializeBattingForTeam(match, teamNumber);
        matchInputProcessor.startBowlingForTeam(match);
        matchOutputProcessor.showScoreCard(match, teamNumber);
    }

    private static Match initializeMatchWithGivenInput() {
        int totalPlayers = acceptInt("No. of players for each team");
        int totalOvers = acceptInt("No. of overs");
        return matchService.initializeMatch(totalPlayers, totalOvers);
    }

    private static void initializeAllServices() {
        matchService = new MatchService();
        liveMatchService = new LiveMatchService();
        matchInputProcessor = new MatchInputProcessor();
        matchOutputProcessor = new MatchOutputProcessor();
    }

}
