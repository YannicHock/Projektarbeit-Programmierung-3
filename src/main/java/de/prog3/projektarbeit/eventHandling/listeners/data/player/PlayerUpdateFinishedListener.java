package de.prog3.projektarbeit.eventHandling.listeners.data.player;

import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerUpdateFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class PlayerUpdateFinishedListener extends EventListener<PlayerUpdateFinishedEvent> {
    private final Player player;

    protected PlayerUpdateFinishedListener(Player player) {
        super(PlayerUpdateFinishedEvent.class);
        this.player = player;
    }

    protected PlayerUpdateFinishedListener() {
        super(PlayerUpdateFinishedEvent.class);
        this.player = null;
    }


    public Player getPlayer() {
        return player;
    }

    @Override
    public abstract void onEvent(PlayerUpdateFinishedEvent event);
}