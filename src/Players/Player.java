package Players;

import java.util.Scanner;

public abstract class Player {
    protected int playerScore;
    protected Scanner scanner;

    public Player() {
        this.scanner = new Scanner(System.in);
    }

    abstract String play();
}
