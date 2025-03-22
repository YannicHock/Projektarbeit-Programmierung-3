package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.objects.Transfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransferFactoryTests {

    private TransferFactory transferFactory;

    @BeforeEach
    void setUp() {
        transferFactory = new TransferFactory()
                .setPlayerId(1)
                .setOldTeamId(100)
                .setNewTeamId(200)
                .setAmount(5000);
    }

    @Test
    @DisplayName("build() mit gültigen Daten erzeugt ein Transfer-Objekt mit korrekten Feldern")
    void build_validData_returnsTransfer() {
        Transfer transfer = transferFactory.build();

        assertNotNull(transfer, "Das Transfer-Objekt sollte nicht null sein.");
        assertEquals(1, transfer.getPlayerId(), "Die Player-ID sollte 1 sein.");
        assertEquals(100, transfer.getFromTeamId(), "Die alte Team-ID sollte 100 sein.");
        assertEquals(200, transfer.getToTeamId(), "Die neue Team-ID sollte 200 sein.");
        assertEquals(5000, transfer.getAmount(), "Der Betrag sollte 5000 sein.");
        assertNotNull(transfer.getDate(), "Das Transferdatum sollte nicht null sein.");
        assertNull(transfer.getFromTeamName(), "Der alte Teamname sollte null sein.");
        assertNull(transfer.getToTeamName(), "Der neue Teamname sollte null sein.");
    }

    @Test
    @DisplayName("build() wirft IllegalArgumentException, wenn alter und neuer Team-ID identisch sind")
    void build_sameTeam_throwsException() {
        transferFactory.setOldTeamId(100).setNewTeamId(100);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> transferFactory.build());
        assertTrue(ex.getMessage().contains("nicht von einem Team zu dem gleichen Team transferiert werden"),
                "Die Exception sollte darauf hinweisen, dass beide Teams identisch sind.");
    }

    @Test
    @DisplayName("build() wirft IllegalArgumentException, wenn der Transferbetrag negativ ist")
    void build_negativeAmount_throwsException() {
        transferFactory.setAmount(-1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> transferFactory.build());
        assertTrue(ex.getMessage().contains("nicht negativ sein"),
                "Die Exception sollte darauf hinweisen, dass der Transferbetrag nicht negativ sein darf.");
    }
}