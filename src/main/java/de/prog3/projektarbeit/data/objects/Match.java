package de.prog3.projektarbeit.data.objects;

import de.prog3.projektarbeit.utils.Formatter;

import java.util.Date;

public class Match {
    private final Team homeTeam;
    private final Team awayTeam;
    private final Date date;


    public Match(Team homeTeam, Team awayTeam, Date date) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.date = date;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return Formatter.parseDateToString(date) + " Heim: " + homeTeam.getName()
                + " - Gast: " + awayTeam.getName();
    }
}


