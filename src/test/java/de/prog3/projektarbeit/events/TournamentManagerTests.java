package de.prog3.projektarbeit.events;

import de.prog3.projektarbeit.data.objects.Match;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.data.TournamentManager;
import de.prog3.projektarbeit.utils.Formatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;


public class TournamentManagerTests {
    private TournamentManager tournamentManager;

    @BeforeEach

   public void setUp() {
        tournamentManager = new TournamentManager();
    }

    @Test
    public void testCreateTournament() {
        tournamentManager.createTournament("Sommer Turnier");
        assertEquals(1, tournamentManager.getTournaments().size(), "Es sollte einen Turnier erstellt werden" );
        assertEquals("Sommer Turnier", tournamentManager.getTournaments().getFirst().getName(), "Tournament name should be 'Summer Cup'");
    }

    @Test
    void testAddTeamToTournament() {
        tournamentManager.createTournament("Sommer Turnier");
        Team team = new Team("Team A");
        tournamentManager.addTeamToTournament("Sommer Turnier", team);
        assertEquals(1, tournamentManager.getTournaments().getFirst().getTeams().size(), "There should be one team in the tournament");
        assertEquals("Team A", tournamentManager.getTournaments().getFirst().getTeams().getFirst().getName(), "Team name should be 'Team A'");
    }

    @Test
    void testAddMatchToTournament() throws ParseException {
        tournamentManager.createTournament("Sommer Turnier");
        Team team1 = new Team("Team A");
        Team team2 = new Team("Team B");
        Match match = new Match(team1, team2, Formatter.parseStringToDate("2025-06-15"));
        tournamentManager.addMatchToTournament("Sommer Turnier", match);
        assertEquals(1, tournamentManager.getTournaments().getFirst().getMatches().size(), "There should be one match in the tournament");
        assertEquals(team1, tournamentManager.getTournaments().getFirst().getMatches().getFirst().getHomeTeam(), "Home team should be 'Team A'");
        assertEquals(team2, tournamentManager.getTournaments().getFirst().getMatches().getFirst().getAwayTeam(), "Away team should be 'Team B'");
    }

    @Test
    void testTournamentNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tournamentManager.addTeamToTournament("Es gibt kein Turnier", new Team("Team A"));
        });
        assertEquals("Turnier nicht gefunden", exception.getMessage());
    }
}
