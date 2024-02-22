package pij.test.Square;

import org.junit.jupiter.api.Test;
import pij.main.Square.Square;
import pij.main.Square.SquareType;
import pij.main.Tile.Tile;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    @Test
    void setTileOnSquare() {
        Tile tile = new Tile("A1", 1);
        Square square = new Square(SquareType.NORMAL, " . ", 1);
        square.setTileOnSquare(tile);
        assertTrue(square.isSquareOccupied());

        Square square2 = new Square(SquareType.NORMAL, " . ", 1);
        assertFalse(square2.isSquareOccupied());
    }

    @Test
    void getTileOnSquare() {
        Tile tile = new Tile("A1", 1);
        Tile tile1 = new Tile("A1", 1);
        Square square = new Square(SquareType.NORMAL, " . ", 1);
        square.setTileOnSquare(tile);

        assertNotEquals(tile1, square.getTileOnSquare());
        assertEquals(tile, square.getTileOnSquare());
    }


}