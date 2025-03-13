package de.prog3.projektarbeit.eventHandling.events.data.player;

import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.eventHandling.events.Event;

import java.util.ArrayList;
import java.util.Optional;

public class PlayerUpdateFinishedEvent extends Event {
    private Player player;
    private Player oldPlayer;
    ArrayList<Exception> exceptions;

    public PlayerUpdateFinishedEvent(Player player, Player oldPlayer) {
        super("PlayerUpdateFinishedEvent");
        this.player = player;
        this.oldPlayer = oldPlayer;
    }


    public PlayerUpdateFinishedEvent(ArrayList<Exception> exceptions) {
        super("PlayerUpdateFinishedEvent");
        this.exceptions = exceptions;
    }

    public Optional<Player> getOldPlayer() {
        return Optional.ofNullable(oldPlayer);
    }

    public Optional<Player> getPlayer() {
        return Optional.ofNullable(player);
    }

    public Optional<ArrayList<Exception>> getExceptions() {
        return Optional.ofNullable(exceptions);
    }
}
