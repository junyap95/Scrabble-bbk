package Square;

import TileBag.Tile;

public class Square {

    private SquareType squareType;



    private String display;
    private Integer squareScore;
    private Tile tilePlaced;

    public Square(SquareType squareType, String display, int squareScore) {
        this.display = display;
        this.squareType = squareType;
        this.squareScore = squareScore;
    }

    public SquareType getSquareType() {
        return this.squareType;
    }

    public boolean setTile(Tile tile) {
        if (this.tilePlaced != null) {
            return false;
        }
        this.tilePlaced = tile;
        return true;

    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return this.display;
    }
}
