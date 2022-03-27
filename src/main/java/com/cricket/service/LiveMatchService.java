package com.cricket.service;

import com.cricket.model.Match;
import com.cricket.model.Player;
import com.cricket.model.Team;
import com.cricket.model.TeamScore;
import lombok.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.cricket.util.Utility.getIntegerValue;

public class LiveMatchService {

    /**
     * Process each ball and update all the dependent resource based on the ball score
     *
     * @param match and ball
     */
    public void processEvent(@NonNull Match match, @NonNull String ball) {
        addBall(match, ball);
        addRuns(match, ball);
        addWicket(match, ball);
        changeStrike(match, ball);
    }

    /**
     * Add ball to Team score
     *
     * @param match, ball
     */
    public void addBall(@NonNull Match match, @NonNull String ball) {
        final TeamScore teamScore = match.getBattingTeamScore();

        if (isBallValid(ball)) {
            final int balls = teamScore.getBalls();
            if (balls < 5) {
                teamScore.setBalls(balls + 1);
            } else {
                teamScore.setOvers(teamScore.getOvers() + 1);
                teamScore.setBalls(0);
            }
        } else {
            teamScore.setRuns(teamScore.getRuns() + 1);
        }
    }

    /**
     * Add run to Team score
     *
     * @param match, ball
     */
    private void addRuns(@NonNull Match match, @NonNull String ball) {

        if (!isWicket(ball) && isBallValid(ball)) {
            int runs = getIntegerValue(ball);
            Team team = match.getBattingTeam();
            List<Player> batsman = team.getCurrentBatsman();
            Player first = batsman.get(0);
            Player second = batsman.get(1);
            if (first.isOnStrike()) {
                first.addScore(runs);
            } else {
                second.addScore(runs);
            }

            TeamScore teamScore = match.getBattingTeamScore();
            teamScore.setRuns(teamScore.getRuns() + runs);
        }
    }

    /**
     * add wicket to Team score
     *
     * @param match, ball
     */
    public void addWicket(@NonNull Match match, @NonNull String ball) {
        if (isWicket(ball)) {
            Team team = match.getBattingTeam();
            List<Player> batsman = team.getCurrentBatsman();
            Player first = batsman.get(0);
            Player second = batsman.get(1);
            if (first.isOnStrike()) {
                first.addOut();
            } else {
                second.addOut();
            }
            team.addNextPlayerForBatting();
            TeamScore teamScore = match.getBattingTeamScore();
            teamScore.setWickets(teamScore.getWickets() + 1);
        }

    }

    /**
     * Change the current strike
     *
     * @param match, ball
     */
    private void changeStrike(@NonNull Match match, @NonNull String ball) {

        if (shouldChangeStrike(match, ball)) {
            final Team team = match.getBattingTeam();
            final List<Player> batsman = team.getCurrentBatsman();
            Player first = batsman.get(0);
            Player second = batsman.get(1);
            if (first.isOnStrike()) {
                second.setOnStrike(true);
                first.setOnStrike(false);
            } else {
                first.setOnStrike(true);
                second.setOnStrike(false);
            }
        }
    }

    /**
     * Utility function to take decision regarding strike
     *
     * @param match - current match
     * @param ball  - current ball
     * @return true or false
     */
    private boolean shouldChangeStrike(@NonNull Match match, @NonNull String ball) {
        final TeamScore teamScore = match.getBattingTeamScore();
        if (teamScore.isOverBreak()) {
            return true;
        }

        final int i = getIntegerValue(ball);
        return i < 4 && i % 2 == 1;
    }

    /**
     * Mark batting completed, if innings over
     *
     * @param match - current match
     */
    public void afterInningsTask(@NonNull Match match) {
        if (isInningOver(match)) {
            Team team = match.getBattingTeam();
            team.setBatting(false);
        }
    }

    /**
     * Utility method to check, if inning is over or not
     *
     * @param match - current match
     * @return true or false
     */
    public boolean isInningOver(@NonNull Match match) {
        final int players = match.getPlayers();
        final int overs = match.getOvers();
        final TeamScore teamScore = match.getBattingTeamScore();
        if (overs == teamScore.getOvers()) {
            return true;
        }

        return players == teamScore.getWickets() + 1;
    }

    /**
     * Utility method to check, if match is over or not
     *
     * @param match
     * @return true or false
     */
    public boolean isMatchOver(@NonNull Match match) {
        final int players = match.getPlayers();
        final int overs = match.getOvers();
        final TeamScore team1Score = match.getTeam1Score();
        if (overs != team1Score.getOvers() && players != team1Score.getWickets() + 1) {
            return false;
        }

        final TeamScore team2Score = match.getTeam2Score();
        if (team1Score.getRuns() < team2Score.getRuns()) {
            return true;
        }
        return overs == team2Score.getOvers() || players == team2Score.getWickets() + 1;
    }

    /**
     * Utility method to check, if a ball is valid or not
     *
     * @param ball - current ball
     * @return true or false
     */
    public boolean isBallValid(@NonNull String ball) {
        return !getInvalidBallType().contains(ball.toLowerCase());
    }

    private Set<String> getInvalidBallType() {
        final Set<String> hashSet = new HashSet<>();
        hashSet.add("nb");
        hashSet.add("wd");
        return hashSet;
    }

    /**
     * Method to check, if there is a wicket for the current ball
     *
     * @param ball - current ball
     * @return true or false
     */
    private boolean isWicket(@NonNull String ball) {
        return "w".equalsIgnoreCase(ball);
    }

    /**
     * Calculate result of the match based on the score of both team
     *
     * @param match - current match
     * @return mesage stating the winner of the match
     */
    public String calculateResult(@NonNull Match match) {
        final TeamScore team1Score = match.getTeam1Score();
        final TeamScore team2Score = match.getTeam2Score();

        final int runsDiff = team1Score.getRuns() - team2Score.getRuns();
        final int wicketDiff = match.getPlayers() - team2Score.getWickets() - 1;
        if (runsDiff > 0) {
            return "Team 1 won By " + runsDiff + " Runs";
        } else {
            return "Team 2 won By " + wicketDiff + " Wickets";
        }
    }
}
