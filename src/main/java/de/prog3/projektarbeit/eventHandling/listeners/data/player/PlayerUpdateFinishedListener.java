package de.prog3.projektarbeit.eventHandling.listeners.data.player;

import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerUpdateFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.Priority;

public abstract class PlayerUpdateFinishedListener extends EventListener<PlayerUpdateFinishedEvent> {

    protected PlayerUpdateFinishedListener() {
        super(PlayerUpdateFinishedEvent.class);
    }

    protected PlayerUpdateFinishedListener(Priority priority) {
        super(PlayerUpdateFinishedEvent.class, priority);
    }


    @Override
    public abstract void onEvent(PlayerUpdateFinishedEvent event);
}