package de.prog3.projektarbeit.events;

import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.data.Match;
import de.prog3.projektarbeit.eventHandling.events.data.team.TournamentManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TounamentManagerTest {
    private TournamentManager tournamentManager;

    @BeforeEach

   public void setUp() {
        tournamentManager = new TournamentManager();
    }

    @Test
    public void testCreateTournament() {
        tournamentManager.createTournament("Summer Cup");
        assertEquals(1, tournamentManager.getTournaments().size(), "There should be one tournament created" );
        assertEquals("Summer Cup", tournamentManager.getTournaments().get(0).getName(), "Tournament name should be 'Summer Cup'");
    }

    @Test
    void testAddTeamToTournament() {
        tournamentManager.createTournament("Summer Cup");
        Team team = new Team("Team A");
        tournamentManager.addTeamToTournament("Summer Cup", team);
        assertEquals(1, tournamentManager.getTournaments().get(0).getTeams().size(), "There should be one team in the tournament");
        assertEquals("Team A", tournamentManager.getTournaments().get(0).getTeams().get(0).getName(), "Team name should be 'Team A'");
    }

    @Test
    void testAddMatchToTournament() {
        tournamentManager.createTournament("Summer Cup");
        Team team1 = new Team("Team A");
        Team team2 = new Team("Team B");
        Match match = new Match(team1, team2, "2025-06-15");
        tournamentManager.addMatchToTournament("Summer Cup", match);
        assertEquals(1, tournamentManager.getTournaments().get(0).getMatches().size(), "There should be one match in the tournament");
        assertEquals(team1, tournamentManager.getTournaments().get(0).getMatches().get(0).getHomeTeam(), "Home team should be 'Team A'");
        assertEquals(team2, tournamentManager.getTournaments().get(0).getMatches().get(0).getAwayTeam(), "Away team should be 'Team B'");
    }

    @Test
    void testTournamentNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tournamentManager.addTeamToTournament("Nonexistent Cup", new Team("Team A"));
        });
        assertEquals("Tournament not found: Nonexistent Cup", exception.getMessage());
    }
}
