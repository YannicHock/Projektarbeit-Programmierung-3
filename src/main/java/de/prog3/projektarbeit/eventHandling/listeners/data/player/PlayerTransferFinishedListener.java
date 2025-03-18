package de.prog3.projektarbeit.eventHandling.listeners.data.player;

import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerTransferFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class PlayerTransferFinishedListener extends EventListener<PlayerTransferFinishedEvent> {

    protected PlayerTransferFinishedListener() {
        super(PlayerTransferFinishedEvent.class);
    }


    @Override
    public abstract void onEvent(PlayerTransferFinishedEvent event);
}