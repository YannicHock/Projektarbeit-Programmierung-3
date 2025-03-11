package de.prog3.projektarbeit.data.objects;

import de.prog3.projektarbeit.data.DataObject;

import java.util.HashMap;


public class Team extends DataObject {
    private final String name;
    private HashMap<Integer, Player> players;
    private int id;

    public Team(String name) {
        super();
        this.name = name;
        players = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void addPlayer(Player player) {
        players.put(player.getId(), player);
    }


    public String getName() {
        return name;
    }

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }

    public void save() {}

    public void setPlayers(HashMap<Integer, Player> players) {
        this.players = players;
    }

    @Override
    public void registerListener() {}
}