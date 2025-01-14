package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.Team;

import java.util.Random;
import java.util.UUID;

public class TeamGenerator {

    private static final String[] TEAM_NAMES = {"Lions", "Tigers", "Bears", "Wolves", "Eagles", "Sharks", "Dragons", "Panthers"};

    public static Team generateRandomTeam(int numberOfPlayers) {
        Random random = new Random();
        String teamName = TEAM_NAMES[random.nextInt(TEAM_NAMES.length)];
        Team team = new Team(teamName);
        for (int i = 0; i < numberOfPlayers; i++) {
            team.addPlayer(PlayerGenerator.generateRandomPlayer());
        }
        return team;
    }
}
