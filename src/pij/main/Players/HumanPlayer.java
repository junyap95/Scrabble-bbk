package pij.main.Players;

import pij.main.GameRunner.GameTextPrinter;
import pij.main.GameRunner.MoveValidator;
import pij.main.TileBag.Tile;
import pij.main.TileBag.TileBag;
import pij.main.TileBag.TileRack;

import java.util.ArrayList;
import java.util.List;
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
        return "I am a Human Player";
    }

}
