package de.prog3.projektarbeit.eventHandling.events.data.tournament;

import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.Event;

import java.util.ArrayList;
import java.util.Optional;

public class TournamentFinishedEvent extends Event {

    private final ArrayList<Exception> exceptions;

    public TournamentFinishedEvent(ArrayList<Exception> exceptions) {
        super("TournamentFinishedEvent");
        this.exceptions = exceptions;
    }

    public Optional<ArrayList<Exception>> getExceptions() {
        return Optional.ofNullable(exceptions);
    }
}
