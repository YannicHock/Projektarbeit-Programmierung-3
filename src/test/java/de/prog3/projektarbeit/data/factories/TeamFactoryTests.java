package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.objects.Team;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TeamFactoryTests {

    private TeamFactory teamFactory;

    @Test
    void testBuildWithValidName() {
        String validName = "Dream Team";
        teamFactory = new TeamFactory();
        teamFactory.setName(validName);
        Team team = teamFactory.build();
        assertNotNull(team);
        assertEquals(validName, team.getName());
    }

    @Test
    void testBuildWithNullName() {
        teamFactory = new TeamFactory();
        teamFactory.setName(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            teamFactory.build();
        });
        assertEquals("Fehlender Vorname", exception.getMessage());
    }

    @Test
    void testBuildWithEmptyName() {
        teamFactory = new TeamFactory();
        teamFactory.setName("");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            teamFactory.build();
        });
        assertEquals("Fehlender Vorname", exception.getMessage());
    }

    @Test
    void testBuildWithBlankName() {
        teamFactory = new TeamFactory();
        teamFactory.setName("   ");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            teamFactory.build();
        });
        assertEquals("Fehlender Vorname", exception.getMessage());
    }

    @Test
    void testSetNameReturnsInstance() {
        teamFactory = new TeamFactory();
        String validName = "Awesome Team";
        TeamFactory returnedFactory = teamFactory.setName(validName);
        assertSame(teamFactory, returnedFactory);
    }
}