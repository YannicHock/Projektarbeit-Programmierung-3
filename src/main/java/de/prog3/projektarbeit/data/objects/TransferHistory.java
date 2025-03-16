package de.prog3.projektarbeit.data.objects;

import de.prog3.projektarbeit.data.database.query.Transfers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;


public class TransferHistory {
    public record Transfer(int id, String fromTeam, String toTeam, int amount, Date date){
        @Override
        public String toString() {
            try {
                return "(" + Player.parseDateToString(date) + ") " + amount + " from " + fromTeam + " to " + toTeam;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    ArrayList<Transfer> transfers;

    public TransferHistory(Player player) {
        loadTransfers(player);
    }

    private void loadTransfers(Player player) {
        this.transfers = Transfers.getTransfers(player);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        transfers.forEach(transfer -> sb.append("- ").append(transfer).append("\n"));
        return sb.toString();
    }
}