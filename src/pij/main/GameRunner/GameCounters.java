package pij.main.GameRunner;

public class GameCounters {
    private int roundCounter = 1; // keeps track of how many round of games have been played
    private int passCounter = 0; // when it is 4, game is over


    public GameCounters() {
    }

    public int getRoundCounter() {
        return roundCounter;
    }

    public int getPassCounter() {
        return passCounter;
    }

    public void incrementRoundCounter() {
        this.roundCounter++;
    }

    public void incrementPassCounter() {
        this.passCounter++;
    }

    public void resetPassCounter() {
        this.passCounter = 0;
    }



}
