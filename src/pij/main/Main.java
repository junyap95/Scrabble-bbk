package pij.main;

import GameBoard.GameBoard;
import FileReader.*;
import Square.Square;
import TileBag.*;
import com.sun.security.jgss.GSSUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    int roundCounter = 1;

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

    private static void tileRackLauncher(TileRack computer, TileRack human, boolean openOrClose, TileBag tileBag) {
        computer.refillUserRack(tileBag);
        human.refillUserRack(tileBag);
        if (openOrClose) {
            System.out.println("OPEN GAME: The computer's tiles:");
            computer.printUserRow();
        } else {
            System.out.println("CLOSED GAME: The computer's tiles are not shown");
        }
        System.out.println("It's your turn! Your tiles:");
        human.printUserRow();
    }

    private static String userMove() {
        System.out.println("Please enter your move in the format: \"word,square\" (without the quotes) " + "\n"
                           + "For example, for suitable tile rack and board configuration, a downward move could be \"HI,f4\" and a rightward move could be \"HI,4f\"."
                           + "\n\n" + "In the word, upper-case letters are standard tiles"
                           + "and lower-case letters are wildcards."
                           + "\n" + "Entering \",\" passes the turn.");
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static boolean tileVerifier(TileRack tileRack, String userMove, GameBoard gameBoard) {
        boolean validTile = true;
        boolean validPosition = true;

        String tileMove = userMove.split(",")[0];
        List<String> input = List.of(tileMove.split(""));
        List<String> containsTile = tileRack.getUserRow().stream()
                .map(t -> String.valueOf((t.getDisplay().charAt(0))))
                .map(String::toUpperCase)
                .toList();
        for (String s : input) {
            if (!containsTile.contains(s)) {
                System.out.println("With tiles " + tileRack + " you cannot play word " + userMove);
                validTile = false;
                return validTile;
            }
        }

        String positionMove = userMove.split(",")[1];
        char columnChar = 0;
        StringBuilder rowChar = new StringBuilder();
        for (int i = 0; i < positionMove.length(); i++) {
            if (Character.isDigit(positionMove.charAt(i))) {
                rowChar.append(positionMove.charAt(i));
            } else {
                columnChar += positionMove.charAt(i);
            }
        }

        if (Integer.parseInt(rowChar.toString()) > gameBoard.getGameBoardSize() || columnChar >= 'a' + gameBoard.getGameBoardSize()) {
            System.out.println("Illegal move format");
            validPosition = false;
            return validPosition;
        }

        int rowIndex= Integer.parseInt(rowChar.toString())-1;;
        int colIndex= columnChar - 97;
        Square startSquare = gameBoard.getTileSpace().get(rowIndex).get(colIndex);
        int lengthOfTravel = tileMove.length();
        String directionOfTravel = Character.isDigit(positionMove.charAt(0)) ? "RIGHTWARD":"DOWNWARD";
        System.out.println("length of travel " + lengthOfTravel);
        System.out.println(directionOfTravel);
        System.out.println("start square is "  + startSquare);
        List<String> tileReplaceSquare = tileRack.getUserRow().stream()
                .map(t -> String.valueOf((t.getDisplay()).toUpperCase()))
                .toList();
        System.out.println(tileReplaceSquare);
        if(directionOfTravel.equals("RIGHTWARD")){
            for(int i = 0; i < lengthOfTravel; i++) {
                System.out.print(gameBoard.getTileSpace().get(rowIndex).get(colIndex+i));
            }
        }
        if(directionOfTravel.equals("DOWNWARD")){
            List<List<Square>> squareList = gameBoard.getTileSpace();
            for(int i = 0; i < lengthOfTravel; i++) {
                String current = input.get(i);
                List<String> toReplaced = tileReplaceSquare.stream()
                        .filter(s->current.equals(String.valueOf(s.charAt(0)).toUpperCase()))
                        .toList();


                System.out.println(squareList.get(rowIndex+i).get(colIndex));
                squareList.get(rowIndex+i).get(colIndex).setDisplay(toReplaced.get(0)+" ");
            }
            gameBoard.printGameBoard();

        }

        return true;

    }


    public static void main(String[] args) {
        GameBoard gb = new GameBoard();
        bannerPrinter();
        //ask user load or default
        FileLoader.loadBoard(gb);
        //...
        TileBag tileBag = new TileBag();
        TileRack humanRow = new TileRack();
        TileRack computerRow = new TileRack();
        //        System.out.println("Initialised userRack. Length: " + humanRow.getUserRow().size());
        //        System.out.println("Initialised computerRack. Length: " + computerRow.getUserRow().size());
        //        System.out.println("Initialised. Tilebag" + tileBag.getTileMap());

        tileRackLauncher(computerRow, humanRow, openCloseGameOption(), tileBag);
        //        System.out.println("Gigalaunched. userRack. Length: " + humanRow.getUserRow().size());
        //        System.out.println("Gigalaunched. comRack. Length: " + computerRow.getUserRow().size());
        //        System.out.println("Gigalaunched. Tilebag" + tileBag.getTileMap());
        //        while(1+1==2){

        System.out.println("gb size "+gb.getGameBoardSize());
        System.out.println("centreSquare " + gb.getTileSpace().get(gb.getCentreIndex()).get(gb.getCentreIndex()));
        System.out.println(tileVerifier(humanRow, userMove(), gb));


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

