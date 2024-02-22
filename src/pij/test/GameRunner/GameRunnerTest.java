package pij.test.GameRunner;

import org.junit.jupiter.api.Test;
import pij.main.GameBoard.GameBoard;
import pij.main.GameRunner.GameCounters;
import pij.main.GameRunner.GameRunner;
import pij.main.Players.ComputerPlayer;
import pij.main.Players.HumanPlayer;
import pij.main.Players.Player;
import pij.main.Tile.TileBag;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GameRunnerTest {

    @Test
    void isGameOverWithPassCounter() {
        GameBoard gameBoard = mock(GameBoard.class);
        TileBag tileBag = TileBag.getTileBag();
        tileBag.resetTileMap();
        Player humanPlayer = new HumanPlayer(tileBag);
        Player computerPlayer = new ComputerPlayer(tileBag, gameBoard);
        humanPlayer.getTileRack().refillPlayerRack();
        computerPlayer.getTileRack().refillPlayerRack();
        GameCounters gameCounters = new GameCounters();
        GameRunner gameRunner = new GameRunner(gameBoard, humanPlayer, computerPlayer, gameCounters);
        GameCounters counter = gameRunner.getGameCounters();
        counter.incrementPassCounter();
        assertFalse(gameRunner.isGameOver());

        counter.incrementPassCounter();
        assertFalse(gameRunner.isGameOver());

        counter.incrementPassCounter();
        assertFalse(gameRunner.isGameOver());

        counter.incrementPassCounter();
        assertTrue(gameRunner.isGameOver());
    }

    @Test
    void isGameOverWithPassCounterReset() {
        GameBoard gameBoard = mock(GameBoard.class);
        TileBag tileBag = TileBag.getTileBag();
        tileBag.resetTileMap();
        Player humanPlayer = new HumanPlayer(tileBag);
        Player computerPlayer = new ComputerPlayer(tileBag, gameBoard);
        humanPlayer.getTileRack().refillPlayerRack();
        computerPlayer.getTileRack().refillPlayerRack();
        GameCounters gameCounters =new GameCounters();
        GameRunner gameRunner = new GameRunner(gameBoard, humanPlayer, computerPlayer, gameCounters);
        GameCounters counter = gameRunner.getGameCounters();
        counter.incrementPassCounter();
        assertFalse(gameRunner.isGameOver());

        counter.incrementPassCounter();
        assertFalse(gameRunner.isGameOver());

        counter.incrementPassCounter();
        assertFalse(gameRunner.isGameOver());

        counter.resetPassCounter();

        counter.incrementPassCounter();
        assertFalse(gameRunner.isGameOver());
    }

    @Test
    void isGameOverWithNonEmptyRacks() {
        TileBag tileBag = TileBag.getTileBag();
        tileBag.resetTileMap();
        GameBoard gameBoard = mock(GameBoard.class);
        Player humanPlayer = new HumanPlayer(tileBag);
        Player computerPlayer = new ComputerPlayer(tileBag, gameBoard);
        GameCounters gameCounters = mock(GameCounters.class);
        GameRunner gameRunner = new GameRunner(gameBoard, humanPlayer, computerPlayer, gameCounters);

        humanPlayer.getTileRack().refillPlayerRack();
        computerPlayer.getTileRack().refillPlayerRack();

        assertFalse(gameRunner.isGameOver());
    }

    @Test
    void isGameOverWithEmptyRacks() {
        TileBag tileBag = TileBag.getTileBag();
        tileBag.resetTileMap();
        GameBoard gameBoard = mock(GameBoard.class);
        Player humanPlayer = new HumanPlayer(tileBag);
        Player computerPlayer = new ComputerPlayer(tileBag, gameBoard);
        GameCounters gameCounters = mock(GameCounters.class);
        GameRunner gameRunner = new GameRunner(gameBoard, humanPlayer, computerPlayer, gameCounters);

        humanPlayer.getTileRack().refillPlayerRack();
        computerPlayer.getTileRack().refillPlayerRack();

        var humanPlayerTiles = humanPlayer.getTileRack().getPlayersTiles();

        while(!humanPlayerTiles.isEmpty()) {
            assertFalse(gameRunner.isGameOver());
            humanPlayerTiles.removeFirst();
        }
        assertTrue(gameRunner.isGameOver());
    }
}