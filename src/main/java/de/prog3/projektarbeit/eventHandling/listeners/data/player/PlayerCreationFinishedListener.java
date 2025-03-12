package de.prog3.projektarbeit.eventHandling.listeners.data.player;

import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class PlayerCreationFinishedListener extends EventListener<PlayerCreationFinishedEvent> {
    protected PlayerCreationFinishedListener() {
        super(PlayerCreationFinishedEvent.class);
    }


    @Override
    public abstract void onEvent(PlayerCreationFinishedEvent event);
}