package pij.main.Tile;

public class Tile {
    private final int tileScore;
    private String displayOnBoard;
    private String displayAsLetter;

    public Tile(String displayOnBoard, int tileScore){
        this.displayOnBoard = displayOnBoard;
        this.tileScore = tileScore;
        this.displayAsLetter = displayOnBoard.substring(0,1);
    }

    public void setDisplayOnBoard(String displayOnBoard) {
        this.displayOnBoard = displayOnBoard;
        this.displayAsLetter = displayOnBoard.substring(0,1);;
    }

    public String getDisplayOnBoard() {
        return displayOnBoard;
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
