package pij.main;

import GameBoard.GameBoard;
import FileReader.*;
import TileBag.*;


public class Main {

    public static void main(String[] args) {
        GameRunner gameRunner = new GameRunner();
        GameBoard gb = new GameBoard();
        gameRunner.bannerPrinter();

        TileBag tileBag = new TileBag();
        TileRack humanRow = new TileRack();
        TileRack computerRow = new TileRack();
        //        System.out.println("Initialised userRack. Length: " + humanRow.getUserRow().size());
        //        System.out.println("Initialised computerRack. Length: " + computerRow.getUserRow().size());
        //        System.out.println("Initialised. Tilebag" + tileBag.getTileMap());

        gameRunner.tileRackLauncher(computerRow, humanRow, gameRunner.openCloseGameOption(), tileBag);
        //        System.out.println("Gigalaunched. userRack. Length: " + humanRow.getUserRow().size());
        //        System.out.println("Gigalaunched. comRack. Length: " + computerRow.getUserRow().size());
        //        System.out.println("Gigalaunched. Tilebag" + tileBag.getTileMap());
        //        while(1+1==2){

        System.out.println("gb size "+gb.getGameBoardSize());
        System.out.println("centreSquare " + gb.getAllSquares().get(gb.getCentreSquareIndex()).get(gb.getCentreSquareIndex()));
        gameRunner.tileVerifier(humanRow, gameRunner.userMove(), gb);


        //        }

        //        System.out.println("b4 " + tileBag.tileMap);
        //        userRow.refillUserRack(tileBag);
        //        System.out.println("Refilled userRack. Length: " + userRow.getUserRow().size());
        //        Human humanPlayer = new Human();
        //        humanPlayer.play();

        // String playThis;
        //                    do {
        //                        playThis = scan.nextLine().toLowerCase();
        //                    } while (!userRow.containsTile(playThis));
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

