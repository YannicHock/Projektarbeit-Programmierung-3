package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.Player;

import java.util.Random;

public class PlayerGenerator {

    private static final String[] NAMES = {"John", "Mike", "Sarah", "Jessica", "David", "Emily", "Chris", "Anna"};
    private static final String[] POSITIONS = {"Goalkeeper", "Defender", "Midfielder", "Forward"};

    public static Player generateRandomPlayer() {
        Random random = new Random();
        String name = NAMES[random.nextInt(NAMES.length)];
        int age = random.nextInt(23) + 18; // Age between 18 and 40
        String position = POSITIONS[random.nextInt(POSITIONS.length)];
        return new Player(name, age, position, 12);
    }
}
