package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.Player;
import de.prog3.projektarbeit.Team;

import java.util.UUID;

public class Database {

    public static Player fetchPlayer(UUID uuid){
        return PlayerGenerator.generateRandomPlayer();
    }

    public static Team fetchTeam(UUID uuid){
        return TeamGenerator.generateRandomTeam(10);
    }

}
