package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.Player;

import java.util.UUID;

public class Database {

    public static Player fetchPlayer(UUID uuid){
        return new Player("Test", 10, "LS");
    }

}
