package pij.main.Players;

import pij.main.TileBag.TileBag;
import pij.main.TileBag.TileRack;

public class ComputerPlayer extends Player {

    public ComputerPlayer(TileBag tileBag) {
        super(tileBag);
    }

    @Override
    public String move() {
        return ",";
    }
}
