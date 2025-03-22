package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.data.objects.Match;
import de.prog3.projektarbeit.data.objects.Tournament;

import java.util.ArrayList;
import java.util.List;

public class TournamentManager {
    private final List<Tournament> tournaments;


    public TournamentManager(){
        this.tournaments = new ArrayList<>();
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void createTournament(String tournamentName) {
        Tournament tournament = new Tournament(tournamentName);
        this.tournaments.add(tournament);
    }

    public void addTeamToTournament(String tournamentName, Team team) {
        for (Tournament tournament : tournaments) {
            if (tournamentName.equals(tournament.getName())) {
                tournament.addTeam(team);
                return;
            }
        }
        throw new IllegalArgumentException("Turnier nicht gefunden");
    }

    public void addMatchToTournament(String tournamentName, Match match) {
        for (Tournament tournament : tournaments) {
            if (tournamentName.equals(tournament.getName())) {
                tournament.addMatch(match);
                return;
            }
        }
        throw new IllegalArgumentException("Turnier nicht gefunden");
    }

    public void removeTeamFromTournament(String tournamentName, Team team) {
        for (Tournament tournament : tournaments) {
            if (tournamentName.equals(tournament.getName())) {
                tournament.removeTeam(team);
            }
        }
    }


}
