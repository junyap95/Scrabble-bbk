package TileBag;

import java.util.*;

public class TileRack {
    public final int TILE_RACK_CAPACITY = 7;
    private List<Tile> userRow = new ArrayList<>(TILE_RACK_CAPACITY);

    public List<Tile> getUserRow() {
        return this.userRow;
    }

    public void removeFromUserRow(Tile tile) {
        this.getUserRow().remove(tile);
    }

    public void setUserRow(List<Tile> userRow) {
        this.userRow = userRow;
    }

    public void refillUserRack(TileBag tileBag) {
        Map<String, Integer> tileMap = tileBag.getTileMap();
        List<String> setList = new ArrayList<>(tileMap.keySet());

        Random rand = new Random();
        for (int i = 0; i < TILE_RACK_CAPACITY; i++) {
            int randomIndex = rand.nextInt(setList.size());
            String tileString = setList.get(randomIndex);

            if (tileMap.containsKey(tileString)) {
                this.userRow.add(new Tile(tileString, Integer.parseInt(tileString.substring(1))));
                int tileCount = (tileMap.get(tileString)) - 1;
                if (tileCount == 0) {
                    tileMap.remove(tileString);
                    setList.remove(tileString);
                } else {
                    tileMap.put(tileString, tileCount);
                }
            }
        }
    }

    public void printUserRow() {
        System.out.println(this.userRow);
    }

    @Override
    public String toString() {
        return this.getUserRow().toString().substring(1, this.getUserRow().toString().length()-1);
    }
}
