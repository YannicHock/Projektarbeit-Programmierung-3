package de.prog3.projektarbeit.eventHandling.listeners.data.tournament;

import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.data.team.AttemptTeamCreationEvent;
import de.prog3.projektarbeit.eventHandling.events.data.tournament.AttemptTournamentCreationEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class AttemptTournamentCreationListener extends EventListener<AttemptTournamentCreationEvent> {
    protected AttemptTournamentCreationListener() {
        super(AttemptTournamentCreationEvent.class);
    }


    @Override
    public abstract void onEvent(AttemptTournamentCreationEvent event);
}
