package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.Player;

import java.util.Random;

public class PlayerGenerator {

    private static final String[] NAMES = {"John", "Mike", "Sarah", "Jessica", "David", "Emily", "Chris", "Anna"};
    private static final String[] POSITIONS = {"Goalkeeper", "Defender", "Midfielder", "Forward"};
//    private static final Integer[] JERSEY_NUMBER = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
//    private static final String [] BIRTHPLACE = {
//            "Germany", "France", "Italy", "Spain", "Portugal",
//            "Netherlands", "Belgium", "Sweden", "Denmark",
//            "Norway", "Finland", "Poland", "Austria", "Switzerland",
//            "United Kingdom", "Ireland", "Czech Republic", "Slovakia",
//            "Hungary", "Romania", "Bulgaria", "Greece", "Turkey",
//            "Russia", "Ukraine", "Croatia", "Serbia", "Bosnia and Herzegovina",
//            "Slovenia", "Montenegro", "Albania", "North Macedonia",
//            "United States", "Canada", "Brazil", "Argentina", "Chile",
//            "Uruguay", "Colombia", "Mexico", "South Africa", "Egypt",
//            "Nigeria", "Ghana", "Kenya", "China", "Japan", "South Korea",
//            "India", "Australia", "New Zealand"
//    };
    public static Player generateRandomPlayer() {
        Random random = new Random();
        String name = NAMES[random.nextInt(NAMES.length)];
        int age = random.nextInt(23) + 18; // Age between 18 and 40
        String position = POSITIONS[random.nextInt(POSITIONS.length)];
        return new Player(name, age, position, 12);
    }
}
