package de.prog3.projektarbeit.data.objects;

import de.prog3.projektarbeit.data.database.JooqContextProvider;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;

import java.util.*;


public class Tournament {
    private final int id;
    private final String name;
    private final List<Team> teams;
    private final List<Match> matches;

    private static final int WEEK_IN_MILLIS = 7*60*60*24*1000;

    public Tournament(int id, String name) {
        this.id = id;
        this.name = name;
        this.teams = new ArrayList<>();
        this.matches = new ArrayList<>();
    }

    public Tournament(int id, String name, List<Team> teams) {
        this.id = id;
        this.name = name;
        this.teams = teams;
        this.matches = new ArrayList<>();
        generate();
    }

    public Tournament(int id, String name, List<Team> teams, List<Match> matches) {
        this.id = id;
        this.name = name;
        this.teams = teams;
        this.matches = matches;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void removeTeam(Team team) {
        this.teams.remove(team);
    }

    /*
       Spiele pro Spieltag: Anzahl der Teams/2
     */

    private void generate() {
        Date date = new Date();
        int totalMatchdays = (teams.size() - 1) * 2;

        for (int round = 0; round < totalMatchdays; round++) {
            List<Match> matchdayMatches = new ArrayList<>();
            Set<Team> teamsPlayedToday = new HashSet<>();

            for (int i = 0; i < teams.size() / 2; i++) {
                int homeIndex = (round + i) % (teams.size() - 1);
                int awayIndex = (teams.size() - 1 - i + round) % (teams.size() - 1);

                if (i == 0) {
                    awayIndex = teams.size() - 1;
                }

                Team homeTeam = (round % 2 == 0) ? teams.get(homeIndex) : teams.get(awayIndex);
                Team awayTeam = (round % 2 == 0) ? teams.get(awayIndex) : teams.get(homeIndex);

                if (teamsPlayedToday.contains(homeTeam) || teamsPlayedToday.contains(awayTeam)) {
                    date = new Date(date.getTime() + WEEK_IN_MILLIS);
                    teamsPlayedToday.clear();
                }

                matchdayMatches.add(new Match(homeTeam, awayTeam, date));
                teamsPlayedToday.add(homeTeam);
                teamsPlayedToday.add(awayTeam);
            }

            matches.addAll(matchdayMatches);
            date = new Date(date.getTime() + WEEK_IN_MILLIS);
        }
    }

    @Override
    public String toString() {
        StringBuilder teamNames = new StringBuilder();
        for (Team team : teams) {
            teamNames.append(team.getName()).append(", ");
        }
        StringBuilder matches = new StringBuilder();
        for (Match match : this.matches) {
            matches.append(match.toString()).append("\n");
        }
        return teamNames.toString() + "\n" + matches.toString();
    }

}

