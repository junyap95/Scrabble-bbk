package pij.main.GameRunner;

import pij.main.GameBoard.GameBoard;
import pij.main.Tile.TileBag;

public class GameItems {
    // shared game items
    TileBag tileBag;
    GameBoard gameBoard;

    public GameItems() {
        this.gameBoard = new GameBoard(); // 1
        this.tileBag = new TileBag(); // 2
    }

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
}
