package pij.main.Square;

import pij.main.TileBag.Tile;

public class Square {
    private SquareType squareType;
    private String squareDisplayOnBoard;
    private Integer squareScore;
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

    public String getSquareDisplayOnBoard() {
        return squareDisplayOnBoard;
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

    public void setSquareDisplayOnBoard(String squareDisplayOnBoard) {
        this.squareDisplayOnBoard = squareDisplayOnBoard;
    }

    public void setTileOnSquare(Tile tileOnSquare) {
        this.tileOnSquare = tileOnSquare;
        this.isSquareOccupied = true;
    }

    public void setSquareOccupied(boolean squareOccupied) {
        isSquareOccupied = squareOccupied;
    }

    @Override
    public String toString() {
        return this.squareDisplayOnBoard;
    }
}
