package de.prog3.projektarbeit.data.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Tournament {
        private final String name;
        private final List<Team> teams;
        private final List<Match> matches;
        private List<Match> FirtsLegMatches;
        private List<Match> SecondLegMatches;


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

        public void firstMatchLeg(){
            for (int i = 0; i < teams.size(); i++){
                for (int j = i + 1; j < teams.size(); j++){
                    FirtsLegMatches.add(new Match(teams.get(i), teams.get(j),MatchDate()));
                }
            }
            Collections.shuffle(matches);
        }

        public void secondMatchLeg(){
            for (int i = 0; i < teams.size(); i++){
                for (int j = i + 1; j < teams.size(); j++){
                    SecondLegMatches.add(new Match(teams.get(j), teams.get(i),MatchDate()));
                }
            }
            Collections.shuffle(matches);
        }

        public Date MatchDate(){
            return new Date();
        }
    }

