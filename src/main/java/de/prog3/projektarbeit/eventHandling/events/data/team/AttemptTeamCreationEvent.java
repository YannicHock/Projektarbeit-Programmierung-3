package de.prog3.projektarbeit.eventHandling.events.data.team;

import de.prog3.projektarbeit.eventHandling.events.Event;


public class AttemptTeamCreationEvent extends Event {
    private final String teamName;
    private final int leagueId;


    public AttemptTeamCreationEvent(String teamName, int id) {
        super("AttemptTeamCreationEvent");
        this.teamName = teamName;
        this.leagueId = id;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public String getTeamName() {
        return teamName;
    }
}
