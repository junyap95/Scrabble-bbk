package pij.main.Players;

import pij.main.GameRunner.GameTextPrinter;
import pij.main.TileBag.TileBag;

import java.util.Scanner;

public class ComputerPlayer extends Player {

    public ComputerPlayer(TileBag tileBag) {
        super(tileBag);
    }

//    @Override
//    public String move() {
//        return ",";
//    }

    @Override
    public String move() {
        GameTextPrinter.printPlayersMoveInstruction();
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public String toString() {
        return "I am a Computer Player";
    }
}
