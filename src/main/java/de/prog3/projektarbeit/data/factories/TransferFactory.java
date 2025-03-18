package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.objects.Transfer;

import java.util.Date;

public class TransferFactory {
    private int oldTeamId;
    private int newTeamId;
    private int amount;
    private int playerId;

    private void validate() throws IllegalArgumentException {
        if(oldTeamId == newTeamId){
            throw new IllegalArgumentException("Der Spieler kann nicht von einem Team zu dem gleichen Team transferiert werden");
        }
        if(amount < 0){
            throw new IllegalArgumentException("Der Transferbetrag kann nicht negativ sein");
        }
    }

    public Transfer build() throws IllegalArgumentException {
        validate();
        return new Transfer(playerId, oldTeamId, newTeamId, amount, new Date());
    }

    public TransferFactory setOldTeamId(int oldTeamId) {
        this.oldTeamId = oldTeamId;
        return this;
    }

    public TransferFactory setNewTeamId(int newTeamId) {
        this.newTeamId = newTeamId;
        return this;
    }

    public TransferFactory setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public TransferFactory setPlayerId(int playerId) {
        this.playerId = playerId;
        return this;
    }

}
