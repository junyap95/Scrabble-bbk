package pij.main.Players;

import pij.main.Tile.TileBag;
import pij.main.Tile.TileRack;

/**
 * A superclass for either a human or computer player
 * has required fields for a player such as score and tile racks
 * and methods for making a move and updating score
 */
public abstract class Player {
     private int playerScore;
     private TileRack tileRack;

     public Player(TileBag tileBag) {
          this.playerScore = 0;
          this.tileRack = new TileRack(tileBag);
     }

     public int getPlayerScore() {
          return this.playerScore;
     }

     public TileRack getTileRack() {
          return tileRack;
     }

     public abstract String move();

     public void updateScore(int playerScore) {
          this.playerScore += playerScore;
     }
}
