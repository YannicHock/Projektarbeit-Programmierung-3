package de.prog3.projektarbeit.data.objects;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static de.prog3.projektarbeit.data.Position.CM;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    @Test
    void TestAPlayerCreation (){
        Player testPlayer1 = new Player(250, "Ashish", "Kumar", Date.from(Instant.now()), 24, new ArrayList<>(List.of(CM)), 450);
        assertEquals("Ashish",testPlayer1.getFirstName());
        assertEquals("Kumar",testPlayer1.getLastName());
        assertEquals(24,testPlayer1.getNumber());
        assertEquals(250,testPlayer1.getId());

    }
}
