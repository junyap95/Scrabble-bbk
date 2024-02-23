package pij.main.GameRunner;

import pij.main.FileProcessor.FileProcessor;
import pij.main.GameBoard.GameBoard;
import pij.main.Players.Player;
import pij.main.Square.Square;
import pij.main.Square.SquareType;
import pij.main.Tile.Tile;
import pij.main.Tile.TileRack;
import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * This class initialises a new game
 * sets up required class instances for the game
 * determines if the game should end
 * processes scores and game board based on moves
 */
public class GameRunner {
    static Scanner scanner = new Scanner(System.in);
    private GameBoard gameBoard;
    private Player humanPlayer;
    private Player computerPlayer;
    private GameCounters gameCounters;
    private boolean isGameOpen; // option to display the computer tiles

    public GameRunner(GameBoard gameBoard, Player humanPlayer, Player computerPlayer, GameCounters gameCounters) {
        this.gameBoard = gameBoard;
        this.humanPlayer = humanPlayer;
        this.computerPlayer = computerPlayer;
        this.gameCounters = gameCounters;
    }

    // method - initialises board, refill players' racks, decide open/close game
    public void initNewGame() {
        GameTextPrinter.printWelcomeBanner();
        FileProcessor.loadWordList(); // initialises word list into hashset
        this.loadAndPrintBoard();
        this.setupTileRacks();
        this.setOpenOrCloseGame();
    }

    // helper method - set the game to open/close
    private void setOpenOrCloseGame() {
        GameTextPrinter.printOpenOrCloseGameText();
        String playersChoice = scanner.nextLine().toLowerCase();
        boolean validChoice = false;

        while (!validChoice) {
            if (playersChoice.equals("o") || playersChoice.equals("c")) {
                validChoice = true;
            } else {
                System.out.println("Please enter a valid letter: o / c ");
                playersChoice = scanner.nextLine();
            }
        }
        if (playersChoice.equals("o")) {
            this.isGameOpen = true;
            return;
        }
        this.isGameOpen = false;
    }

    // helper method - player decide to load or use default game board
    private void loadAndPrintBoard() {
        GameTextPrinter.printLoadBoard();
        String playersChoice;
        playersChoice = scanner.nextLine();
        boolean validChoice = false;

        while (!validChoice) {
            if (playersChoice.equals("d") || playersChoice.equals("l")) {
                validChoice = true;
            } else {
                System.out.println("Please enter a valid letter: l / d ");
                playersChoice = scanner.nextLine();
            }
        }

        if (playersChoice.equals("d")) {
            FileProcessor.boardProcessor(this.gameBoard, "defaultBoard.txt");
        } else {
            boolean validFileName = false;
            String loadCustomBoard;

            while (!validFileName) {
                System.out.println("Please enter your file name in .txt format");
                loadCustomBoard = scanner.nextLine();
                File file = new File("resources/" + loadCustomBoard);

                if (file.exists() && loadCustomBoard.endsWith(".txt")) {
                    validFileName = true;
                    FileProcessor.boardProcessor(this.gameBoard, loadCustomBoard);
                } else {
                    System.out.println("Invalid file name or format. Please try again, or rerun the program.");
                }
            }
        }
        this.gameBoard.printGameBoard(); // print initial empty gameBoard
    }

    public boolean isGameOver() {
        int passCounter = this.gameCounters.getPassCounter();
        TileRack player1Rack = this.getHumanPlayer().getTileRack();
        TileRack player2Rack = this.getComputerPlayer().getTileRack();
        return passCounter >= 4 || player1Rack.getPlayersTiles().isEmpty() || player2Rack.getPlayersTiles().isEmpty();
    }

    public boolean isGameOpen() {
        return isGameOpen;
    }

    // during game initiation - fill up both players' racks
    private void setupTileRacks() {
        this.getHumanPlayer().getTileRack().refillPlayerRack();
        this.getComputerPlayer().getTileRack().refillPlayerRack();
    }

    // update the display of the game board
    // update the square status to 'occupied'
    public void updateGameBoard(List<Square> squaresToBeOccupied, List<Tile> tileToBeSetOnSquare) {
        for (int i = 0; i < squaresToBeOccupied.size(); i++) {
            squaresToBeOccupied.get(i).setTileOnSquare(tileToBeSetOnSquare.get(i));
        }
    }

    // helper method - for score calculations
    private int calculateScore(List<Tile> tileToBeSetOnSquare, List<Square> squaresToBeOccupied, List<Square> listOfOccupiedSquares) {
        int result = 0;
        int premiumWordCumulative = 1;
        boolean hasPremiumWord = false;

        for (Square square : listOfOccupiedSquares) {
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
            result *= premiumWordCumulative;
        }

        // if player plays all 7 tiles in one move, award 75 points after all other calculations are done
        if(tileToBeSetOnSquare.size() == 7) {
            result += 75;
        }

        return result;
    }

    // helper method - for score update
    public void updatePlayerScore(List<Tile> tileToBeSetOnSquare, List<Square> squaresToBeOccupied, List<Square> occupiedSquares, Player player) {
        int score = this.calculateScore(tileToBeSetOnSquare, squaresToBeOccupied, occupiedSquares);
        player.updateScore(score);
    }

    public GameCounters getGameCounters() {
        return gameCounters;
    }

    public Player getHumanPlayer() {
        return humanPlayer;
    }

    public Player getComputerPlayer() {
        return computerPlayer;
    }
}
