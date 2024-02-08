package pij.main.GameRunner;

import pij.main.FileReader.FileProcessor;
import pij.main.GameBoard.GameBoard;
import pij.main.Players.ComputerPlayer;
import pij.main.Players.HumanPlayer;
import pij.main.Players.Player;
import pij.main.Square.Square;
import pij.main.TileBag.TileBag;
import pij.main.TileBag.TileRack;

import java.util.List;
import java.util.Scanner;

public class GameRunner extends MoveValidator {
    static Scanner scanner = new Scanner(System.in);
    private GameCounters gameCounters;
    private GameItems gameItems;
    private Player computerPlayer;
    private Player humanPlayer;

    public Player getHumanPlayer() {
        return humanPlayer;
    }
    public Player getComputerPlayer() {
        return computerPlayer;
    }

    // constructor, initialises a new game
    public GameRunner(){
        // a welcome banner is printed
        GameTextPrinter.printWelcomeBanner();
        // required game items initialised. assuming human versus computer for this project
        this.initNewGame();
        // based on player's choice a board is loaded and printed on console
        this.loadBoard();

        this.setOpenOrCloseGame();
    }

    public GameCounters getGameCounters() {
        return gameCounters;
    }

    public GameItems getGameItems() {
        return gameItems;
    }

    // helper method 1 - initialises game items, counters, players
    private void initNewGame(){
        this.gameCounters = new GameCounters();
        this.gameItems = new GameItems();
        this.humanPlayer = new HumanPlayer();
        this.computerPlayer = new ComputerPlayer();
    }

    // helper method 2 - load/default board and prints out
    public void loadBoard(){
        GameBoard gb = this.gameItems.getGameBoard();
        GameTextPrinter.printLoadBoard();
        String playersChoice;
        playersChoice = scanner.nextLine();

        if (playersChoice.equals("d")) FileProcessor.fileProcessor(gb, "defaultBoard.txt");
        //TODO: load board
        // if (playersChoice.equals("l")) FileProcessor.fileProcessor(gb, "otherBoard.txt");

        gb.printGameBoard();
    }

    // helper method 3 - player picks open/close game
    public void setOpenOrCloseGame() {
        GameTextPrinter.printOpenOrCloseGameText();
        String openOrClose = scanner.nextLine().toLowerCase();
        // TODO: string check
        if (openOrClose.equals("o")) {
            gameCounters.setGameOpen(true);
            return;
        }
        gameCounters.setGameOpen(false);
    }

    // method - refill players racks every round if possible
    public void tileRackRunner() {
        TileRack compsRack = this.gameItems.getComputersTileRack();
        TileRack humansRack = this.gameItems.getHumansTileRack();
        TileBag tileBag = this.gameItems.getTileBag();
        boolean openGame = this.gameCounters.isGameOpen();

        compsRack.refillUserRack(tileBag);
        humansRack.refillUserRack(tileBag);

        if (openGame) {
            GameTextPrinter.printComputersRack(true);
            System.out.println(compsRack);
        }

        GameTextPrinter.printItsYourTurn();
        System.out.println(humansRack);
    }

    public void boardUpdater() {
        GameBoard gb = this.getGameItems().getGameBoard();
        int centreSquareIndex = gb.getCentreSquareIndex();

        if (this.getGameCounters().getRoundCounter() == 1) {

        }
    }

    public boolean isMovePlayableOnBoard(Square square, GameBoard gb) {
        List<Square> moveList = this.currentMove.createMoveList(this.gameItems.getGameBoard());
        int roundCounter = this.gameCounters.getRoundCounter();
        Square centreSquare = this.gameItems.getGameBoard().getCentreSquare();

        if(roundCounter == 1) {
            return moveList.contains(centreSquare); // true if contains centre square
        }

        for(Square sq : moveList){
            if(sq.isSquareOccupied()) return true; // if contains >= 1 occupied square
        }

        return false;// if no overlap of >= 1 square
    }

//    public boolean playersMoveValidation() {
//        String humanPlayersMove = this.humanPlayer.move();
//
//
//
//        // verifies if position played allowed, managed by board?
//        char columnChar = 0;
//        StringBuilder rowChar = new StringBuilder();
//        for (int i = 0; i < positionMove.length(); i++) {
//            if (Character.isDigit(positionMove.charAt(i))) {
//                rowChar.append(positionMove.charAt(i));
//            } else {
//                columnChar += positionMove.charAt(i);
//            }
//        }
//
//        //boundary check, managed by board?
//        if (Integer.parseInt(rowChar.toString()) > gameBoard.getGameBoardSize() || columnChar >= 'a' + gameBoard.getGameBoardSize()) {
//            System.out.println("Illegal move format");
//            validPosition = false;
//            return validPosition;
//        }
//
//        int rowIndex = Integer.parseInt(rowChar.toString()) - 1;
//        int colIndex = columnChar - 97;
//        Square startSquare = gameBoard.getAllSquares().get(rowIndex).get(colIndex);
//        int lengthOfTravel = tileMove.length();
//        String directionOfTravel = Character.isDigit(positionMove.charAt(0)) ? "RIGHTWARD" : "DOWNWARD";
//        System.out.println("length of travel " + lengthOfTravel);
//        System.out.println(directionOfTravel);
//        System.out.println("start square is " + startSquare);
//
//        if (directionOfTravel.equals("RIGHTWARD")) {
//            updateSquareList("RIGHTWARD", lengthOfTravel, rowIndex, colIndex, input, tileRack, gameBoard);
//        } else {
//            updateSquareList("DOWNWARD", lengthOfTravel, rowIndex, colIndex, input, tileRack, gameBoard);
//        }
//
//        gameBoard.printGameBoard();
//
//        return true;
//
//    }
//
//    public void updateSquareList(String directionOfTravel, int lengthOfTravel, int rowIndex, int colIndex, List<String> input, TileRack tileRack, GameBoard gb) {
//        List<String> tileReplaceSquare = tileRack.getUserRow().stream()
//                .map(t -> (t.getDisplayOnBoard()).toUpperCase())
//                .toList();
//        System.out.println(tileReplaceSquare);
//        List<List<Square>> squareList = gb.getAllSquares();
//        for (int i = 0; i < lengthOfTravel; i++) {
//            String current = input.get(i);
//            List<String> toBeReplaced = tileReplaceSquare.stream()
//                    .filter(s -> current.equals(String.valueOf(s.charAt(0)).toUpperCase()))
//                    .toList();
//            if (directionOfTravel.equals("RIGHTWARD")) {
//                squareList.get(rowIndex).get(colIndex + i).setDisplay(toBeReplaced.getFirst().length() > 2 ? toBeReplaced.getFirst() : toBeReplaced.getFirst() + " ");
//
//            }
//            if (directionOfTravel.equals("DOWNWARD")) {
//                squareList.get(rowIndex + i).get(colIndex).setDisplay(toBeReplaced.getFirst().length() > 2 ? toBeReplaced.getFirst() : toBeReplaced.getFirst() + " ");
//
//            }
//
//
//        }
//    }
}
