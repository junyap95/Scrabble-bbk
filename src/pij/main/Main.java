package pij.main;

import GameBoard.GameBoard;
import FileReader.*;
import TileBag.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner scanner;

    private static void bannerPrinter() {
        System.out.println("============" + "                     " + "============");
        System.out.println("============" + "  S k r a B B k l e  " + "============");
        System.out.println("============" + "                     " + "============");
    }

    private static boolean openCloseGameOption() {
        System.out.println("Would you like to play an _o_pen or a _c_losed game?" + "\n" + "Please enter your choice (o/c): ");
        scanner = new Scanner(System.in);
        String openOrClose = scanner.nextLine().toLowerCase();
        return openOrClose.equals("o");
    }

    private static void gigaLauncher(TileRack computer, TileRack human, boolean openOrClose, TileBag tileBag) {
            computer.refillUserRack(tileBag);
            human.refillUserRack(tileBag);
        if(openOrClose){
            System.out.println("OPEN GAME: The computer's tiles:");
            computer.printUserRow();
        } else {
            System.out.println("CLOSED GAME: The computer's tiles are not shown");
        }
        System.out.println("It's your turn! Your tiles:");
        human.printUserRow();
    }

    public static void main(String[] args) {
        //        FileProcessor fp = new FileProcessor();
        GameBoard gb = new GameBoard();
        bannerPrinter();
        //ask user load or default
        FileLoader.loadBoard(gb);
        //...
        TileBag tileBag = new TileBag();
        TileRack humanRow = new TileRack();
        TileRack computerRow = new TileRack();
        System.out.println("Initialised userRack. Length: " + humanRow.getUserRow().size());
        System.out.println("Initialised computerRack. Length: " + computerRow.getUserRow().size());
        System.out.println("Initialised. Tilebag" + tileBag.getTileMap());

        gigaLauncher(computerRow, humanRow, openCloseGameOption(), tileBag);
        System.out.println("Gigalaunched. userRack. Length: " + humanRow.getUserRow().size());
        System.out.println("Gigalaunched. comRack. Length: " + computerRow.getUserRow().size());
        System.out.println("Gigalaunched. Tilebag" + tileBag.getTileMap());
//        while(1+1==2){



//        }

//        System.out.println("b4 " + tileBag.tileMap);
//        userRow.refillUserRack(tileBag);
//        System.out.println("Refilled userRack. Length: " + userRow.getUserRow().size());
//        Human humanPlayer = new Human();
//        humanPlayer.play();

        // String playThis;
        //            do {
        //                playThis = scan.nextLine().toLowerCase();
        //            } while (!userRow.containsTile(playThis));
        //            String playThis = scan.nextLine();
        //            System.out.println(playThis);
        //            String pieceMove = playThis.split(",")[0];
        //            System.out.println(pieceMove);
        //            String coordinateMove =playThis.split(",")[1];
        //            System.out.println(coordinateMove);

//        if (pieceMove.length() <= userRow.TILE_RACK_CAPACITY) {
//            userRow.playPiece(playThis);
//        }
//        System.out.println("after " + tileBag.tileMap);
//        userRow.printUserRow();
//
//        //try to locate coordinateMoves
//        while (coordinateMove.length() < 2 || coordinateMove.length() > 3) {
//            System.out.println("Invalid coordinateMove, try again");
//            coordinateMove = scan.nextLine();
//        }
//
//        if (Character.isDigit(coordinateMove.charAt(0))) {
//            System.out.println("is digit, go right");
//        } else {
//            System.out.println("is char, go down");
//        }


//        this.scanner.close();
    }

}

