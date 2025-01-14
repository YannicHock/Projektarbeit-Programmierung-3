package de.prog3.projektarbeit;

import java.util.ArrayList;
import java.util.UUID;

public class Team {
    private UUID uuid;
    private String name; // name of the Team
    private ArrayList<Player> players; // Members of the Team

    public Team(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        players = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public String getName() {
        return name;
    }
}
