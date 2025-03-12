package de.prog3.projektarbeit.eventHandling.events.data.team;

import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.Event;

import java.util.ArrayList;
import java.util.Optional;

public class TeamCreationFinishedEvent extends Event {
    private Team team;
    ArrayList<Exception> exceptions;

    public TeamCreationFinishedEvent(Team team) {
        super("TeamCreationFinishedEvent");
        this.team = team;
    }


    public TeamCreationFinishedEvent(ArrayList<Exception> exceptions) {
        super("TeamCreationFinishedEvent");
        this.exceptions = exceptions;
    }

    public Optional<Team> getTeam() {
        return Optional.ofNullable(team);
    }

    public Optional<ArrayList<Exception>> getExceptions() {
        return Optional.ofNullable(exceptions);
    }
}
