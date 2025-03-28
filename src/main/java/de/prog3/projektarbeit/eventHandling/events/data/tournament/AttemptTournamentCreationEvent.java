package de.prog3.projektarbeit.eventHandling.events.data.tournament;

import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.Event;

import java.util.ArrayList;

public class AttemptTournamentCreationEvent extends Event {
    private final String tournamentName;
    private final ArrayList<Team> participants;


    public AttemptTournamentCreationEvent(String tournamentName, ArrayList<Team> participants) {
        super("AttemptTournamentCreationEvent");
        this.tournamentName = tournamentName;
        this.participants = participants;
    }

    public ArrayList<Team> getParticipants() {
        return participants;
    }

    public String getTournamentName() {
        return tournamentName;
    }
}