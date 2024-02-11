package pij.main.Players;

import pij.main.TileBag.TileRack;

import java.util.Scanner;

public interface Player {
     String move();
     public int getPlayerScore();
     public void updateScore(int playerScore);
}
