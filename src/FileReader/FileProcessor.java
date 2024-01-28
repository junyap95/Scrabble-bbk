package FileReader;

import GameBoard.GameBoard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Tile.*;

public class FileProcessor {
    File boardLoaded;

    public void bannerPrinter() {
        System.out.println("============" + "                     " + "============");
        System.out.println("============" + "  S k r a B B k l e  " + "============");
        System.out.println("============" + "                     " + "============");
    }

    Scanner scan = new Scanner(System.in);
    String userChoice;

    /**
     * Prompts the user to choose between loading a custom board or using the default board.
     * It repeats the prompt until a valid choice ('l' or 'd') is entered.
     */
    // make sure there is an exit/close down program option, no infinite ...
//        do
//
//    {
//        System.out.println("Would you like to _l_oad a board or use the _d_efault board?\n" + "Please enter your choice (l/d): ");
//        userChoice = scan.nextLine();
//    } while(!userChoice.equals("l")&&!userChoice.equals("d"));
//
//        if(userChoice.equals("l"))
//
//    {
//        System.out.println("You have chosen to load a custom board.");
//    } else
//
//    {
//        System.out.println("You have chosen to use the default board.");
//            read defaultBoard.txt from resources folder
    public void readFile(String fileName) {
        this.boardLoaded = new File("resources/" + fileName);
    }

    private String tileDisplay(String line, int index){
        return String.valueOf(line.charAt(index)) + line.charAt(index + 1) + line.charAt(index + 2);
    }

    private int tileScore(String line, int index) {
        String subStr = Character.isDigit(line.charAt(index+2)) ?
                line.substring(index+1,index+3) :
                line.substring(index+1,index+2);

        return Integer.parseInt(subStr);
    }

    public void fileProcessor(GameBoard gameBoard) {
        try (BufferedReader in = new BufferedReader(new java.io.FileReader(boardLoaded))) {
            // refer to defaultBoard.txt. The first line tells the board size, i.e. S=16
            int boardSize = Integer.parseInt(in.readLine());
            // call the setter method in class GameBoard to set the board size, and initialise the 2d array that is SxS size
            if (boardSize > 11 && boardSize < 26) {
                gameBoard.setUpTiles(boardSize);
            }

            // now process the Tiles
            String line;
            int k = 0;
            List<List<Tile>> tileSpace = gameBoard.getTileSpace();
            System.out.println("the multidimensional array from gameBoard has a length of: " + tileSpace.size());
            while ((line = in.readLine()) != null) {
//                System.out.println("round " + i);
                System.out.println(line);
                System.out.println(k);
                tileSpace.add(new ArrayList<>());
                for (int i = 0; i < line.length(); i++) {
                    switch (line.charAt(i)) {
                        case '.':
                            tileSpace.get(k).add(new Tile(TileType.NORMAL, " . ", 1));
                            break;
                        case '{':
                            System.out.println(tileScore(line,i));
                            tileSpace.get(k).add(new Tile(TileType.PREMIUM_LETTER, tileDisplay(line,i), tileScore(line,i)));
                            break;
                        case '(':
                            tileSpace.get(k).add(new Tile(TileType.PREMIUM_WORD,  tileDisplay(line,i), tileScore(line,i)));
                            break;
                        default:
                            break;
                    }
                }
                k++;
            }

            tileSpace
                    .forEach(System.out::println);



        } catch (FileNotFoundException ex) {
            System.out.println("File " + gameBoard + " not found!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}



