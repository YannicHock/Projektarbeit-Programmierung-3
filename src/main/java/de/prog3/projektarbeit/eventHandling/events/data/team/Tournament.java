package de.prog3.projektarbeit.eventHandling.events.data.team;

import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.data.Match;

import java.util.ArrayList;
import java.util.List;

public class Tournament {
        private String name;
        private List<Team> teams;
        private List<Match> matches;

        public Tournament(String name) {
            this.name = name;
            this.teams = new ArrayList<>();
            this.matches = new ArrayList<>();
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

        public void addMatch(Match match) {
            this.matches.add(match);
        }

        public void removeTeam(Team team) {
            this.teams.remove(team);
        }
    }

