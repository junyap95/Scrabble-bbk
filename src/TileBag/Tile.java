package TileBag;

public class Tile {
    private final int tileScore;
    private final String displayOnBoard;


    public String getDisplayOnBoard() {
        return this.displayOnBoard.toLowerCase();
    }

    public Tile(String displayOnBoard, int tileScore){
        this.displayOnBoard = displayOnBoard;
        this.tileScore = tileScore;
    }

    @Override
    public String toString() {
        return "[" + this.displayOnBoard + "]";

    }
}
