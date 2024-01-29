package GameBoard;


import Square.Square;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    List<List<Square>> allTiles;
    private int gameBoardSize;

    public GameBoard() {
        allTiles = new ArrayList<>();
    }

    public void setTileSpace(List<List<Square>> sortedTiles) {
        this.allTiles = sortedTiles;
    }

    public List<List<Square>> getTileSpace() {
        return this.allTiles;
    }

    public void printGameBoard() {
        List<List<Square>> allTiles = this.getTileSpace();
        int yAxis = 1;

        PrintingUtil.printAlphabetsRow(this.gameBoardSize);

        for (List<Square> innerArray : allTiles) {
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

    public void setGameBoardSize(int gameBoardSize) {
        this.gameBoardSize = gameBoardSize;
    }

    private int getCentreIndex() {
        return this.gameBoardSize % 2 == 0 ? (this.gameBoardSize / 2) - 1 : this.gameBoardSize / 2;
    }


}
