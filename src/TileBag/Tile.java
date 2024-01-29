package TileBag;

public class Tile {
    private final int tileScore;
    private final String display;

    public Tile(String display, int tileScore){
        this.display = display;
        this.tileScore = tileScore;
    }

    @Override
    public String toString() {
        return "[" + this.display +" "+ this.tileScore + "]";

    }
}
