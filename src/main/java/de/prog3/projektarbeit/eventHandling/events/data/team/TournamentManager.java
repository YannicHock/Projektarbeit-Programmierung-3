package de.prog3.projektarbeit.eventHandling.events.data.team;

import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.data.Match;

import java.util.ArrayList;
import java.util.List;

public class TournamentManager {
    private List<Tournament> tournaments;


    public TournamentManager(){
        this.tournaments = new ArrayList<Tournament>();
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void createTournament(String tournament) {
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

    public void removeTeamFromTournament(String tournamentNmae, Team team) {
        for (Tournament tournament : tournaments) {
            if (tournamentNmae.equals(tournament.getName())) {
                tournament.removeTeam(team);
            }
        }
        if (team.equals(null)) throw new IllegalArgumentException("Team not found " + tournamentNmae + " does not exist");
    }


}
