package pij.main.Tile;

import java.util.HashMap;
import java.util.Map;

public class TileBag {
    private Map<String, Integer> tileMap = null;
    private static TileBag instance; // WARNING: not thread safe

    // Singleton - private constructor to prevent outside instantiation
    private TileBag() {
        tileMap = generateTileMap(); // lazy initialisation
    }

    public static TileBag getTileBag() {
        if (instance == null) {
            instance = new TileBag();
        }
        return instance;
    }

    private Map<String, Integer> generateTileMap() {
        return TileSupplier.getTileSupply();
    }

    public Map<String, Integer> getTileMap() {
        return tileMap;
    }

    public void resetTileMap() {
        this.tileMap = TileSupplier.getTileSupply();
    }

    public int currentAmountOfTiles() {
        return tileMap.values().stream().mapToInt(n -> n).sum();
    }
}