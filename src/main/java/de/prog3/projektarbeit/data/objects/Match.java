package de.prog3.projektarbeit.data.objects;

public class Match {
    Team homeTeam;
    Team awayTeam;
    String date;
    public Match(Team team1, Team team2, String date) {
            this.homeTeam = team1;
            this.awayTeam = team2;
            this.date = date;
    }

    public Team getHomeTeam() {
       return this.homeTeam = homeTeam;
    }
    public Team getAwayTeam() {
        return this.awayTeam = awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Match{" +
                "homeTeam=" + homeTeam.getName() +
                ", awayTeam=" + awayTeam.getName() +
                ", date='" + date + '\'' +
                '}';
    }
}


