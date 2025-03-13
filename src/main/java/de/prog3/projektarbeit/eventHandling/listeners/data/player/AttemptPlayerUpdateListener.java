package de.prog3.projektarbeit.eventHandling.listeners.data.player;

import de.prog3.projektarbeit.eventHandling.events.data.player.AttemptPlayerUpdateEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class AttemptPlayerUpdateListener extends EventListener<AttemptPlayerUpdateEvent> {
    protected AttemptPlayerUpdateListener() {
        super(AttemptPlayerUpdateEvent.class);
    }


    @Override
    public abstract void onEvent(AttemptPlayerUpdateEvent event);
}