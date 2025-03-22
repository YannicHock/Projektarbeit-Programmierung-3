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
    private ArrayList<Position> gueltigePositionen;

    @BeforeEach
    void setUp() {
        gueltigePositionen = new ArrayList<>();
        gueltigePositionen.add(Position.CB);

        playerFactory = new PlayerFactory()
                .setFirstName("Max")
                .setLastName("Mustermann")
                .setDateOfBirth(new Date())
                .setNumber(10)
                .setPositions(gueltigePositionen);
    }

    @Test
    @DisplayName("Erstellt bei gültigen Daten einen Spieler mit korrekten Attributen")
    void build_validPlayer_returnsPlayer() throws ValidationException {
        Player player = playerFactory.build();
        assertNotNull(player, "Der Spieler sollte nicht null sein.");
        assertEquals("Max", player.getFirstName(), "Der Vorname sollte 'Max' sein.");
        assertEquals("Mustermann", player.getLastName(), "Der Nachname sollte 'Mustermann' sein.");
        assertEquals(10, player.getNumber(), "Die Spielernummer sollte 10 sein.");
        assertNotNull(player.getPositions(), "Die Positionsliste sollte nicht null sein.");
        assertFalse(player.getPositions().isEmpty(), "Der Spieler muss mindestens eine Position haben.");
    }

    @Test
    @DisplayName("Leerer Vorname führt zu einer ValidationException")
    void build_emptyFirstName_throwsException() {
        playerFactory.setFirstName("");
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Null als Vorname führt zu einer ValidationException")
    void build_nullFirstName_throwsException() {
        playerFactory.setFirstName(null);
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Nur Leerzeichen im Nachnamen führt zu einer ValidationException")
    void build_blankLastName_throwsException() {
        playerFactory.setLastName("   ");
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Null als Nachname führt zu einer ValidationException")
    void build_nullLastName_throwsException() {
        playerFactory.setLastName(null);
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Null als Geburtsdatum führt zu einer ValidationException")
    void build_nullDateOfBirth_throwsException() {
        playerFactory.setDateOfBirth(null);
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Ungültige Spielernummer (0) führt zu einer ValidationException")
    void build_invalidNumberZero_throwsException() {
        playerFactory.setNumber(0);
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Ungültige Spielernummer (>= 100) führt zu einer ValidationException")
    void build_invalidNumberTooHigh_throwsException() {
        playerFactory.setNumber(100);
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Null als Positionsliste führt zu einer ValidationException")
    void build_nullPositions_throwsException() {
        playerFactory.setPositions(null);
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Leere Positionsliste führt zu einer ValidationException")
    void build_emptyPositions_throwsException() {
        playerFactory.setPositions(new ArrayList<>());
        assertThrows(ValidationException.class, () -> playerFactory.build());
    }

    @Test
    @DisplayName("Setzen einer gültigen ID führt zu einem Spieler mit korrekter ID")
    void build_validId_returnsPlayerWithId() throws ValidationException {
        playerFactory.setId(1);
        Player player = playerFactory.build();
        assertEquals(1, player.getId(), "Die ID des Spielers sollte 1 sein.");
    }

    @Test
    @DisplayName("Setzen einer gültigen Team-ID führt zu einem Spieler mit korrekter Team-ID")
    void build_validTeamId_returnsPlayerWithTeamId() throws ValidationException {
        playerFactory.setTeamId(2);
        Player player = playerFactory.build();
        assertEquals(2, player.getTeamId(), "Die Team-ID des Spielers sollte 2 sein.");
    }
}