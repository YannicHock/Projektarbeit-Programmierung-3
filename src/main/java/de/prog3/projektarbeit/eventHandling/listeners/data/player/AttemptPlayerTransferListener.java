package de.prog3.projektarbeit.eventHandling.listeners.data.player;

import de.prog3.projektarbeit.eventHandling.events.data.player.AttemptPlayerTransferEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class AttemptPlayerTransferListener extends EventListener<AttemptPlayerTransferEvent> {
    protected AttemptPlayerTransferListener() {
        super(AttemptPlayerTransferEvent.class);
    }


    @Override
    public abstract void onEvent(AttemptPlayerTransferEvent event);
}