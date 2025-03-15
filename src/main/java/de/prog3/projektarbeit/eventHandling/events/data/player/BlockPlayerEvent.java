package de.prog3.projektarbeit.eventHandling.events.data.player;

import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.eventHandling.events.Event;


public class BlockPlayerEvent extends Event {
    private final Player player;
    private final boolean blocked;


    public BlockPlayerEvent(Player player, boolean blocked) {
        super("BlockPlayerEvent");
        this.player = player;
        this.blocked = blocked;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public Player getPlayer() {
        return player;
    }
}
