package GameBoard;


import Tile.Tile;

import java.util.ArrayList;
import java.util.List;
public class GameBoard {
    List<List<Tile>> allTiles;

    public GameBoard (){}

    public void setUpTiles(int boardSize){
        allTiles = new ArrayList<>(boardSize);
    }

    public List<List<Tile>> getTileSpace(){
        return this.allTiles;
    }

}
