package pij.main.GameBoard;

import pij.main.GameRunner.GameTextPrinter;
import pij.main.Square.Square;
import java.util.ArrayList;
import java.util.List;

/**
 * this class stores all the Squares to be played on
 * and used for the main board display
 * and helps move validation
 */
public class GameBoard {
    private static GameBoard instance = null; // WARNING: not thread safe
    private List<List<Square>> allSquaresOnBoard; // 2D arrayList to store all squares on the game board
    private int gameBoardSize;

    // Singleton - private constructor to prevent outside instantiation
    private GameBoard() {
        allSquaresOnBoard = new ArrayList<>();
    }

    // method - get the only instance of game board in a game
    public static GameBoard getGameBoard () {
        if(instance == null) {
            instance = new GameBoard();
        }
        return instance;
    }

    // method - board is empty only when not a single square is occupied by a tile
    public boolean isGameBoardEmpty() {
        for(List<Square> ls : this.allSquaresOnBoard) {
            for(Square square : ls) {
                if (square.isSquareOccupied()) {
                    return false; //if at least one square is occupied
                }
            }
        }
        return true;
    }

    // helper method - prints the board with correct format. i.e. rows and columns
    public void printGameBoard() {
        List<List<Square>> allSquaresOnBoard = this.getAllSquaresOnBoard();
        int yAxis = 1;

        GameTextPrinter.printAlphabetsRow(this.gameBoardSize);

        for (List<Square> innerArray : allSquaresOnBoard) {
            System.out.println();
            System.out.print(yAxis < 10 ? yAxis + " " : yAxis);
            for (Square square : innerArray) {
                System.out.print(square);
            }
            System.out.print(" " + yAxis);
            yAxis++;
        }

        System.out.println();
        GameTextPrinter.printAlphabetsRow(this.gameBoardSize);
        System.out.println();
        System.out.println();
    }

    // helper - for locating centre and for testing
    public int getCentreSquareIndex() {
        return this.gameBoardSize % 2 == 0 ? (this.gameBoardSize / 2) - 1 : this.gameBoardSize / 2;
    }

    public Square getCentreSquare() {
        return getAllSquaresOnBoard().get(getCentreSquareIndex()).get(getCentreSquareIndex());
    }

    public int getGameBoardSize() {
        return this.gameBoardSize;
    }

    public List<List<Square>> getAllSquaresOnBoard() {
        return this.allSquaresOnBoard;
    }

    public Square getSquareByIndex(int x, int y) {
        return getAllSquaresOnBoard().get(x).get(y);
    }

    public void setAllSquaresOnBoard(List<List<Square>> sortedTiles) {
        this.allSquaresOnBoard = sortedTiles;
    }

    public void setGameBoardSize(int gameBoardSize) {
        this.gameBoardSize = gameBoardSize;
    }
}
