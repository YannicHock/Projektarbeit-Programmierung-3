package de.prog3.projektarbeit.data.objects;

import de.prog3.projektarbeit.data.database.JooqContextProvider;
import de.prog3.projektarbeit.exceptions.UnableToSaveTeamExeption;
import org.jooq.DSLContext;

import java.util.HashMap;

import static de.prog3.projektarbeit.data.jooq.tables.Team.TEAM;


public class Team extends DataObject {
    private final String name;
    private HashMap<Integer, Player> players;
    private int playerCount;
    private int leagueId;

    public Team(String name, int leagueId) {
        super(0);
        this.name = name;
        this.leagueId = leagueId;
        players = new HashMap<>();
    }

    public Team(int id, String name, int leagueId) {
        super(id);
        this.name = name;
        this.leagueId = leagueId;
        players = new HashMap<>();
    }

    public Team(int id, String name, int leagueId, int playerCount) {
        this(id, name, leagueId);
        this.leagueId = leagueId;
        this.playerCount = playerCount;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public int getPlayerCount() {
        if(playerCount != -1){
            return playerCount;
        } else {
            return players.size();
        }
    }

    public void incrementPlayerCount() {
        playerCount++;
    }

    public void decrementPlayerCount() {
        playerCount--;
    }

    public int getPlayerCount_Int() {
        return playerCount;
    }

    public void addPlayer(Player player) {
        players.put(player.getId(), player);
    }

    
    public void removePlayer(Player player) {
        players.remove(player.getId());
    }

    public String getName() {
        return name;
    }

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<Integer, Player> players) {
        this.players = players;
    }


    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", players=" + players +
                ", playerCount=" + playerCount +
                ", leagueId=" + leagueId +
                '}';
    }
}
