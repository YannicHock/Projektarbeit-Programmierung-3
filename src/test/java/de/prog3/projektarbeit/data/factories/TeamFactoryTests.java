package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.objects.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TeamFactoryTests {

    private TeamFactory teamFactory;

    @Test
    @DisplayName("Erstellt ein Team mit gültigem Namen")
    void testBuildWithValidName() {
        String validName = "Dream Team";
        teamFactory = new TeamFactory();
        teamFactory.setName(validName).setLeagueId(1);
        Team team = teamFactory.build();
        assertNotNull(team, "Das Team sollte nicht null sein.");
        assertEquals(validName, team.getName(), "Der Teamname sollte dem gesetzten Namen entsprechen.");
    }

    @Test
    @DisplayName("Fehlender Teamname (null) führt zu IllegalArgumentException")
    void testBuildWithNullName() {
        teamFactory = new TeamFactory();
        teamFactory.setName(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> teamFactory.build());
        assertEquals("Fehlender Teamname", exception.getMessage(), "Die Exception sollte 'Fehlender Teamname' enthalten.");
    }

    @Test
    @DisplayName("Fehlender Teamname (leer) führt zu IllegalArgumentException")
    void testBuildWithEmptyName() {
        teamFactory = new TeamFactory();
        teamFactory.setName("");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> teamFactory.build());
        assertEquals("Fehlender Teamname", exception.getMessage(), "Die Exception sollte 'Fehlender Teamname' enthalten.");
    }

    @Test
    @DisplayName("Fehlender Teamname (nur Leerzeichen) führt zu IllegalArgumentException")
    void testBuildWithBlankName() {
        teamFactory = new TeamFactory();
        teamFactory.setName("   ");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> teamFactory.build());
        assertEquals("Fehlender Teamname", exception.getMessage(), "Die Exception sollte 'Fehlender Teamname' enthalten.");
    }

    @Test
    @DisplayName("setName() gibt die Instanz zurück (Fluent API)")
    void testSetNameReturnsInstance() {
        teamFactory = new TeamFactory();
        String validName = "Awesome Team";
        TeamFactory returnedFactory = teamFactory.setName(validName);
        assertSame(teamFactory, returnedFactory, "setName() sollte dieselbe Instanz zurückgeben.");
    }
}