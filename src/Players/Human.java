package Players;

import TileBag.Tile;
import TileBag.TileRack;

import java.util.ArrayList;
import java.util.List;

public class Human extends Player{

    void updateRack(String userMove, TileRack tileRack) {
        String tilePlayed = userMove.split(",")[0];
        List<Character> tiles = new ArrayList<>();
        for (char c : tilePlayed.toCharArray()) {
            tiles.add(c);
        }

        List<Tile> updatedRack = tileRack.getUserRow().stream()
                .filter(t -> !tiles.contains(t.getDisplayOnBoard().charAt(0)))
                .toList();

        tileRack.setUserRow(updatedRack);
    }

    void updateGameboard(String userMove){
        String tilePlayed = userMove.split(",")[1];

    }
//        System.out.println(playThis);
//        String pieceMove = playThis.split(",")[0];
//        System.out.println(pieceMove);
//        String coordinateMove =playThis.split(",")[1];
//        System.out.println(coordinateMove);





    //    public void updateUserRack(TileRack userRack){

//
//        List<Tile> updatedList = userRack.getUserRow().stream()
//                .filter(t->!tiles.contains(t.getDisplay().charAt(0)))
//                .toList();
//
//        userRack.setUserRow(updatedList);
//    }


}
