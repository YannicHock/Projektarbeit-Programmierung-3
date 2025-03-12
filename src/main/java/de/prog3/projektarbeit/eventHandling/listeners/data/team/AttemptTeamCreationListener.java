package de.prog3.projektarbeit.eventHandling.listeners.data.team;

import de.prog3.projektarbeit.eventHandling.events.data.team.AttemptTeamCreationEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class AttemptTeamCreationListener extends EventListener<AttemptTeamCreationEvent> {
    protected AttemptTeamCreationListener() {
        super(AttemptTeamCreationEvent.class);
    }


    @Override
    public abstract void onEvent(AttemptTeamCreationEvent event);
}