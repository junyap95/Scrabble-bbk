package pij.main;

import GameBoard.GameBoard;
import FileReader.*;
import TileBag.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileProcessor fp = new FileProcessor();
        GameBoard gb = new GameBoard();
        fp.bannerPrinter();
        //ask user load or default
//        Scanner scan = new Scanner(System.in);
//        String userChoice;
//        System.out.println("Would you like to _l_oad a board or use the _d_efault board?" +
//                           "\n" + "Please enter your choice (l/d): ");
//        userChoice = scan.nextLine();
//        if(userChoice.equals("d")) {
//            fp.fileProcessor(gb, "defaultBoard.txt");
//            gb.printGameBoard();
//        }
        //if board size ok proceed to process
        //...
        fp.fileProcessor(gb, "defaultBoard.txt");
        gb.printGameBoard();
        TileBag tileBag = new TileBag();
        System.out.println("Before bag " + tileBag.tileMap);
        TileRow userRow = new TileRow();
        TileRow computerRow = new TileRow();
        userRow.setTileRow(tileBag);
        System.out.println("YOU:");
        userRow.printUserRow();
        computerRow.setTileRow(tileBag);
        System.out.println("COMP:");
        computerRow.printUserRow();
        System.out.println("After bag " + tileBag.tileMap);




    }

}

