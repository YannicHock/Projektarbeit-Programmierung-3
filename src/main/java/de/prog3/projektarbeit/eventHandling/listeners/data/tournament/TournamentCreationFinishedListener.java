package de.prog3.projektarbeit.eventHandling.listeners.data.tournament;

import de.prog3.projektarbeit.eventHandling.events.data.tournament.TournamentFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class TournamentCreationFinishedListener extends EventListener<TournamentFinishedEvent> {
    protected TournamentCreationFinishedListener() {
        super(TournamentFinishedEvent.class);
    }


    @Override
    public abstract void onEvent(TournamentFinishedEvent event);
}
