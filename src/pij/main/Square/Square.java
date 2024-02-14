package pij.main.Square;

import pij.main.TileBag.Tile;

public class Square extends SquareNeighbours{
    private SquareType squareType;
    private String squareDisplayOnBoard;
    private Integer squareScore;

    public void setSquareOccupied() {
        isSquareOccupied = true;
    }

    private boolean isSquareOccupied;
    private Tile tileOnSquare;

    public Square(SquareType squareType, String squareDisplayOnBoard, int squareScore) {
        this.squareDisplayOnBoard = squareDisplayOnBoard;
        this.squareType = squareType;
        this.squareScore = squareScore;
        this.isSquareOccupied = false;
    }

    public SquareType getSquareType() {
        return squareType;
    }

    public Integer getSquareScore() {
        return squareScore;
    }

    public Tile getTileOnSquare() {
        return tileOnSquare;
    }

    public boolean isSquareOccupied() {
        return isSquareOccupied;
    }

    public void setTileOnSquare(Tile tileOnSquare) {
        this.tileOnSquare = tileOnSquare;
        this.isSquareOccupied = true;
    }

    @Override
    public String toString() {
        if (this.isSquareOccupied) {
            String tileDisplay = this.tileOnSquare.getDisplayOnBoard();
            return tileDisplay.length() > 2 ? tileDisplay : tileDisplay + " ";
        }
        return this.squareDisplayOnBoard;
        // return this.squareDisplayOnBoard;
    }
}
