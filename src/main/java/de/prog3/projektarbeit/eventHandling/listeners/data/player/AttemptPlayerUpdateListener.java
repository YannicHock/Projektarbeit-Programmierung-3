package de.prog3.projektarbeit.eventHandling.listeners.data.player;

import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.eventHandling.events.data.player.AttemptPlayerUpdateEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class AttemptPlayerUpdateListener extends EventListener<AttemptPlayerUpdateEvent> {
    private final Player player;
    protected AttemptPlayerUpdateListener(Player player) {
        super(AttemptPlayerUpdateEvent.class);
        this.player = player;
    }


    public Player getPlayer() {
        return player;
    }

    @Override
    public abstract void onEvent(AttemptPlayerUpdateEvent event);
}