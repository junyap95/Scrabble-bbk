package pij.main.Players;

import pij.main.GameRunner.GameTextPrinter;
import pij.main.Tile.TileBag;

import java.util.Scanner;

public class HumanPlayer extends Player{

    public HumanPlayer(TileBag tileBag) {
        super(tileBag);
    }

    @Override
    public String move() {
        GameTextPrinter.printPlayersMoveInstruction();
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public String toString() {
        return "Human Player";
    }

}
