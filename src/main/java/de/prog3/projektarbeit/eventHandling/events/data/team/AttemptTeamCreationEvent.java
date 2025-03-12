package de.prog3.projektarbeit.eventHandling.events.data.team;

import de.prog3.projektarbeit.eventHandling.events.Event;


public class AttemptTeamCreationEvent extends Event {
    private final String teamName;


    public AttemptTeamCreationEvent(String teamName) {
        super("AttemptTeamCreationEvent");
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }
}
