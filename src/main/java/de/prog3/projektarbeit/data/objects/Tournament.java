package de.prog3.projektarbeit.data.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Tournament {
        private final String name;
        private final List<Team> teams;
        private final List<Match> matches;



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

        public List<Match> firstMatchLeg(List<Team> teamsFirstleg ) {
            List<Match> FirstLegMatches = new ArrayList<>();
            for (int i = 0; i < teamsFirstleg.size(); i++){
                for (int j = i + 1; j < teamsFirstleg.size(); j++){
                    FirstLegMatches.add(new Match(teamsFirstleg.get(i), teamsFirstleg.get(j),MatchDate()));
                }
            }
            return FirstLegMatches;
        }

        public List<Match> secondMatchLeg(List<Team> teamsSecondleg) {
            List<Match> SecondLegMatches = new ArrayList<>();
            for (int i = 0; i < teamsSecondleg.size(); i++){
                for (int j = i + 1; j < teamsSecondleg.size(); j++){
                    SecondLegMatches.add(new Match(teamsSecondleg.get(j), teamsSecondleg.get(i),MatchDate()));
                }
            }
            return SecondLegMatches;
        }

        public Date MatchDate(){
            return new Date();
        }
    }

