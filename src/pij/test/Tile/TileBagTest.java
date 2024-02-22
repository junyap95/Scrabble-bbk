package pij.test.Tile;

import org.junit.jupiter.api.Test;
import pij.main.Tile.TileBag;
import pij.main.Tile.TileRack;
import pij.main.Tile.TileSupplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TileBagTest {

    @Test
    void initialAmountOfTiles() {
        TileBag tileBag = TileBag.getTileBag();
        tileBag.resetTileMap();

        int TOTAL_AMT_OF_TILES = 99;
        int noOfTilesInBag = tileBag.currentAmountOfTiles();

        assertEquals(TOTAL_AMT_OF_TILES, noOfTilesInBag);
    }

    @Test
    void amountOfTilesAfterFirstRefill() {
        TileBag tileBag = TileBag.getTileBag();
        tileBag.resetTileMap();

        TileRack mockRack1 = new TileRack(tileBag);
        TileRack mockRack2 = new TileRack(tileBag);
        mockRack1.refillPlayerRack();
        mockRack2.refillPlayerRack();

        int TOTAL_AMT_OF_TILES = 99 - 14;

        int noOfTilesInBag = tileBag.currentAmountOfTiles();
        assertEquals(TOTAL_AMT_OF_TILES, noOfTilesInBag);

    }
}