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
        tileMap = new HashMap<>();
        tileMap.put("A1", 8);
        tileMap.put("B3", 2);
        tileMap.put("C3", 2);
        tileMap.put("D2", 4);
        tileMap.put("E1", 10);
        tileMap.put("F4", 3);
        tileMap.put("G2", 4);
        tileMap.put("H4", 3);
        tileMap.put("I1", 8);
        tileMap.put("J9", 1);
        tileMap.put("K6", 1);
        tileMap.put("L1", 4);
        tileMap.put("M3", 2);
        tileMap.put("N1", 7);
        tileMap.put("O1", 7);
        tileMap.put("P3", 2);
        tileMap.put("Q12", 1);
        tileMap.put("R1", 6);
        tileMap.put("S1", 4);
        tileMap.put("T1", 6);
        tileMap.put("U1", 5);
        tileMap.put("V4", 2);
        tileMap.put("W4", 1);
        tileMap.put("X9", 1);
        tileMap.put("Y5", 2);
        tileMap.put("Z11", 1);
        // wild card
        tileMap.put("_5", 2);
        return tileMap;
    }

    public Map<String, Integer> getTileMap() {
        return tileMap;
    }

    public int currentAmountOfTiles() {
        return tileMap.values().stream().mapToInt(n -> n).sum();
    }
}