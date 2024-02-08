package pij.main.GameRunner;

public class GameCounters {
    // game counters fields
    // the first 3 fields always have consistent values when instantiated as below
    private int roundCounter=1;
    private int passCounter=0;
    private boolean isGameOver=false;
    private boolean isGameOpen;

    /*
     * all getters and setters
     */
    public int getRoundCounter() {
        return roundCounter;
    }

    // additional feature: increments roundCounter
    public void setRoundCounter(int roundCounter) {
        this.roundCounter++;
    }

    public int getPassCounter() {
        return passCounter;
    }

    public void incrementPassCounter() {
        this.passCounter++;
    }
    public void refreshPassCounter() {
        this.passCounter = 0;
    }

    public boolean isGameOpen() {
        return isGameOpen;
    }

    public void setGameOpen(boolean gameOpen) {
        isGameOpen = gameOpen;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
