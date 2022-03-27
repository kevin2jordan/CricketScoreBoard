package com.cricket.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Team {
    private int id;
    private String name;
    private boolean isBatting;
    private List<Player> playerList;

    public Team(@NonNull String name) {
        this.name = name;
        playerList = new ArrayList<>();
    }

    public void addPlayer(@NonNull String name) {
        if (playerList == null) {
            playerList = new ArrayList<Player>();
        }
        Player player = new Player(name);
        player.setTeam(this);
        playerList.add(player);
    }

    public List<Player> getCurrentBatsman() {
        return playerList.stream().filter(Player::isPlaying).collect(Collectors.toList());
    }

    public void addNextPlayerForBatting() {
        Player nextPlayer = playerList.stream()
                .filter(player -> !player.getPlayerScore().isIsout() && !player.getPlayerScore().isPlaying())
                .findFirst().orElse(null);
        if (nextPlayer != null) {
            nextPlayer.setOnStrike(true);
            nextPlayer.setPlaying(true);
        }
    }
}
