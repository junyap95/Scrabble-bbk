package pij.main.GameRunner;

import static pij.main.GameRunner.GameRunner.scanner;

public class GameCounters {
    // game counters fields
    // the first 3 fields always have consistent values when instantiated as below
    private int roundCounter=1;
    private int passCounter=0;
    private boolean isGameOpen;

    public GameCounters() {
        this.setOpenOrCloseGame();
    }

    /*
     * all getters and setters
     */
    public int getRoundCounter() {
        return roundCounter;
    }

    // additional feature: increments roundCounter
    public void incrementRoundCounter() {
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





    // helper method - player picks open/close game
    public void setOpenOrCloseGame() {
        GameTextPrinter.printOpenOrCloseGameText();
        String openOrClose = scanner.nextLine().toLowerCase();
        // TODO: string check
        if (openOrClose.equals("o")) {
            this.isGameOpen = true;
            return;
        }
        this.isGameOpen = false;
    }
}
