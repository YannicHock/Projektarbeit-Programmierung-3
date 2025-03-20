package de.prog3.projektarbeit.data.factory;

import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.factories.PlayerFactory;
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
        ValidationException ex = assertThrows(ValidationException.class, () -> playerFactory.build());
        assertTrue(ex.getMessage().contains("Fehlender Vorname"),
                "Die Fehlermeldung sollte 'Fehlender Vorname' enthalten.");
    }

    @Test
    @DisplayName("Build with blank last name throws ValidationException")
    void build_blankLastName_throwsException() {
        playerFactory.setLastName("   "); // Nur Leerzeichen als Nachname
        ValidationException ex = assertThrows(ValidationException.class, () -> playerFactory.build());
        assertTrue(ex.getMessage().contains("Fehlender Nachname"),
                "Die Fehlermeldung sollte 'Fehlender Nachname' enthalten.");
    }

    @Test
    @DisplayName("Build with null date of birth throws ValidationException")
    void build_nullDateOfBirth_throwsException() {
        playerFactory.setDateOfBirth(null);
        ValidationException ex = assertThrows(ValidationException.class, () -> playerFactory.build());
        assertTrue(ex.getMessage().contains("Fehlender Geburtstag"),
                "Die Fehlermeldung sollte 'Fehlender Geburtstag' enthalten.");
    }

    @Test
    @DisplayName("Build with invalid number (0) throws ValidationException")
    void build_invalidNumberZero_throwsException() {
        playerFactory.setNumber(0); // Ungültige Nummer: 0
        ValidationException ex = assertThrows(ValidationException.class, () -> playerFactory.build());
        assertTrue(ex.getMessage().contains("Spielernummer muss größer als 0 und kleiner als 100"),
                "Die Fehlermeldung sollte auf die Spielernummer hinweisen.");
    }

    @Test
    @DisplayName("Build with invalid number (>= 100) throws ValidationException")
    void build_invalidNumberTooHigh_throwsException() {
        playerFactory.setNumber(100); // Ungültige Nummer: 100
        ValidationException ex = assertThrows(ValidationException.class, () -> playerFactory.build());
        assertTrue(ex.getMessage().contains("Spielernummer muss größer als 0 und kleiner als 100"),
                "Die Fehlermeldung sollte auf die Spielernummer hinweisen.");
    }

    @Test
    @DisplayName("Build with null positions throws ValidationException")
    void build_nullPositions_throwsException() {
        playerFactory.setPositions(null);
        ValidationException ex = assertThrows(ValidationException.class, () -> playerFactory.build());
        assertTrue(ex.getMessage().contains("Spieler muss mindestens eine Position haben"),
                "Die Fehlermeldung sollte 'Spieler muss mindestens eine Position haben' enthalten.");
    }

    @Test
    @DisplayName("Build with empty positions list throws ValidationException")
    void build_emptyPositions_throwsException() {
        playerFactory.setPositions(new ArrayList<>());
        ValidationException ex = assertThrows(ValidationException.class, () -> playerFactory.build());
        assertTrue(ex.getMessage().contains("Spieler muss mindestens eine Position haben"),
                "Die Fehlermeldung sollte 'Spieler muss mindestens eine Position haben' enthalten.");
    }
}