package TileBag;

public class Tile {
    private final int tileScore;
    private final String display;

    public String getDisplay() {
        return this.display.toLowerCase();
    }

    public Tile(String display, int tileScore){
        this.display = display;
        this.tileScore = tileScore;
    }

    @Override
    public String toString() {
        return "[" + this.display + "]";

    }
}
