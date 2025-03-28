package de.prog3.projektarbeit.eventHandling.events.data;

import de.prog3.projektarbeit.eventHandling.events.Event;

public class DataImportEvent extends Event {
    private final String url;
    private final int leagueId;

    public DataImportEvent(String url, int leagueId) {
        super("DataImportEvent");
        this.url = url;
        this.leagueId = leagueId;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public String getUrl() {
        return url;
    }
}
