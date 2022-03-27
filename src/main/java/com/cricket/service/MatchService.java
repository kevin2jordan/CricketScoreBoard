package com.cricket.service;

import com.cricket.model.Match;
import com.cricket.model.Player;
import com.cricket.model.Team;
import com.cricket.model.TeamScore;
import lombok.NonNull;

import java.util.Optional;

public class MatchService {

    /**
     * Initialize match with the given inputs
     * @param players, overs
     * @return Match
     */
    public Match initializeMatch(@NonNull int players,@NonNull int overs) {
        Match match = new Match();
        match.setPlayers(players);
        match.setDescription("Team 1 vs Team 2");
        match.setTeam1(new Team("Team 1"));
        match.setTeam2(new Team("Team 2"));
        match.setTeam1Score(new TeamScore());
        match.setTeam2Score(new TeamScore());
        match.setOvers(overs);
        return match;
    }

    /**
     * Add Player to the team for a match
     * @param match
     * @param teamNumber
     * @param name
     * @return Match
     */
    public void addPlayerToTheTeam(@NonNull Match match, @NonNull int teamNumber , @NonNull String name) {
        Team team = null;

        if (teamNumber == 1) {
            team = match.getTeam1();
            if(team == null) {
                System.out.println("Team not found ");
            }
            isPlayerAlreadyExist(name, team);
            team.addPlayer(name);
            match.setTeam1(team);
        } else {
            team = match.getTeam2();
            if(team == null) {
                System.out.println("Team not found ");
            }
            isPlayerAlreadyExist(name, team);
            team.addPlayer(name);
            match.setTeam2(team);
        }
    }

    private  void isPlayerAlreadyExist(@NonNull String name, @NonNull Team team) {
        Optional<Player> playerExist = team.getPlayerList().stream()
                .filter(player -> player.getName().equals(name))
                .findFirst();

        if(playerExist.isPresent()) {
            System.out.println("Player already exist ");
            System.out.println("Closing the application ");
            System.exit(0);
        }
    }
}
