package de.prog3.projektarbeit.eventHandling.listeners.data.player;

import de.prog3.projektarbeit.eventHandling.events.data.player.AttemptPlayerCreationEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class AttemptPlayerCreationListener extends EventListener<AttemptPlayerCreationEvent> {
    protected AttemptPlayerCreationListener() {
        super(AttemptPlayerCreationEvent.class);
    }


    @Override
    public abstract void onEvent(AttemptPlayerCreationEvent event);
}