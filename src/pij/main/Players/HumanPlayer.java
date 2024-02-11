package pij.main.Players;

import pij.main.GameRunner.GameTextPrinter;
import pij.main.GameRunner.MoveValidator;
import pij.main.TileBag.Tile;
import pij.main.TileBag.TileRack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanPlayer implements Player{
    private int playerScore = 0;

    @Override
    public int getPlayerScore() {
        return playerScore;
    }

    @Override
    public String move() {
        GameTextPrinter.printPlayersMoveInstruction();
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void updateScore(int playerScore) {
        this.playerScore += playerScore;
    }

}
