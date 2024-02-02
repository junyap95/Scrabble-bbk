package TileBag;

import static org.junit.jupiter.api.Assertions.*;

class TileRowTest {

    @org.junit.jupiter.api.Test
    void setTileRow() {
        TileRack tileRow = new TileRack();
        tileRow.refillUserRack(new TileBag());
        assertEquals(tileRow.getUserRow().size(),  tileRow.TILE_RACK_CAPACITY);
    }

    @org.junit.jupiter.api.Test
    void printUserRow() {
    }
}