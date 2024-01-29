package TileBag;

import java.util.*;

public class TileRow {
    public final int TILE_RACK_CAPACITY = 7;
    private List<Tile> userRow;

    public List<Tile> getUserRow() {
        return userRow;
    }

    public void setTileRow(TileBag tileBag) {
        Map<String, Integer> tileMap = tileBag.tileMap;
        Set<String> set = tileMap.keySet();
        List<String> setList = new ArrayList<>(set);
        userRow = new ArrayList<>(TILE_RACK_CAPACITY);
        Random rand = new Random();
        for (int i = 0; i < TILE_RACK_CAPACITY; i++) {
            int randomIndex = rand.nextInt(setList.size());
            String tileString = setList.get(randomIndex);

            if (tileMap.containsKey(tileString)) {
                userRow.add(new Tile(tileString, Integer.parseInt(tileString.substring(1))));
                int tileCount = (tileMap.get(tileString)) - 1;
                if(tileCount == 0) {
                    tileMap.remove(tileString);
                    setList.remove(tileString);
                } else {
                    tileMap.put(tileString, (tileMap.get(tileString)) - 1);
                }
            }

        }

    }

    public void printUserRow() {
        System.out.println(userRow);
    }
}
