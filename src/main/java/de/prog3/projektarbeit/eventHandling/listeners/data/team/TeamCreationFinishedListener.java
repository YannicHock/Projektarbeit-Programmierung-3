package de.prog3.projektarbeit.eventHandling.listeners.data.team;

import de.prog3.projektarbeit.eventHandling.events.data.team.TeamCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class TeamCreationFinishedListener extends EventListener<TeamCreationFinishedEvent> {
    protected TeamCreationFinishedListener() {
        super(TeamCreationFinishedEvent.class);
    }


    @Override
    public abstract void onEvent(TeamCreationFinishedEvent event);
}