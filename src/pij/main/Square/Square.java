package pij.main.Square;

import pij.main.Tile.Tile;
import java.util.Objects;

public class Square extends SquareNeighbours {
    private final SquareType squareType;
    private final String squareDisplayOnBoard;
    private String coordinates;
    private final Integer squareScore;
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

    public String getSquareCoordinates() {
        return this.coordinates;
    }

    public void setSquareCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isSquareOccupied() {
        return isSquareOccupied;
    }

    public void setTileOnSquare(Tile tileOnSquare) {
        this.tileOnSquare = tileOnSquare;
        this.isSquareOccupied = true;
    }

    // when a square is occupied by a tile, display the tile instead
    @Override
    public String toString() {
        if (this.isSquareOccupied) {
            String tileDisplay = this.tileOnSquare.getDisplayOnBoard();
            return tileDisplay.length() > 2 ? tileDisplay : tileDisplay + " ";
        }
        return this.squareDisplayOnBoard;
    }

    // to compare two squares - e.g. in the first round when centre square is required
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Square square = (Square) object;
        return isSquareOccupied == square.isSquareOccupied && squareType == square.squareType && Objects.equals(squareDisplayOnBoard, square.squareDisplayOnBoard) && Objects.equals(coordinates, square.coordinates) && Objects.equals(squareScore, square.squareScore) && Objects.equals(tileOnSquare, square.tileOnSquare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(squareType, squareDisplayOnBoard, coordinates, squareScore, isSquareOccupied, tileOnSquare);
    }
}
