package de.prog3.projektarbeit.data.objects;

import java.util.ArrayList;
import java.util.List;

public class League {
    private final int id;
    private final String name;
    private final List<Team> participants;

    public League(int id, String name){
        this.id = id;
        this.name = name;
        this.participants = new ArrayList<>();
    }

    public League(int id, String name, List<Team> participants) {
        this.id = id;
        this.name = name;
        this.participants = participants;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Team> getParticipants() {
        return participants;
    }
}
