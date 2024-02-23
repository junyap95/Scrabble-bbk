package pij.main.Tile;

import java.util.HashMap;
import java.util.Map;

/**
 * This class supplies a map of available tiles for the tile bag
 * Decoupled from the tile bag for brevity and ease of testing
 */
public class TileSupplier {
    public static Map<String, Integer> getTileSupply () {
        Map<String, Integer> tileSupply = new HashMap<>();
        tileSupply.put("A1", 8);
        tileSupply.put("B3", 2);
        tileSupply.put("C3", 2);
        tileSupply.put("D2", 4);
        tileSupply.put("E1", 10);
        tileSupply.put("F4", 3);
        tileSupply.put("G2", 4);
        tileSupply.put("H4", 3);
        tileSupply.put("I1", 8);
        tileSupply.put("J9", 1);
        tileSupply.put("K6", 1);
        tileSupply.put("L1", 4);
        tileSupply.put("M3", 2);
        tileSupply.put("N1", 7);
        tileSupply.put("O1", 7);
        tileSupply.put("P3", 2);
        tileSupply.put("Q12", 1);
        tileSupply.put("R1", 6);
        tileSupply.put("S1", 4);
        tileSupply.put("T1", 6);
        tileSupply.put("U1", 5);
        tileSupply.put("V4", 2);
        tileSupply.put("W4", 1);
        tileSupply.put("X9", 1);
        tileSupply.put("Y5", 2);
        tileSupply.put("Z11", 1);
        // wild card
        tileSupply.put("_5", 2);
        return tileSupply;
    }
}
