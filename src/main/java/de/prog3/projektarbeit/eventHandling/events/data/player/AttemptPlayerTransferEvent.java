package de.prog3.projektarbeit.eventHandling.events.data.player;


import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.eventHandling.events.Event;


public class AttemptPlayerTransferEvent extends Event {
    private final Player player;
    private final int newTeamId;
    private final String numberString;
    private final String amountString;


    public AttemptPlayerTransferEvent(Player player, String numberString, int newTeamId, String amountString) {
        super("AttemptPlayerTransferEvent");
        this.player = player;
        this.newTeamId = newTeamId;
        this.numberString = numberString;
        this.amountString = amountString;
    }

    public String getNumberString() {
        return numberString;
    }

    public String getAmount() {
        return amountString;
    }

    public int getNewTeamId() {
        return newTeamId;
    }

    public Player getPlayer() {
        return player;
    }
}
