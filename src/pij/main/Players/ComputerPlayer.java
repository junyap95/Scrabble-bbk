package pij.main.Players;

import pij.main.FileReader.FileProcessor;
import pij.main.GameBoard.GameBoard;
import pij.main.GameRunner.GameTextPrinter;
import pij.main.GameRunner.Move;
import pij.main.GameRunner.MoveValidator;
import pij.main.Square.Square;
import pij.main.TileBag.Tile;
import pij.main.TileBag.TileBag;
import pij.main.TileBag.TileRack;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

public class ComputerPlayer extends Player {
    private GameBoard gameBoard;

    public ComputerPlayer(TileBag tileBag, GameBoard gameBoard) {
        super(tileBag);
        this.gameBoard = gameBoard;
    }

    @Override
    public String move() {
        return this.computerWordSearch();
    }

    private String computerWordSearch() {

        String direction = "RIGHTWARD";
        TileRack tileRack = this.getTileRack();
        List<Tile> playersTiles = tileRack.getPlayersTiles();
        String resultString = ",";

        List<Square> playableSquares = new ArrayList<>();
        boolean wordIsFound = false;
        // another overall while loop that
        // find start square first, must be empty
        Square startSquare = null;

        // i is misleading, as y axis starts from 1
        for (int i = 0; i < gameBoard.getGameBoardSize(); i++) {
            System.out.println("i " + i);

            for (int j = 0; j < gameBoard.getGameBoardSize(); j++) {
                startSquare = gameBoard.getSquareByIndex(i, j);



                System.out.println("j: " + j);
                System.out.println("start square " + startSquare);



                // if occupied, wont come into this, skips through and j++
                // Get directions to test
                if (!startSquare.isSquareOccupied()) { // unoccupied, continue
                    System.out.println("this square is unoccupied");

                    List<String> directionToTest = new ArrayList<>(Arrays.asList("DOWNWARD", "RIGHTWARD"));
                    // START unoccupied - check which direction to go? default is right
                    if (startSquare.hasTopOccupiedNeighbour() || startSquare.hasBtmOccupiedNeighbour()) {
                        System.out.println("right move cannot have top/btm neighbour, changing direction...");
//                            direction = "DOWNWARD";
                        directionToTest.remove("RIGHTWARD");
                    } else if (startSquare.hasLeftOccupiedNeighbour() || startSquare.hasRightOccupiedNeighbour()) {
                        directionToTest.remove("DOWNWARD");
                    }

                    for (String dir : directionToTest) {
                        playableSquares = new ArrayList<>();
                        boolean isThisSquarePlayable = false;
                        boolean hasOverlap = false;
                        boolean hasInvalidNeighbour = false;
                        System.out.println("testing direction" + dir);

                        // Get all letters to the left/top
                        Square leftOrTopNeighbour = dir.equals("RIGHTWARD") ? startSquare.getLeftNeighbour() : startSquare.getTopNeighbour();
                        while (leftOrTopNeighbour != null && leftOrTopNeighbour.isSquareOccupied()) {
                            playableSquares.add(leftOrTopNeighbour);
                            leftOrTopNeighbour = dir.equals("RIGHTWARD") ? leftOrTopNeighbour.getLeftNeighbour() : leftOrTopNeighbour.getTopNeighbour();
                        }
                        // Find all playable squares in n playersTile steps
                        Square startSquarePtr = startSquare;
                        for (int k = 0; k < playersTiles.size(); k++) {
                            if(startSquarePtr == null) break;

                            if (!startSquarePtr.isSquareOccupied()) {
                                boolean hasNeighbour = dir.equals("RIGHTWARD") ? startSquarePtr.hasTopOccupiedNeighbour() || startSquarePtr.hasBtmOccupiedNeighbour() : startSquarePtr.hasLeftOccupiedNeighbour() || startSquarePtr.hasRightOccupiedNeighbour();
                                if (hasNeighbour) {
                                    hasInvalidNeighbour = true;
                                    // If square is occupied, it's ok to have neighbour
                                    // Else
                                    // Before overlap found, startSquare not playable -> try next direction / if both dir tried, next start square j
                                    // After overlap found, then don't take this square and is playable
                                    System.out.println("while scanning 7 tiles right, found T/B neighbours, terminating k loop");
//                                    if (hasOverlap) {
//                                        isThisSquarePlayable = true;
//                                    }
                                    break;
                                } else {
                                    playableSquares.add(startSquarePtr);
                                }
                            } else { // occupied also add to playable
//                                hasOverlap = true;
                                isThisSquarePlayable = true;
                                playableSquares.add(startSquarePtr);
                                k--;
                            }

                            startSquarePtr = dir.equals("RIGHTWARD") ? startSquarePtr.getRightNeighbour() : startSquarePtr.getBottomNeighbour();
                        } // end of k loop
                        if (!hasInvalidNeighbour) {
                            Square rightOrBottomNeighbour = startSquarePtr;
                            while (rightOrBottomNeighbour != null && rightOrBottomNeighbour.isSquareOccupied()) {
                                isThisSquarePlayable = true;
                                playableSquares.add(rightOrBottomNeighbour);
                                rightOrBottomNeighbour = dir.equals("RIGHTWARD") ? rightOrBottomNeighbour.getRightNeighbour() : rightOrBottomNeighbour.getBottomNeighbour();
                            }
                        }

                        System.out.println("playableSquares b4 " + playableSquares);
                        // end of 7 tiles possible placement, check right again
                        //                        while(startSquare.hasRightOccupiedNeighbour()) {
                        //                            playableSquares.add(startSquare.getRightNeighbour());
                        //                        }

                        // if this square is checked 7 tiles right or down, i would have already got a list of playable squares
                        if (isThisSquarePlayable) {
                            // Get all letters to the right/bottom

                            System.out.println("Square is playable" + playableSquares);
                            String availableLetters = getAvailableLetters(playableSquares, playersTiles);
                            List<String> possibleWords = FileProcessor.possibleWordsGenerator(availableLetters);
                            System.out.println(possibleWords);
                            wordIsFound = true;

                            String occupiedSquaresLetters = extractOccupiedSquaresLetters(playableSquares);

                            // for each possible word
                            for (String word: possibleWords) {
                                // form a move
                                // i, j -> coord
                                List<String> wordAsArray = new ArrayList<>(Arrays.asList(word.toUpperCase().split("")));

                                for(char letterOnBoard: occupiedSquaresLetters.toCharArray()) {
                                    wordAsArray.remove(String.valueOf(letterOnBoard));
                                }

                                String wordToPlay = String.join("", wordAsArray).toUpperCase();
                                System.out.println("Cleaned up string" + wordAsArray);

                                String move = wordToPlay + "," + gameBoard.getSquareCoordinates(i, j, dir);
                                System.out.println("generated move " + move);
                                MoveValidator moveValidator = new MoveValidator(move, gameBoard); // new unverified move created here
                                // TODO: Assume not first round, handle first round later
                                // TODO: wild card
                                boolean isMoveVerified = moveValidator.isMovePermitted(tileRack, false);
                                if (isMoveVerified) {
                                    return move;
                                }
                            }
                            // put into move validator
                            // if valid, return string + start square

                            // use startSquare's coords i, j


                        }
                    }


                }



                // if not playable will not go into the if condition and j++ continues
                // j continues if wordIsFound is still false;


            } // end of j loop

        } // end of i loop

        return resultString;
    } // end of method

    private static String extractOccupiedSquaresLetters(List<Square> playableSquares) {
        StringBuilder extractedLetters = new StringBuilder();
        for (Square s : playableSquares) { // extract letters out of existing occupied squares
            if(s.isSquareOccupied()) {
                extractedLetters.append(s.getTileOnSquare().getDisplayAsLetter());
            }
        }
        return extractedLetters.toString();
    }

    private static String getAvailableLetters(List<Square> playableSquares, List<Tile> playersTiles) {
        StringBuilder availableLetters = new StringBuilder(); // all letters from player's tiles and occupied square

        availableLetters.append(extractOccupiedSquaresLetters(playableSquares));

        for (Tile t : playersTiles) { // extract letters out of player's tiles
            availableLetters.append(t.getDisplayAsLetter());
        }
        return availableLetters.toString();
    }

    @Override
    public String toString() {
        return "I am a Computer Player, my current score is: " + this.getPlayerScore();
    }
}
