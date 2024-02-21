package pij.main.Tile;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//public class TileBag {
//    // this field stores all the tiles available for both players (a pool)
//    public Map<String, Integer> tileMap;
//
//    public TileBag(){
//        // LinkedHashMap is used so the tiles are ordered by sequence of addition (slower performance)
//        // is it necessary to have it ordered? probably not, but useful for debugging
//        tileMap = new LinkedHashMap<>();
//        this.tileMap.put("A1",8);
//        this.tileMap.put("B3",2);
//        this.tileMap.put("C3",2);
//        this.tileMap.put("D2",4);
//        this.tileMap.put("E1",10);
//        this.tileMap.put("F4",3);
//        this.tileMap.put("G2",4);
//        this.tileMap.put("H4",3);
//        this.tileMap.put("I1",8);
//        this.tileMap.put("J9",1);
//        this.tileMap.put("K6",1);
//        this.tileMap.put("L1",4);
//        this.tileMap.put("M3",2);
//        this.tileMap.put("N1",7);
//        this.tileMap.put("O1",7);
//        this.tileMap.put("P3",2);
//        this.tileMap.put("Q12",1);
//        this.tileMap.put("R1",6);
//        this.tileMap.put("S1",4);
//        this.tileMap.put("T1",6);
//        this.tileMap.put("U1",5);
//        this.tileMap.put("V4",2);
//        this.tileMap.put("W4",1);
//        this.tileMap.put("X9",1);
//        this.tileMap.put("Y5",2);
//        this.tileMap.put("Z11",1);
//        // wild card
//        this.tileMap.put("_5",2);
//    }
//
//    public Map<String, Integer> getTileMap() {
//        return tileMap;
//    }
//
//    // for debugging
//
//
//    @Override
//    public String toString() {
//        return "TileBag{" + "tileMap=" + tileMap + '}';
//    }
//}

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