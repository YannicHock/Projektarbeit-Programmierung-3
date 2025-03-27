package de.prog3.projektarbeit.data.objects;

import de.prog3.projektarbeit.utils.Formatter;

import java.util.Date;

public class Transfer {

    private final int playerId;
    private final String fromTeamName;
    private final String toTeamName;
    private final int toTeamId;
    private final int fromTeamId;
    private final int amount;
    private final Date date;

    public Transfer(int playerId, String fromTeamName, String toTeamName, int amount, Date date){
        this.playerId = playerId;
        this.fromTeamName = fromTeamName;
        this.toTeamName = toTeamName;
        this.amount = amount;
        this.date = date;
        this.toTeamId = -1;
        this.fromTeamId = -1;
    }

    public Transfer(int playerId, int fromTeamId, int toTeamId, int amount, Date date){
        this.playerId = playerId;
        this.fromTeamName = null;
        this.toTeamName = null;
        this.amount = amount;
        this.date = date;
        this.toTeamId = toTeamId;
        this.fromTeamId = fromTeamId;
    }

    public int getFromTeamId() {
        return fromTeamId;
    }

    public int getToTeamId() {
        return toTeamId;
    }

    public int getAmount() {
        return amount;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        return Formatter.parseDateToString(date);
    }

    public String getFromTeamName() {
        return fromTeamName;
    }

    public String getToTeamName() {
        return toTeamName;
    }
}
