package de.prog3.projektarbeit.eventHandling.events.data.player;

import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.eventHandling.events.Event;

import java.util.ArrayList;
import java.util.Optional;

public class PlayerTransferFinishedEvent extends Event {
    private Player player;
    private int fromTeamId;
    private int toTeamId;

    ArrayList<Exception> exceptions;

    public PlayerTransferFinishedEvent(Player player, int fromTeamId, int toTeamId) {
        super("PlayerCreationFinishedEvent");
        this.player = player;
        this.fromTeamId = fromTeamId;
        this.toTeamId = toTeamId;
    }

    public int getFromTeamId() {
        return fromTeamId;
    }

    public int getToTeamId() {
        return toTeamId;
    }

    public PlayerTransferFinishedEvent(ArrayList<Exception> exceptions) {
        super("PlayerCreationFinishedEvent");
        this.exceptions = exceptions;
    }

    public Optional<Player> getPlayer() {
        return Optional.ofNullable(player);
    }

    public Optional<ArrayList<Exception>> getExceptions() {
        return Optional.ofNullable(exceptions);
    }
}
