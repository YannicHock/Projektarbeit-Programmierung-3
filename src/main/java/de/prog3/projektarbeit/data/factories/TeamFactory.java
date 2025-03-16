package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.objects.Team;

public class TeamFactory {

    private String name;

    public Team build() throws IllegalArgumentException {
        if(name == null || name.isEmpty() || name.isBlank()){
            throw new IllegalArgumentException("Fehlender Vorname");
        }

        return new Team(name);
    }

    public TeamFactory setName(String name) {
        this.name = name;
        return this;
    }
}

