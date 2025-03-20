package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.data.Match;
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

    public void addTeamToTournament(String tournamentNmae, Team team) {
        for (Tournament tournament : tournaments) {
            if (tournamentNmae.equals(tournament.getName())) {
                tournament.addTeam(team);
                return;
            }
        }
        throw new IllegalArgumentException("Tournament not found " + tournamentNmae + " does not exist");
    }

    public void addMatchToTournament(String tournamentNmae, Match match) {
        for (Tournament tournament : tournaments) {
            if (tournamentNmae.equals(tournament.getName())) {
                tournament.addMatch(match);
                return;
            }
        }
        throw new IllegalArgumentException("Tournament not found " + tournamentNmae + " does not exist");
    }

    public void removeTeamFromTournament(String tournamentName, Team team) {
        for (Tournament tournament : tournaments) {
            if (tournamentName.equals(tournament.getName())) {
                tournament.removeTeam(team);
            }
        }
    }


}
