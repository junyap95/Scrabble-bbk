package pij.main.Players;

import pij.main.Tile.TileBag;
import pij.main.Tile.TileRack;

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
