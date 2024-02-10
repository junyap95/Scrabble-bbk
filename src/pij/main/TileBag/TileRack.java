package pij.main.TileBag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TileRack {
    public final int TILE_RACK_CAPACITY = 7;
    private List<Tile> playersRack = new ArrayList<>();

    public void refillUserRack(TileBag tileBag) {
        // obtain all the available tiles and individual amount from the pool
        // when refilling player's racks, the pool will update the amount of tiles
        Map<String, Integer> tileMap = tileBag.getTileMap();
        // create a set list, with no duplicates (i.e. tile A to Z and wildcard)
        // this is to allow for random tile allocation for every refill action
        List<String> setList = new ArrayList<>(tileMap.keySet());
        Random rand = new Random();
        int numOfTilesToBeRefilled = TILE_RACK_CAPACITY - this.playersRack.size();

        // the loop will refill 'missing tiles' from the rack of capacity 7
        // e.g. during round 1, playersRack.size() is 0, so the loop will run (7-0) times
        // e.g. assume a player played 3 tiles in a round, his playerRack.size() becomes 4
        // e.g. in the next round, the loop will run (7-4) times, refilling 3 tiles for him
        for (int i = 0; i < numOfTilesToBeRefilled; i++) {
            // a random index is generated in range of 0-26
            int randomIndex = rand.nextInt(setList.size());
            // the random index is used to choose a tile from the set list
            String tileString = setList.get(randomIndex); // 0-25 = A-Z
            // for the key stored in the pool, the second char of the String is the score
            int tileScore = Integer.parseInt(tileString.substring(1));

            // updating the tile pool
            if (tileMap.containsKey(tileString)) {
                this.playersRack.add(new Tile(tileString, tileScore));
                // the tile added to the player's rack will be 'deducted' from the pool, i.e. updated tile count
                int tileCount = (tileMap.get(tileString)) - 1;
                // if all of this specific tile are taken (value in pool is 0 after 'deduction'), remove this tile from the pool map
                if (tileCount == 0) {
                    tileMap.remove(tileString);
                    // the specific tile will be removed from the above set list, so it will not be randomly chosen anymore
                    setList.remove(tileString);
                } else {
                    tileMap.put(tileString, tileCount);
                }
            }
        }
    }

    public List<Tile> getPlayersRack() {
        return playersRack;
    }


    public void printUserRack() {
        System.out.println(this.playersRack);
    }

    @Override
    public String toString() {
        // returns the player's rack without the [] enclosing the tiles
        return this.playersRack.toString().substring(1, this.playersRack.toString().length() - 1);
    }
}
