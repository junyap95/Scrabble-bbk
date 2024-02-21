package pij.test.Tile;

import org.junit.jupiter.api.Test;
import pij.main.Tile.Tile;
import pij.main.Tile.TileBag;
import pij.main.Tile.TileRack;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TileRackTest {

    @Test
    void testGetPlayersTilesAsLetters() {
        Tile mockTile1 = new Tile("A", 1);
        Tile mockTile2 = new Tile("B", 3);
        Tile mockTile3 = new Tile("C", 3);
        TileBag mockBag = mock(TileBag.class);

        TileRack tileRack = new TileRack(mockBag);
        tileRack.getPlayersTiles().add(mockTile1);
        tileRack.getPlayersTiles().add(mockTile2);
        tileRack.getPlayersTiles().add(mockTile3);

        List<String> expectedLetters = new ArrayList<>();
        expectedLetters.add("A");
        expectedLetters.add("B");
        expectedLetters.add("C");

        List<String> playersTilesAsLetters = tileRack.getPlayersTilesAsLetters();

        assertEquals(expectedLetters, playersTilesAsLetters);
    }
}