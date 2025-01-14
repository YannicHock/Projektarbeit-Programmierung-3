package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.Team;

import java.util.UUID;

public class TeamFactory {
    private UUID uuid;


    public TeamFactory(UUID uuid){
        this.uuid = uuid;
    }

    public Team createTeam(){
        return Database.fetchTeam(uuid);
    }
}

