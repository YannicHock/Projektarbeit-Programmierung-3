package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.objects.Transfer;

import java.util.Date;

/**
 * Die Klasse TransferFactory ist verantwortlich für die Erstellung von Transfer-Objekten.
 * Sie bietet Methoden zum Setzen verschiedener Attribute eines Transfers und validiert die Daten vor der Erstellung des Transfers.
 */
public class TransferFactory {
    private int oldTeamId;
    private int newTeamId;
    private int amount;
    private int playerId;

    /**
     * Validiert die Daten des Transfers.
     */
    private void validate() throws IllegalArgumentException {
        if(oldTeamId == newTeamId){
            throw new IllegalArgumentException("Der Spieler kann nicht von einem Team zu dem gleichen Team transferiert werden");
        }
        if(amount < 0){
            throw new IllegalArgumentException("Der Transferbetrag kann nicht negativ sein");
        }
    }

    /**
     * Erstellt ein Transfer-Objekt.
     * @return das Transfer-Objekt
     */
    public Transfer build() throws IllegalArgumentException {
        validate();
        return new Transfer(playerId, oldTeamId, newTeamId, amount, new Date());
    }

    /**
     * Setzt die ID des alten Teams.
     * @param oldTeamId die ID des alten Teams
     * @return die TransferFactory-Instanz
     */
    public TransferFactory setOldTeamId(int oldTeamId) {
        this.oldTeamId = oldTeamId;
        return this;
    }

    /**
     * Setzt die ID des neuen Teams.
     * @param newTeamId die ID des neuen Teams
     * @return die TransferFactory-Instanz
     */
    public TransferFactory setNewTeamId(int newTeamId) {
        this.newTeamId = newTeamId;
        return this;
    }

    /**
     * Setzt den Transferbetrag.
     * @param amount der Transferbetrag
     * @return die TransferFactory-Instanz
     */
    public TransferFactory setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Setzt die ID des Spielers.
     * @param playerId die ID des Spielers
     * @return die TransferFactory-Instanz
     */
    public TransferFactory setPlayerId(int playerId) {
        this.playerId = playerId;
        return this;
    }

}


