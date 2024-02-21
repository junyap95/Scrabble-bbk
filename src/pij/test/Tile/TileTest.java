package pij.test.Tile;

import org.junit.jupiter.api.Test;
import pij.main.Tile.Tile;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void getDisplayOnBoard() {
        Tile tile1 = new Tile("A1", 1);
        Tile tile2 = new Tile("B3", 3);

        String expectedDisplay1 = "A1";
        String displayOnBoard1 = tile1.getDisplayOnBoard();
        assertEquals(expectedDisplay1, displayOnBoard1);

        String expectedDisplay2 = "B3";
        String displayOnBoard2 = tile2.getDisplayOnBoard();
        assertEquals(expectedDisplay2, displayOnBoard2);

    }

    @Test
    void getDisplayAsLetter() {
        Tile tile1 = new Tile("C3", 3);
        Tile tile2 = new Tile("D4", 4);

        String expectedDisplay1 = "C";
        String displayOnBoard1 = tile1.getDisplayAsLetter();
        assertEquals(expectedDisplay1, displayOnBoard1);

        String expectedDisplay2 = "D";
        String displayOnBoard2 = tile2.getDisplayAsLetter();
        assertEquals(expectedDisplay2, displayOnBoard2);
    }

    @Test
    void getTileScore() {
        Tile tile1 = new Tile("E3", 3);
        Tile tile2 = new Tile("F4", 4);

        int expectedDisplay1 = 3;
        int displayOnBoard1 = tile1.getTileScore();
        assertEquals(expectedDisplay1, displayOnBoard1);

        int expectedDisplay2 = 4;
        int displayOnBoard2 = tile2.getTileScore();
        assertEquals(expectedDisplay2, displayOnBoard2);
    }
}