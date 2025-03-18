package de.prog3.projektarbeit.eventHandling.listeners.data.player;

import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerTransferFinishedEvent;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerUpdateFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.Priority;

public abstract class PlayerTransferFinishedListener extends EventListener<PlayerTransferFinishedEvent> {

    protected PlayerTransferFinishedListener() {
        super(PlayerTransferFinishedEvent.class);
    }

    protected PlayerTransferFinishedListener(Priority priority) {
        super(PlayerTransferFinishedEvent.class, priority);
    }


    @Override
    public abstract void onEvent(PlayerTransferFinishedEvent event);
}