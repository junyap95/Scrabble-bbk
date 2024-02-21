package pij.main.FileReader;

import pij.main.GameBoard.GameBoard;

import java.io.*;
import java.util.*;

import pij.main.Square.*;

public class FileProcessor {
    private static Set<String> wordSet = new HashSet<>();
    private static List<String> wordAlreadyPlayed = new ArrayList<>();

    // helper function to create display string for a square
    private static String squareToDisplay(String line, int index) {
        return String.valueOf(line.charAt(index)) + line.charAt(index + 1) + line.charAt(index + 2);
    }

    // helper function to obtain score for a square
    private static int squareScore(String line, int index) {
        String subStr = Character.isDigit(line.charAt(index + 2)) ? line.substring(index + 1, index + 3) : line.substring(index + 1, index + 2);

        return Integer.parseInt(subStr);
    }

    public static void addToWordAlreadyPlayed(String word) {
        wordAlreadyPlayed.add(word);
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
        if (wordAlreadyPlayed.contains(wordPlayed)) {
            System.out.println(wordPlayed + " is already played once on board!");
            return false;
        }
        return wordSet.contains(wordPlayed.toLowerCase());
    }


    public static List<String> possibleWordsGenerator(String string) {
        // generate map from player tiles and occupied squares' letters
        Map<Character, Integer> playersLetters = lettersToMap(string.toLowerCase());

        // if any, find the number of wildcard
        int wildCardCount = 0;
        if(playersLetters.containsKey('_')) wildCardCount = playersLetters.get('_');


        List<String> possibleWords = new ArrayList<>();
        for (String s : wordSet) {
            Map<Character, Integer> wordFromSet = lettersToMap(s);
            boolean canGenerateWord = true;
            int wildCardNeeded = 0;

            for (Character character : wordFromSet.keySet()) {
                int currentWordCharCount = wordFromSet.get(character); // A
                int lettersCharCount = playersLetters.containsKey(character) ? playersLetters.get(character) : 0;

                if(wildCardCount == 0) { // run normal check
                    if (currentWordCharCount > lettersCharCount) {
                        canGenerateWord = false;
                        break;
                    }
                }

                if(wildCardCount > 0) { // if there is wildcard
                    // base case, there is never more than 2 wildcards in the game
                    if(currentWordCharCount - lettersCharCount > 2) {
                        canGenerateWord = false;
                        break;
                    }

                    if(currentWordCharCount - lettersCharCount > 0) {
                        wildCardNeeded += currentWordCharCount - lettersCharCount;
                    }

                }
            }

            if (canGenerateWord && wildCardNeeded <= wildCardCount) {
                possibleWords.add(s.toUpperCase());
            }
        }
        return possibleWords;
    }

    // helper method - takes a string and convert its letters(key) into a map and its amount (value)
    private static Map<Character, Integer> lettersToMap(String string) {
        Map<Character, Integer> lettersCountMap = new HashMap<>();
        for (int i = 0; i < string.length(); i++) {
            char currentChar = string.charAt(i);
            int count = lettersCountMap.getOrDefault(currentChar, 0);
            lettersCountMap.put(currentChar, count + 1);
        }
        return lettersCountMap;
    }

    private static boolean boardSizeValidator(int boardSize) {
            if (boardSize <= 12 || boardSize >= 26) {
                System.out.println("Invalid Board");
                return false;
            }
        return true;
    }

    /**
     *
     * @param gameBoard takes the empty gameBoard and sets its size
     *                  based on the .txt file
     * @param fileName takes a string for the .txt filename
     *                 e.g. the default board is defaultBoard.txt
     */
    public static void boardProcessor(GameBoard gameBoard, String fileName) {
        // reads the file
        try (BufferedReader reader = new BufferedReader(new FileReader("resources/" + fileName))) {
            int boardSize = Integer.parseInt(reader.readLine());
            // verifies if txt file matches the allowed board size
            if(boardSizeValidator(boardSize)) {
                gameBoard.setGameBoardSize(boardSize);
            } else {
                System.out.println("Invalid board size, game exiting...");
                System.exit(-1);
            }
            // set the allowed board size to the game board instance

            // analyses the txt file line-by-line, then allocates appropriate squares to the game board in a 2D-array
            String line;
            int k = 0; // this is to access each arraylist(row) in the 'main' arraylist
            List<List<Square>> allSquaresOnBoard = new ArrayList<>(); // 'main' 2D arraylist
            while ((line = reader.readLine()) != null) {
                allSquaresOnBoard.add(new ArrayList<>()); // 'inner' arraylist - each line in the txt file will occupy one arraylist
                for (int i = 0; i < line.length(); i++) {
                    Square squareCreated;
                    switch (line.charAt(i)) {
                        case '.':
                            squareCreated = new Square(SquareType.NORMAL, " . ", 1);
                            break;
                        case '{':
                            squareCreated = new Square(SquareType.PREMIUM_WORD, squareToDisplay(line, i), squareScore(line, i));
                            break;
                        case '(':
                            squareCreated = new Square(SquareType.PREMIUM_LETTER, squareToDisplay(line, i), squareScore(line, i));
                            break;
                        default:
                            continue;
                    }
                    allSquaresOnBoard.get(k).add(squareCreated);
                    int y = allSquaresOnBoard.get(k).indexOf(squareCreated);
                    squareCreated.setSquareCoordinates(coordinatesGenerator(k, y));
                }
                k++;
            }
            gameBoard.setAllSquaresOnBoard(allSquaresOnBoard);
            createSquareNeighbours(allSquaresOnBoard);

        } catch (FileNotFoundException ex) {
            System.out.println("File " + gameBoard + " not found!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // helper method - generates a square's coordinates (e.g. "f4")
    private static String coordinatesGenerator(int x, int y) {
        int row = x + 1;
        char column = (char) ('a' + y);
        return Integer.toString(row) + column;
    }

    // helper method - for when the 2D array is established
    private static void createSquareNeighbours(List<List<Square>> allSquaresOnBoard) {

        // add square neighbours relationship
        for (int i = 0; i < allSquaresOnBoard.size(); i++) {
            for (int j = 0; j < allSquaresOnBoard.get(i).size(); j++) {
                Square square = allSquaresOnBoard.get(i).get(j);
                if (i > 0) { // i > 0 means it's not the first inner arraylist - square j has a top neighbour
                    square.setTopNeighbour(allSquaresOnBoard.get(i - 1).get(j));
                }
                if (i < allSquaresOnBoard.size() - 1) { // if it is not the last arraylist - square has a bottom neighbour
                    square.setBottomNeighbour(allSquaresOnBoard.get(i + 1).get(j));
                }
                if (j > 0 && j < allSquaresOnBoard.get(i).size() - 1) { // if j is not the first and last square - has left and right neighbours
                    square.setLeftNeighbour(allSquaresOnBoard.get(i).get(j - 1));
                    square.setRightNeighbour(allSquaresOnBoard.get(i).get(j + 1));
                }
                if (j == 0) {
                    square.setRightNeighbour(allSquaresOnBoard.get(i).get(j + 1));
                }
                if (j == allSquaresOnBoard.get(i).size() - 1) {
                    square.setLeftNeighbour(allSquaresOnBoard.get(i).get(j - 1));
                }
            }
        }
    }
}



