package de.prog3.projektarbeit.data;


import de.prog3.projektarbeit.data.objects.Player;

import java.util.*;

public class PlayerGenerator {

    private static final String[] FIRST_NAMES = {"John", "Mike", "Sarah", "Jessica", "David", "Emily", "Chris", "Anna", "Marcel"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis"};
    private static final ArrayList<ArrayList<Position>> POSITION_LISTS = new ArrayList<>(List.of(
            new ArrayList<>(List.of(Position.GK)),
            new ArrayList<>(List.of(Position.RB, Position.LB)),
            new ArrayList<>(List.of(Position.CB, Position.CDM)),
            new ArrayList<>(List.of(Position.RM, Position.LM, Position.CM)),
            new ArrayList<>(List.of(Position.CAM, Position.ST)),
            new ArrayList<>(List.of(Position.RW, Position.LW, Position.RS, Position.LS)),
            new ArrayList<>(List.of(Position.GK, Position.CB)),
            new ArrayList<>(List.of(Position.RB, Position.CM, Position.LB)),
            new ArrayList<>(List.of(Position.CDM, Position.RM, Position.LM)),
            new ArrayList<>(List.of(Position.CAM, Position.ST, Position.RW)),
            new ArrayList<>(List.of(Position.LW, Position.RS, Position.LS))
    ));

    public static Player generateRandomPlayer() {
        Random random = new Random();
        int id = random.nextInt(1000);
        int number = random.nextInt(30);
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        Date birthDate = generateRandomBirthDate(random);
        ArrayList<Position> positions = POSITION_LISTS.get(random.nextInt(POSITION_LISTS.size()));
        return new Player(id, firstName, lastName, birthDate, number, positions);
    }

    private static Date generateRandomBirthDate(Random random) {
        int year = 1990 + random.nextInt(16); // 1990 to 2005
        int dayOfYear = 1 + random.nextInt(365); // 1 to 365
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(GregorianCalendar.YEAR, year);
        calendar.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
        return calendar.getTime();
    }
}
