package pij.main.GameBoard;

import pij.main.GameRunner.GameTextPrinter;
import pij.main.Square.Square;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private static GameBoard instance = null; // WARNING: not thread safe
    private List<List<Square>> allSquaresOnBoard; // 2D arrayList to store all squares on the game board
    private int gameBoardSize;

    // Singleton - private constructor to prevent outside instantiation
    private GameBoard() {
        allSquaresOnBoard = new ArrayList<>();
    }

    public static GameBoard getGameBoard () {
        if(instance == null) {
            instance = new GameBoard();
        }
        return instance;
    }

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

    public int getGameBoardSize() {
        return this.gameBoardSize;
    }

    public List<List<Square>> getAllSquaresOnBoard() {
        return this.allSquaresOnBoard;
    }

    public int getCentreSquareIndex() {
        return this.gameBoardSize % 2 == 0 ? (this.gameBoardSize / 2) - 1 : this.gameBoardSize / 2;
    }

    public Square getCentreSquare() {
        return getAllSquaresOnBoard().get(getCentreSquareIndex()).get(getCentreSquareIndex());
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
