package pij.main.Tile;

import java.util.LinkedHashMap;
import java.util.Map;

public class TileBag {
    // this field stores all the tiles available for both players (a pool)
    public Map<String, Integer> tileMap;

    public TileBag(){
        // LinkedHashMap is used so the tiles are ordered by sequence of addition
        // is it necessary to have it ordered for the gameplay? probably not, but useful for debugging
        tileMap = new LinkedHashMap<>();
        this.tileMap.put("A1",8);
        this.tileMap.put("B3",3);
        this.tileMap.put("C3",2);
        this.tileMap.put("D2",4);
        this.tileMap.put("E1",10);
        this.tileMap.put("F4",3);
        this.tileMap.put("G2",4);
        this.tileMap.put("H4",3);
        this.tileMap.put("I1",8);
        this.tileMap.put("J9",1);
        this.tileMap.put("K6",1);
        this.tileMap.put("L1",4);
        this.tileMap.put("M3",2);
        this.tileMap.put("N1",7);
        this.tileMap.put("O1",7);
        this.tileMap.put("P3",2);
        this.tileMap.put("Q12",1);
        this.tileMap.put("R1",6);
        this.tileMap.put("S1",4);
        this.tileMap.put("T1",6);
        this.tileMap.put("U1",5);
        this.tileMap.put("V4",2);
        this.tileMap.put("W4",1);
        this.tileMap.put("X9",1);
        this.tileMap.put("Y5",2);
        this.tileMap.put("Z11",1);
        // wild card
        this.tileMap.put("_5",2);
    }

    public Map<String, Integer> getTileMap() {
        return tileMap;
    }

    @Override
    public String toString() {
        return "TileBag{" + "tileMap=" + tileMap + '}';
    }
}
