package de.prog3.projektarbeit.data.objects;

import de.prog3.projektarbeit.eventHandling.events.data.Match;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GamePlan {
    private List<Team> teams;
    private List<Match> matches;
    private Random rand;

    public GamePlan(List<Team> teams) {
        this.teams = teams;
        this.matches = new ArrayList<>();
    }


    public String MatchDate(){
        int twoThousandsYears = 2000 * 365;
        LocalDate RandomMatchdate= LocalDate.ofEpochDay(ThreadLocalRandom.
                current().nextInt(-twoThousandsYears,twoThousandsYears));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String DateinStringform = RandomMatchdate.format(formatter);
        return DateinStringform;
    }

    public void firstLeg(){
       List<Match> AllfirstLegMatch = new ArrayList<>();
       for(int i = 0 ; i< teams.size(); i++){
           for (int j = 0 ; j< teams.size(); j++){
               AllfirstLegMatch.add(new Match(teams.get(i), teams.get(j), MatchDate()));
           }
           Collections.shuffle(AllfirstLegMatch);
       }
    }

    public void SecondLeg(){
        List<Match> AllsecondLegMatch = new ArrayList<>();
        for(int i = 0 ; i< teams.size(); i++){
            for (int j = 0 ; j< teams.size(); j++){
                AllsecondLegMatch.add(new Match(teams.get(j), teams.get(i), MatchDate()));
            }
         Collections.shuffle(AllsecondLegMatch);
        }
    }

}
