package pij.main.TileBag;

public class Tile {
    private final int tileScore;
    private final String displayOnBoard;
    private final String displayAsLetter;

    public Tile(String displayOnBoard, int tileScore){
        this.displayOnBoard = displayOnBoard;
        this.tileScore = tileScore;
        this.displayAsLetter = displayOnBoard.substring(0,1);
    }

    public String getDisplayAsLetter() {
        return displayAsLetter;
    }

    public int getTileScore() {
        return tileScore;
    }

    @Override
    public String toString() {
        return "[" + this.displayOnBoard + "]";

    }
}
