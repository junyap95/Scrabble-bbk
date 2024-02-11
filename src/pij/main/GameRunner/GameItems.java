package pij.main.GameRunner;

import pij.main.GameBoard.GameBoard;
import pij.main.TileBag.TileBag;
import pij.main.TileBag.TileRack;

public class GameItems {
    public TileBag getTileBag() {
        return tileBag;
    }

    public void setTileBag(TileBag tileBag) {
        this.tileBag = tileBag;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public TileRack getHumansTileRack() {
        return humansTileRack;
    }

    public void setHumansTileRack(TileRack humansTileRack) {
        this.humansTileRack = humansTileRack;
    }

    public TileRack getComputersTileRack() {
        return computersTileRack;
    }

    public void setComputersTileRack(TileRack computersTileRack) {
        this.computersTileRack = computersTileRack;
    }

    private void setTileRack(TileRack tileRack, TileBag tileBag) {
        tileRack.setTileBag(tileBag);
    }

    // shared game items
    TileBag tileBag;
    GameBoard gameBoard;
    // player related fields
    TileRack humansTileRack;
    TileRack computersTileRack;

    public GameItems() {
        this.tileBag = new TileBag();
        this.humansTileRack = new TileRack();
        this.setTileRack(this.getHumansTileRack(),this.getTileBag());
        this.computersTileRack = new TileRack();
        this.setTileRack(this.getComputersTileRack(),this.getTileBag());
        this.gameBoard = new GameBoard();
    }
}
