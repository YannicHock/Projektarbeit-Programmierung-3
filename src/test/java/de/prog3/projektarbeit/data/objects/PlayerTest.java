package de.prog3.projektarbeit.data.objects;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static de.prog3.projektarbeit.data.Position.CM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
    @Test
    void TestAPlayerCreation (){
        Instant currentTime = Instant.now();
        Player testPlayer1 = new Player(250, "Ashish", "Kumar", Date.from(currentTime), 24, new ArrayList<>(List.of(CM)), 450);
        assertEquals("Ashish",testPlayer1.getFirstName());
        assertEquals("Kumar",testPlayer1.getLastName());
        assertEquals(24,testPlayer1.getNumber());
        assertEquals(450,testPlayer1.getTeamId());
        assertEquals(Date.from(currentTime),testPlayer1.getDateOfBirth());
        assertTrue(testPlayer1.getPositions().contains(CM));
        assertEquals(1, testPlayer1.getPositions().size());
        assertEquals(250,testPlayer1.getId());

    }

    @Test
    void testGetAge() {
        Date birthDate = Date.from(Instant.parse("2000-01-01T00:00:00Z"));
        Player testPlayer = new Player(250, "Ashish", "Kumar", birthDate, 24, new ArrayList<>(List.of(CM)), 450);
        int expectedAge = Period.between(birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears();
        assertEquals(expectedAge, testPlayer.getAge());
    }

    @Test
    void testFullName() {
        Date birthDate = Date.from(Instant.parse("2000-01-01T00:00:00Z"));
        Player testPlayer = new Player(250, "Ashish", "Kumar", birthDate, 24, new ArrayList<>(List.of(CM)), 450);
        assertEquals("Ashish Kumar", testPlayer.getFullName());
    }

    @Test
    void testGetPositionsAsString() {
        Player testPlayer = new Player(250, "Ashish", "Kumar", Date.from(Instant.now()), 24, new ArrayList<>(List.of(CM)), 450);
        String expectedPositions = "- " + CM.getFriendlyName();
        assertEquals(expectedPositions, testPlayer.getPositionsAsString());
    }
}
