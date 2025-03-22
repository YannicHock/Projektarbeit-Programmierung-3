package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

public class PlayerFactoryTests {

    private PlayerFactory playerFactory;
    private ArrayList<Position> validPositions;

    @BeforeEach
    void setUp() {

        validPositions = new ArrayList<>();
        validPositions.add(Position.CB);


        playerFactory = new PlayerFactory()
                .setFirstName("Max")
                .setLastName("Mustermann")
                .setDateOfBirth(new Date())
                .setNumber(10)
                .setPositions(validPositions);
    }

    @Test
    @DisplayName("Build valid player returns a non-null Player with correct attributes")
    void build_validPlayer_returnsPlayer() throws ValidationException {
        Player player = playerFactory.build();
        assertNotNull(player, "Der Spieler sollte nicht null sein.");
        assertEquals("Max", player.getFirstName());
        assertEquals("Mustermann", player.getLastName());
        assertEquals(10, player.getNumber());
        assertNotNull(player.getPositions(), "Die Positionsliste sollte nicht null sein.");
        assertFalse(player.getPositions().isEmpty(), "Der Spieler muss mindestens eine Position haben.");
    }

    @Test
    @DisplayName("Build with empty first name throws ValidationException")
    void build_emptyFirstName_throwsException() {
        playerFactory.setFirstName(""); // Ungültiger Vorname
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Build with blank last name throws ValidationException")
    void build_blankLastName_throwsException() {
        playerFactory.setLastName("   "); // Nur Leerzeichen als Nachname
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Build with null date of birth throws ValidationException")
    void build_nullDateOfBirth_throwsException() {
        playerFactory.setDateOfBirth(null);
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Build with invalid number (0) throws ValidationException")
    void build_invalidNumberZero_throwsException() {
        playerFactory.setNumber(0); // Ungültige Nummer: 0
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Build with invalid number (>= 100) throws ValidationException")
    void build_invalidNumberTooHigh_throwsException() {
        playerFactory.setNumber(100); // Ungültige Nummer: 100
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Build with null positions throws ValidationException")
    void build_nullPositions_throwsException() {
        playerFactory.setPositions(null);
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Build with empty positions list throws ValidationException")
    void build_emptyPositions_throwsException() {
        playerFactory.setPositions(new ArrayList<>());
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }


    @Test
    @DisplayName("Build with valid id returns Player with correct id")
    void build_validId_returnsPlayerWithId() throws ValidationException {
        playerFactory.setId(1);
        Player player = playerFactory.build();
        assertEquals(1, player.getId(), "Die ID des Spielers sollte 1 sein.");
    }

    @Test
    @DisplayName("Build with valid teamId returns Player with correct teamId")
    void build_validTeamId_returnsPlayerWithTeamId() throws ValidationException {
        playerFactory.setTeamId(2);
        Player player = playerFactory.build();
        assertEquals(2, player.getTeamId(), "Die Team-ID des Spielers sollte 2 sein.");
    }
}