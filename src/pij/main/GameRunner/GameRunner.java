package pij.main.GameRunner;

import pij.main.FileReader.FileProcessor;
import pij.main.GameBoard.GameBoard;
import pij.main.Players.ComputerPlayer;
import pij.main.Players.HumanPlayer;
import pij.main.Players.Player;
import pij.main.Square.Square;
import pij.main.TileBag.Tile;
import pij.main.TileBag.TileBag;
import pij.main.TileBag.TileRack;

import java.util.ArrayList;
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
        this.loadResources();

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
    public void loadResources(){
        FileProcessor.loadWordList();
        GameBoard gb = this.gameItems.getGameBoard();
        GameTextPrinter.printLoadBoard();
        String playersChoice;
        playersChoice = scanner.nextLine();

        if (playersChoice.equals("d")) FileProcessor.boardProcessor(gb, "defaultBoard.txt");
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

    // create a move list based on current move
    // get a round counter and the centre square
    // for round one - check 1. if word list forms a word that overlaps centre square
    // round 2 onwards - check 1. if word list overlap with any occupied square
    // 2. check if the word formed is parallel to any existing word on the board. e.g. does the new word 'touch' in the same direction
    public boolean isMovePlayableOnBoard() {
        Move currentMove = this.getCurrentMove();
        if(currentMove.isPass()) return false;
        List<Square> moveList = this.getCurrentMove().createMoveList(this.gameItems.getGameBoard());
        int roundCounter = this.gameCounters.getRoundCounter();
        Square centreSquare = this.gameItems.getGameBoard().getCentreSquare();

//        System.out.println("current move " + currentMove.getWordMove());
//        System.out.println("moveList " +  moveList);
//        System.out.println( "centreSquare " + centreSquare);
        if(roundCounter == 1) {
            boolean moveContainsCentreSquare = false;
            // using == operator to compare Square objects
            // rather than using .contains()
            for(Square square : moveList) {
                if (square == centreSquare) {
                    moveContainsCentreSquare = true;
                    break;
                }
            }
//            System.out.println("contains centre square: " + moveContainsCentreSquare);
            if(!moveContainsCentreSquare){
//                System.out.println("In the first round, the word played must include the centre square.");
                return false; // true if contains centre square
            }
            return FileProcessor.wordListProcessor(currentMove.getWordMove()); // after checking centre square just check if word is in word list
        }

        for(Square sq : moveList){
            boolean isSquareOccupied;
            if(sq.isSquareOccupied()) return true; // if contains >= 1 occupied square
        }

        System.out.println("No Overlap");
        return false;// if no overlap of at least 1 square
    }

    public void updateGameBoard(GameBoard gb) {
        List<String> wordMoveList = this.currentMove.getWordMoveList(); // ["D", "O", "G"]
        List<Tile> playerRack = this.gameItems.getHumansTileRack().getPlayersRack();
        List<String> stringToBePlacedOnBoard = new ArrayList<>();
        int x = this.currentMove.getStartSquareX();
        int y = this.currentMove.getStartSquareY();

        for(String s : wordMoveList) {
            boolean charIsLowerCase = Character.isLowerCase(s.charAt(0));
            for(Tile tile : playerRack) {
                if(charIsLowerCase && tile.getDisplayAsLetter().equals("_")){
                    stringToBePlacedOnBoard.add(tile.getDisplayOnBoard().replace("_",s));
                    break;
                }

                if(s.equals(tile.getDisplayAsLetter())){
                    stringToBePlacedOnBoard.add(tile.getDisplayOnBoard());
                    break;
                }

            }
        }

        System.out.println("string to be placed on board: " + stringToBePlacedOnBoard);

        for (int i = 0; i < wordMoveList.size(); i++) {
            int stringSize = stringToBePlacedOnBoard.get(i).length();

            if(this.currentMove.getMoveDirection().equals("RIGHTWARD")){
                // System.out.println("square "+gb.getSquareByIndex(x,y+i));
                gb.getSquareByIndex(x,y+i).setSquareDisplayOnBoard(stringSize > 2 ? stringToBePlacedOnBoard.get(i) : stringToBePlacedOnBoard.get(i) + " ");
            } else {
                // System.out.println("square "+ gb.getSquareByIndex(x+i,y));
                gb.getSquareByIndex(x+i,y).setSquareDisplayOnBoard(stringSize > 2 ? stringToBePlacedOnBoard.get(i) : stringToBePlacedOnBoard.get(i) + " ");

            }
        }
        gb.printGameBoard();
    }
}
