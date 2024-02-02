package Players;

import TileBag.Tile;
import TileBag.TileRack;

import java.util.ArrayList;
import java.util.List;

public class Human extends Player{

    @Override
    public String play() {
        return scanner.nextLine();
//        System.out.println(playThis);
//        String pieceMove = playThis.split(",")[0];
//        System.out.println(pieceMove);
//        String coordinateMove =playThis.split(",")[1];
//        System.out.println(coordinateMove);
    }

//    public void updateUserRack(TileRack userRack){
//        List<Character> tiles = new ArrayList<>();
//        for(char c : this.play().toCharArray()) {
//            tiles.add(c);
//        }
//
//        List<Tile> updatedList = userRack.getUserRow().stream()
//                .filter(t->!tiles.contains(t.getDisplay().charAt(0)))
//                .toList();
//
//        userRack.setUserRow(updatedList);
//    }


}
