package FileReader;

import GameBoard.GameBoard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Square.*;

public class FileProcessor {
    private File boardLoaded;

    public void bannerPrinter() {
        System.out.println("============" + "                     " + "============");
        System.out.println("============" + "  S k r a B B k l e  " + "============");
        System.out.println("============" + "                     " + "============");
    }

    /**
     * Prompts the user to choose between loading a custom board or using the default board.
     * It repeats the prompt until a valid choice ('l' or 'd') is entered.
     */

    private String tileDisplay(String line, int index){
        return String.valueOf(line.charAt(index)) + line.charAt(index + 1) + line.charAt(index + 2);
    }

    private int tileScore(String line, int index) {
        String subStr = Character.isDigit(line.charAt(index+2)) ?
                line.substring(index+1,index+3) :
                line.substring(index+1,index+2);

        return Integer.parseInt(subStr);
    }

    public void fileProcessor(GameBoard gameBoard, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("resources/" + fileName))) {
            int boardSize = Integer.parseInt(reader.readLine());
            if(boardSize <= 11 || boardSize >= 26) {
                System.out.println("Invalid Board");
                throw new RuntimeException("Invalid Board");
            }
            gameBoard.setGameBoardSize(boardSize);
            System.out.println("Board of size " + boardSize + "*" + boardSize + " Loaded");


            String line;
            int k = 0;
            List<List<Square>> tileSpace = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                tileSpace.add(new ArrayList<>());
                for (int i = 0; i < line.length(); i++) {
                    switch (line.charAt(i)) {
                        case '.':
                            tileSpace.get(k).add(new Square(SquareType.NORMAL, " . ", 1));
                            break;
                        case '{':
                            tileSpace.get(k).add(new Square(SquareType.PREMIUM_LETTER, tileDisplay(line,i), tileScore(line,i)));
                            break;
                        case '(':
                            tileSpace.get(k).add(new Square(SquareType.PREMIUM_WORD,  tileDisplay(line,i), tileScore(line,i)));
                            break;
                        default:
                            break;
                    }
                }
                k++;
            }
            gameBoard.setTileSpace(tileSpace);




        } catch (FileNotFoundException ex) {
            System.out.println("File " + gameBoard + " not found!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}



