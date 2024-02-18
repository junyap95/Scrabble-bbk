package pij.main.GameRunner;

import pij.main.FileReader.FileProcessor;
import pij.main.GameBoard.GameBoard;
import pij.main.Move.Move;
import pij.main.Players.ComputerPlayer;
import pij.main.Players.HumanPlayer;
import pij.main.Players.Player;
import pij.main.Square.Square;
import pij.main.Square.SquareType;
import pij.main.TileBag.Tile;
import pij.main.TileBag.TileRack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameRunner {
    static Scanner scanner = new Scanner(System.in);
    private GameItems gameItems;
    private HumanPlayer humanPlayer;
    private Player computerPlayer;
    private GameCounters gameCounters;

    // constructor - initialises a new game
    public GameRunner() {
        this.initNewGame();
    }

    // method - initialises game items, counters, players
    private void initNewGame() {
        // a welcome banner is printed
        GameTextPrinter.printWelcomeBanner();
        this.gameItems = new GameItems(); // new gameBoard and new tileBag
        this.loadResources(getGameItems().getGameBoard()); // reads word list and board txt files
        this.humanPlayer = new HumanPlayer(this.getGameItems().getTileBag()); // new player and tile rack
        this.computerPlayer = new ComputerPlayer(this.getGameItems().getTileBag(), this.gameItems.getGameBoard()); // new player and tile rack
        this.gameCounters = new GameCounters();
        this.setupTileRacks();
    }

    public void loadResources(GameBoard gameBoard) {
        FileProcessor.loadWordList(); // initialises word list into hashset
        GameTextPrinter.printLoadBoard();
        String playersChoice;
        playersChoice = scanner.nextLine();

        if (playersChoice.equals("d")) FileProcessor.boardProcessor(gameBoard, "defaultBoard.txt");
        // TODO: load board
        // if (playersChoice.equals("l")) FileProcessor.fileProcessor(gb, "otherBoard.txt");

        gameBoard.printGameBoard(); // initial empty gameBoard
    }

    public boolean isGameOver() {
        int passCounter = this.gameCounters.getPassCounter();
        TileRack player1Rack = this.getHumanPlayer().getTileRack();
        TileRack player2Rack = this.getComputerPlayer().getTileRack();
        return passCounter >= 4 || player1Rack.getPlayersTiles().isEmpty() || player2Rack.getPlayersTiles().isEmpty();
    }

    private void setupTileRacks() {
        this.getHumanPlayer().getTileRack().refillUserRack();
        this.getComputerPlayer().getTileRack().refillUserRack();
    }

    // create a move list based on current move
    // get a round counter and the centre square
    // for round one - check 1. if word list forms a word that overlaps centre square
    // round 2 onwards - check 1. if word list overlap with any occupied square
    // 2. check if the word formed is parallel to any existing word on the board. e.g. does the new word 'touch' in the same direction


    // update the display of the game board
    // update the square status to 'occupied'
    // TODO: instead of passing move and tileRack, pass in the List needed instead?
    public void updateGameBoard(List<Square> squaresToBeOccupied, List<Tile> tileToBeSetOnSquare) {
        for (int i = 0; i < squaresToBeOccupied.size(); i++) {
            squaresToBeOccupied.get(i).setTileOnSquare(tileToBeSetOnSquare.get(i));
        }
    }

    // helper methods for score calculations and update
    private int calculateScore(List<Tile> tileToBeSetOnSquare, List<Square> squaresToBeOccupied, List<Square> listOfOccupiedSquares) {
        int result = 0;
        int premiumWordCumulative = 1;
        boolean hasPremiumWord = false;

        for(Square square : listOfOccupiedSquares) {
            result += square.getTileOnSquare().getTileScore();
        }

        for (int i = 0; i < squaresToBeOccupied.size(); i++) {
            Square currentSquare = squaresToBeOccupied.get(i);

            if (currentSquare.getSquareType() == SquareType.PREMIUM_WORD) {
                hasPremiumWord = true;
                result += tileToBeSetOnSquare.get(i).getTileScore();
                premiumWordCumulative *= currentSquare.getSquareScore();
            }

            if (currentSquare.getSquareType() == SquareType.PREMIUM_LETTER || currentSquare.getSquareType() == SquareType.NORMAL) {
                result += tileToBeSetOnSquare.get(i).getTileScore() * squaresToBeOccupied.get(i).getSquareScore();
            }
        }

        if (hasPremiumWord) {
            return result * premiumWordCumulative;
        }
        return result;
    }

    public void updatePlayerScore(List<Tile> tileToBeSetOnSquare, List<Square> squaresToBeOccupied, List<Square> occupiedSquares, Player player) {
        int score = this.calculateScore(tileToBeSetOnSquare, squaresToBeOccupied, occupiedSquares);
        player.updateScore(score);
    }

    //getters
    public GameItems getGameItems() {
        return gameItems;
    }

    public GameCounters getGameCounters() {
        return gameCounters;
    }

    public HumanPlayer getHumanPlayer() {
        return humanPlayer;
    }

    public Player getComputerPlayer() {
        return computerPlayer;
    }
}
