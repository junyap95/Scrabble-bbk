package pij.main.GameRunner;

import pij.main.FileReader.FileProcessor;
import pij.main.GameBoard.GameBoard;
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

public class GameRunner extends MoveValidator {
    static Scanner scanner = new Scanner(System.in);
    private GameCounters gameCounters;
    private GameItems gameItems;
    private Player computerPlayer;
    private HumanPlayer humanPlayer;

    public HumanPlayer getHumanPlayer() {
        return humanPlayer;
    }

    public Player getComputerPlayer() {
        return computerPlayer;
    }


    // constructor, initialises a new game
    public GameRunner() {
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
    private void initNewGame() {
        this.gameCounters = new GameCounters();
        this.gameItems = new GameItems();
        this.humanPlayer = new HumanPlayer();
        this.computerPlayer = new ComputerPlayer();
    }

    // helper method 2 - load/default board and prints out
    public void loadResources() {
        FileProcessor.loadWordList();
        GameBoard gb = this.gameItems.getGameBoard();
        GameTextPrinter.printLoadBoard();
        String playersChoice;
        playersChoice = scanner.nextLine();

        if (playersChoice.equals("d")) FileProcessor.boardProcessor(gb, "defaultBoard.txt");
        // TODO: load board
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
        boolean openGame = this.gameCounters.isGameOpen();

        compsRack.refillUserRack();
        humansRack.refillUserRack();

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
        if (currentMove.isPass()) return false;
        List<Square> moveList = this.getCurrentMove().createMoveList(this.gameItems.getGameBoard());
        int roundCounter = this.gameCounters.getRoundCounter();
        Square centreSquare = this.gameItems.getGameBoard().getCentreSquare();

        // in first round only
        if (roundCounter == 1) {
            boolean moveContainsCentreSquare = false;
            // using == operator to compare Square objects
            // rather than using .contains()
            for (Square square : moveList) {
                if (square == centreSquare) {
                    moveContainsCentreSquare = true;
                    break;
                }
            }
            if (!moveContainsCentreSquare) {
                return false; // true if contains centre square
            }
            return FileProcessor.wordListProcessor(currentMove.getWordMove()); // after checking centre square just check if word is in word list
        }

        for (Square sq : moveList) {
            // first check if the current word placement includes at least 1 already occupied square
            boolean isSquareOccupied;
            if (sq.isSquareOccupied()) return true; // if contains >= 1 occupied square
        }

        System.out.println("No Overlap");
        return false;// if no overlap of at least 1 square
    }

    // update the display of the game board
    // update the square status to 'occupied'
    public void updateGameBoard(GameBoard gb) {
        List<String> wordMoveList = this.currentMove.getWordMoveList(); // ["D", "O", "G"]
        List<Tile> playerRack = this.gameItems.getHumansTileRack().getPlayersRack(); // 7 existing tiles
        List<String> stringToBePlacedOnBoard = new ArrayList<>(); // D2, O1, G2
        List<Square> squaresToBeOccupied = this.currentMove.createMoveList(gb);
        List<Tile> tileToBeSetOnSquare = new ArrayList<>();
        int x = this.currentMove.getStartSquareX();
        int y = this.currentMove.getStartSquareY();

        System.out.println("before this: " + this.getHumanPlayer().getPlayerScore());

        // for every letter in wordMoveList, loop through each tile in player's rack
        // check if there tile's letter value equals to it
        // if true 1. add the tile's display string to an array
        // 2. add the corresponding tile to an array. These 2 arrays are in the same order of when they're added
        for (String s : wordMoveList) {
            boolean charIsLowerCase = Character.isLowerCase(s.charAt(0));
            for (Tile tile : playerRack) {
                if (charIsLowerCase && tile.getDisplayAsLetter().equals("_")) {
                    stringToBePlacedOnBoard.add(tile.getDisplayOnBoard().replace("_", s));
                    tileToBeSetOnSquare.add(tile);
                    playerRack.remove(tile);
                    break;
                }

                if (s.equals(tile.getDisplayAsLetter())) {
                    stringToBePlacedOnBoard.add(tile.getDisplayOnBoard());
                    tileToBeSetOnSquare.add(tile);
                    playerRack.remove(tile);
                    break;
                }
            }
        }
        System.out.println("wordMoveList " + wordMoveList);
        System.out.println("tileToBeSet " + tileToBeSetOnSquare);
        System.out.println("squaresToBeOccupied " + squaresToBeOccupied);
        // depending on the move direction, update the display of the square
        // and set the Tile object to the corresponding square on board
        for (int i = 0; i < wordMoveList.size(); i++) {
            int stringSize = stringToBePlacedOnBoard.get(i).length();

            if (this.currentMove.getMoveDirection().equals("RIGHTWARD")) {
                gb.getSquareByIndex(x, y + i).setSquareDisplayOnBoard(stringSize > 2 ? stringToBePlacedOnBoard.get(i) : stringToBePlacedOnBoard.get(i) + " ");
                this.setTileOnSquare(gb, tileToBeSetOnSquare, x, y, i);
                int squareScore = gb.getSquareByIndex(x, y + i).getSquareScore();
                int tileScore = gb.getSquareByIndex(x, y + i).getTileOnSquare().getTileScore();
                System.out.println(squareScore * tileScore);
            } else {
                gb.getSquareByIndex(x + i, y).setSquareDisplayOnBoard(stringSize > 2 ? stringToBePlacedOnBoard.get(i) : stringToBePlacedOnBoard.get(i) + " ");
                this.setTileOnSquare(gb, tileToBeSetOnSquare, x, y, i);
                int squareScore = gb.getSquareByIndex(x, y + i).getSquareScore();
                int tileScore = gb.getSquareByIndex(x, y + i).getTileOnSquare().getTileScore();
                System.out.println(squareScore * tileScore);
            }

        }
        gb.printGameBoard();
        // refills tile rack once everything operation is done
        this.gameItems.getHumansTileRack().refillUserRack();
        this.updatePlayerScore(this.calculateScore(tileToBeSetOnSquare,squaresToBeOccupied));
        System.out.println("after this: " + this.getHumanPlayer().getPlayerScore());
    }

    // helper method - reduces duplication
    private void setTileOnSquare(GameBoard gb, List<Tile> tileToBeSetOnSquare, int x, int y, int i) {
        gb.getSquareByIndex(x, y + i).setTileOnSquare(tileToBeSetOnSquare.get(i));
    }

    private int calculateScore(List<Tile> tileToBeSetOnSquare, List<Square> squaresToBeOccupied) {
        int result = 0;
        int premiumWordCumulative = 1;
        boolean hasPremiumWord = false;

        for (int i = 0; i < squaresToBeOccupied.size(); i++) {
            Square currentSquare = squaresToBeOccupied.get(i);

            if(currentSquare.getSquareType() == SquareType.PREMIUM_WORD) {
                hasPremiumWord = true;
                result += tileToBeSetOnSquare.get(i).getTileScore();
                premiumWordCumulative *= currentSquare.getSquareScore();
            }

            if(currentSquare.getSquareType() == SquareType.PREMIUM_LETTER || currentSquare.getSquareType() == SquareType.NORMAL) {
                System.out.println("tile score " + tileToBeSetOnSquare.get(i).getTileScore());
                System.out.println("square score " + squaresToBeOccupied.get(i).getSquareScore());
                result += tileToBeSetOnSquare.get(i).getTileScore() * squaresToBeOccupied.get(i).getSquareScore();
            }
        }
        System.out.println(hasPremiumWord + " has premium word");
        System.out.println("before PW: " + result);

        if(hasPremiumWord){
            return result * premiumWordCumulative;
        }
        System.out.println("final result " + result);
        return result;
    }

    private void updatePlayerScore(int score) {
        this.getHumanPlayer().updateScore(score);
    }

}
