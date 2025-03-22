package de.prog3.projektarbeit.data.objects;

import de.prog3.projektarbeit.data.Position;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static de.prog3.projektarbeit.data.Position.CM;
import static org.junit.jupiter.api.Assertions.*;

public class TeamTests {


    @Test
    void testATeamWithNoPlayers() {
        Team testTeam = new Team("TestTeam");
        assertEquals("TestTeam", testTeam.getName());
        assertEquals(0, testTeam.getId());
        assertEquals(0, testTeam.getPlayerCount());
    }

    @Test
    void testATeamWithNoPlayersButGivenId() {
        Team testTeam = new Team(450,"TestTeam");
        assertEquals("TestTeam", testTeam.getName());
        assertEquals(450, testTeam.getId());
        assertEquals(0, testTeam.getPlayerCount());
    }

    @Test
    void testTeamWithPlayers()
    {
        Team testTeam = new Team(450,"TestTeam",1);
        Player testPlayer = new Player(250 , "Ashish " , "Kumar " , Date.from(Instant.now()) , 24 , new ArrayList<>(List.of(CM)),testTeam.getId() );
        testTeam.addPlayer(testPlayer);
        assertEquals(1, testTeam.getPlayerCount());
        assertTrue(testTeam.getPlayers().containsValue(testPlayer));
    }

    @Test
    void testNonGetPlayerCount() {
        Team testTeam = new Team(450, "TestTeam", -1);
        Player testPlayer1 = new Player(250, "Ashish", "Kumar", Date.from(Instant.now()), 24, new ArrayList<>(List.of(CM)), testTeam.getId());
        Player testPlayer2 = new Player(251, "Ram", "Kumar", Date.from(Instant.now()), 25, new ArrayList<>(List.of(CM)), testTeam.getId());
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer2);
        assertEquals(2, testTeam.getPlayerCount());
    }
}
