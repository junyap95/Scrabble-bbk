package pij.test.Move;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pij.main.GameBoard.GameBoard;
import pij.main.Move.Direction;
import pij.main.Move.Move;
import pij.main.Square.Square;
import pij.main.TileBag.Tile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MoveTest {

    @Test
    void getSquareMoveByIndex() {
        int x =0, y = 0;
        String squareMoveRight = "1a";
        String squareMoveDown = "a1";
        Assertions.assertEquals(squareMoveRight, Move.getSquareMoveByIndex(x,y, Direction.RIGHTWARD));
        assertEquals(squareMoveDown, Move.getSquareMoveByIndex(x,y, Direction.DOWNWARD));
    }

    @Test
    void getMoveDirection() {
        GameBoard gameboard = mock(GameBoard.class);
        Move move = new Move("A", "8h", 7,7, gameboard);
        assertEquals(Direction.RIGHTWARD, move.getMoveDirection());
    }

    @Test
    void getWordMove() {
    }

    @Test
    void getSquareMove() {
    }

    @Test
    void getWordMoveList() {
    }

    @Test
    void getStartSquareX() {
    }

    @Test
    void getStartSquareY() {
    }

    @Test
    void getStartSquare() {
    }

    @Test
    void getWordFormed() {
        GameBoard gameboard = mock(GameBoard.class);

        List<Square> row1 = new ArrayList<>();
        List<Square> occupiedSquares = new ArrayList<>();
        List<Square> unoccupiedSquares = new ArrayList<>();
        Square mockSquare1 = mock(Square.class); // C
        Square mockSquare2 = mock(Square.class);
        Square mockSquare3 = mock(Square.class);// T

        Tile mockTile1 = mock(Tile.class); // C
        Tile mockTile3 = mock(Tile.class); // T


        row1.add(mockSquare1);
        row1.add(mockSquare2);
        row1.add(mockSquare3);

        int x = 0, y = 1;
        when(gameboard.getSquareByIndex(x, y)).thenReturn(mockSquare2);
        when(mockSquare1.getLeftNeighbour()).thenReturn(null);
        when(mockSquare1.getRightNeighbour()).thenReturn(mockSquare2);
        when(mockSquare1.isSquareOccupied()).thenReturn(true);
        when(mockSquare1.getTileOnSquare()).thenReturn(mockTile1);
        when(mockTile1.getDisplayAsLetter()).thenReturn("C");

        when(mockSquare2.getLeftNeighbour()).thenReturn(mockSquare1);
        when(mockSquare2.getRightNeighbour()).thenReturn(mockSquare3);
        when(mockSquare2.isSquareOccupied()).thenReturn(false);

        when(mockSquare3.getLeftNeighbour()).thenReturn(mockSquare2);
        when(mockSquare3.getRightNeighbour()).thenReturn(null);
        when(mockSquare3.isSquareOnRightEdge()).thenReturn(true);
        when(mockSquare3.isSquareOccupied()).thenReturn(true);
        when(mockSquare3.getTileOnSquare()).thenReturn(mockTile3);
        when(mockTile3.getDisplayAsLetter()).thenReturn("T");

        occupiedSquares.add(mockSquare1);
        unoccupiedSquares.add(mockSquare2);
        occupiedSquares.add(mockSquare3);

        Move move = new Move("A", "1b", x,y, gameboard);
        assertEquals("CAT", move.getWordFormed());

        assertEquals(row1, move.getListOfAllPlayableSquares());
        assertEquals(occupiedSquares, move.getListOfOccupiedSquares());
        assertEquals(unoccupiedSquares, move.getListOfSquaresToBeOccupied());
    }

    @Test
    void getListOfSquaresToBeOccupied() {
    }

    @Test
    void getTilesToBeSetOnSquare() {
    }

    @Test
    void getListOfAllPlayableSquares() {
    }

    @Test
    void getListOfOccupiedSquares() {
    }
}