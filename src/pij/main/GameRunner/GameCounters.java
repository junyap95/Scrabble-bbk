package pij.main.GameRunner;

/**
 * This class provides required counters for the game
 * and methods to help game progression e.g. resetPassCounter
 * to reset the passCounter to 0 whenever a player plays a move
 */
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
