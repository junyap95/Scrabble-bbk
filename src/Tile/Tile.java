package Tile;

public class Tile {
    private TileType tileType;
    private String display;
    private Integer tileScore;

    public Tile (TileType tileType, String display, int tileScore){
        this.display = display;
        this.tileType = tileType;
        this.tileScore = tileScore;
    }

    public TileType getTileType () {
        return this.tileType;
    }

    @Override
    public String toString() {
        return this.display;
    }
}
