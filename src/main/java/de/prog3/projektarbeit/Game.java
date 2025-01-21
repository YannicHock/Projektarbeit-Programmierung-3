package de.prog3.projektarbeit;

public class Game {
    private int gameDate; // Date of Game
    private int gameResult; // game result (Ergebnis)

    public Game(int gameDate, int gameResult) {
        this.gameDate = gameDate;
        this.gameResult = gameResult;
    }


    public void setGameDate(int gameDate) {
        this.gameDate = gameDate;
    }

    public void setGameResult(int gameResult) {
        this.gameResult = gameResult;
    }

    // public int getGameDate() {}

    //public int getGameResult() {}
}
