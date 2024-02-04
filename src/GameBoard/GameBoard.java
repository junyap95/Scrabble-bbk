package GameBoard;

import Square.Square;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private List<List<Square>> allSquaresOnBoard; // 2D arrayList to store all squares on the game board
    private int gameBoardSize;

    // constructor, initialises a list that stores list of squares
    public GameBoard() {
        allSquaresOnBoard = new ArrayList<>();
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

    public void setAllSquaresOnBoard(List<List<Square>> sortedTiles) {
        this.allSquaresOnBoard = sortedTiles;
    }

    public void setGameBoardSize(int gameBoardSize) {
        this.gameBoardSize = gameBoardSize;
    }


    public void printGameBoard() {
        List<List<Square>> allSquaresOnBoard = this.getAllSquaresOnBoard();
        int yAxis = 1;

        PrintingUtil.printAlphabetsRow(this.gameBoardSize);

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
        PrintingUtil.printAlphabetsRow(this.gameBoardSize);
        System.out.println();

    }
}
