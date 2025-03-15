package de.prog3.projektarbeit.eventHandling.listeners.data.player;

import de.prog3.projektarbeit.eventHandling.events.data.player.BlockPlayerEvent;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class BlockPlayerListener extends EventListener<BlockPlayerEvent> {
    protected BlockPlayerListener() {
        super(BlockPlayerEvent.class);
    }


    @Override
    public abstract void onEvent(BlockPlayerEvent event);
}