package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.Player;

import java.util.UUID;

public class PlayerFactory {

    private UUID uuid;

    public PlayerFactory(UUID uuid){
        this.uuid = uuid;
    }

    public Player createPlayer(){
        return Database.fetchPlayer(uuid);
    }

}
