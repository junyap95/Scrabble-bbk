package pij.main.FileReader;

import pij.main.GameBoard.GameBoard;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pij.main.Square.*;

public class FileProcessor {
    private static Set<String> wordSet = new HashSet<>();

    // helper function to create display string for a square
    private static String squareToDisplay(String line, int index) {
        return String.valueOf(line.charAt(index)) + line.charAt(index + 1) + line.charAt(index + 2);
    }

    // helper function to obtain score for a square
    private static int squareScore(String line, int index) {
        String subStr = Character.isDigit(line.charAt(index + 2)) ?
                line.substring(index + 1, index + 3) :
                line.substring(index + 1, index + 2);

        return Integer.parseInt(subStr);
    }


    public static void loadWordList() {
        try (BufferedReader reader = new BufferedReader(new FileReader("resources/wordlist.txt"))) {
            String currentWord;
            while ((currentWord = reader.readLine()) != null) {
                wordSet.add(currentWord); // Store words in lowercase for case-insensitive comparison
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean wordListProcessor(String wordPlayed) {
        if(wordSet.contains(wordPlayed.toLowerCase())) {
            return true;
        }
        System.out.println(wordPlayed +  " is not in word list.");
        return false;
    }

//    public static boolean wordListProcessor(String wordPlayed) {
//        try (BufferedReader reader = new BufferedReader(new FileReader("resources/wordlist.txt"))) {
//            for(String currentWord = reader.readLine(); currentWord != null; currentWord = reader.readLine()) {
//                if(wordPlayed.equalsIgnoreCase(currentWord)) return true;
//            }
//            return false;
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void boardProcessor(GameBoard gameBoard, String fileName) {
        // reads the file
        try (BufferedReader reader = new BufferedReader(new FileReader("resources/" + fileName))) {
            int boardSize = Integer.parseInt(reader.readLine());
            // verifies if txt file matches the allowed board size
            if (boardSize <= 11 || boardSize >= 26) {
                throw new RuntimeException("Invalid Board");
            }
            // set the allowed board size to game board instance
            gameBoard.setGameBoardSize(boardSize);

            // analyses the txt file line-by-line, then allocates appropriate squares to the game board in a 2D-array
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
                            tileSpace.get(k).add(new Square(SquareType.PREMIUM_LETTER, squareToDisplay(line, i), squareScore(line, i)));
                            break;
                        case '(':
                            tileSpace.get(k).add(new Square(SquareType.PREMIUM_WORD, squareToDisplay(line, i), squareScore(line, i)));
                            break;
                        default:
                            break;
                    }
                }
                k++;
            }
            gameBoard.setAllSquaresOnBoard(tileSpace);

        } catch (FileNotFoundException ex) {
            System.out.println("File " + gameBoard + " not found!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}



