package pij.main.Players;

import pij.main.FileReader.FileProcessor;
import pij.main.GameBoard.GameBoard;
import pij.main.Move.Direction;
import pij.main.Move.Move;
import pij.main.Move.MoveValidator;
import pij.main.Square.Square;
import pij.main.TileBag.Tile;
import pij.main.TileBag.TileBag;
import pij.main.TileBag.TileRack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pij.main.Move.Move.PASS;


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

        TileRack tileRack = this.getTileRack();
        List<Tile> playersTiles = tileRack.getPlayersTiles();

        List<Square> playableSquares;
        Square startSquare;

        for (int i = 0; i < gameBoard.getGameBoardSize(); i++) {
            System.out.println("i " + i);
            for (int j = 0; j < gameBoard.getGameBoardSize(); j++) {
                startSquare = gameBoard.getSquareByIndex(i, j);

                System.out.println("j: " + j);

                // find start square first, must be unoccupied
                // if occupied, skips through this if-condition and j++
                // Get directions to test
                if (!startSquare.isSquareOccupied()) {

                    List<Direction> directionToTest = new ArrayList<>(Arrays.asList(Direction.DOWNWARD, Direction.RIGHTWARD));
                    // START unoccupied - check which direction to go?
                    // go for the start square that does not have invalid neighbours
                    if (startSquare.hasTopOccupiedNeighbour() || startSquare.hasBtmOccupiedNeighbour()) {
                        directionToTest.remove(Direction.RIGHTWARD);
                    } else if (startSquare.hasLeftOccupiedNeighbour() || startSquare.hasRightOccupiedNeighbour()) {
                        directionToTest.remove(Direction.DOWNWARD);
                    }

                    // for both directions, run the playability check
                    for (Direction dir : directionToTest) {
                        playableSquares = new ArrayList<>(); // each direction will have its list of playable squares
                        boolean isThisSquarePlayable = false;
                        boolean hasOverlap = false;
                        boolean hasInvalidNeighbour = false;
                        System.out.println("testing direction" + dir);

                        // Get/capture all letters to the left/top of the start square, if any
                        Square leftOrTopNeighbour = dir.equals(Direction.RIGHTWARD) ? startSquare.getLeftNeighbour() : startSquare.getTopNeighbour();
                        while (leftOrTopNeighbour != null && leftOrTopNeighbour.isSquareOccupied()) {
                            playableSquares.add(leftOrTopNeighbour); // captured
                            leftOrTopNeighbour = dir.equals(Direction.RIGHTWARD) ? leftOrTopNeighbour.getLeftNeighbour() : leftOrTopNeighbour.getTopNeighbour();
                        }

                        // Find all playable squares for n playersTiles
                        Square startSquarePtr = startSquare;
                        for (int k = 0; k < playersTiles.size(); k++) {
                            if (startSquarePtr == null) break;

                            if (!startSquarePtr.isSquareOccupied()) {
                                boolean hasNeighbour = dir.equals(Direction.RIGHTWARD) ? startSquarePtr.hasTopOccupiedNeighbour() || startSquarePtr.hasBtmOccupiedNeighbour() : startSquarePtr.hasLeftOccupiedNeighbour() || startSquarePtr.hasRightOccupiedNeighbour();
                                if (hasNeighbour) {
                                    hasInvalidNeighbour = true;
                                    // If square is unoccupied, based on its direction, break the for-k loop if it has invalid neighbours
                                    break;
                                } else {
                                    // if no neighbours
                                    playableSquares.add(startSquarePtr);
                                }
                            } else { // occupied square also to be added to playable squares
                                isThisSquarePlayable = true;
                                playableSquares.add(startSquarePtr);
                                k--;
                            }

                            startSquarePtr = dir.equals(Direction.RIGHTWARD) ? startSquarePtr.getRightNeighbour() : startSquarePtr.getBottomNeighbour();
                        } // end of k loop

                        // end of 7 tiles possible placement, check right/bottom square again, get/capture any subsequent occupied squares
                        if (!hasInvalidNeighbour) {
                            Square rightOrBottomNeighbour = startSquarePtr;
                            while (rightOrBottomNeighbour != null && rightOrBottomNeighbour.isSquareOccupied()) {
                                isThisSquarePlayable = true;
                                playableSquares.add(rightOrBottomNeighbour);
                                rightOrBottomNeighbour = dir.equals(Direction.RIGHTWARD) ? rightOrBottomNeighbour.getRightNeighbour() : rightOrBottomNeighbour.getBottomNeighbour();
                            }
                        }

                        if (isThisSquarePlayable) {
                            System.out.println("Square is playable" + playableSquares);
                            String availableLetters = getAvailableLetters(playableSquares, playersTiles);
                            List<String> possibleWords = FileProcessor.possibleWordsGenerator(availableLetters);
                            System.out.println(possibleWords);

                            String occupiedSquaresLetters = extractOccupiedSquaresLetters(playableSquares);

                            // for each possible word
                            for (String word : possibleWords) {
                                System.out.println("current word " + word);
                                // form a move
                                // we are still in the for loops, i, j -> x and y for a square
                                List<String> wordAsArray = new ArrayList<>(Arrays.asList(word.toUpperCase().split("")));

                                for (char letterOnBoard : occupiedSquaresLetters.toCharArray()) {
                                    wordAsArray.remove(String.valueOf(letterOnBoard));
                                }

                                String wordToPlay = String.join("", wordAsArray).toUpperCase();
                                System.out.println("Cleaned up string" + wordAsArray);

                                String move = wordToPlay + "," + Move.getSquareMoveByIndex(i, j, dir);
                                System.out.println("generated move " + move);
                                MoveValidator moveValidator = new MoveValidator(move, gameBoard); // new unverified move created here
                                // TODO: Assume not first round, handle first round later
                                // TODO: wild card
                                boolean isMoveVerified = moveValidator.isMovePermitted(tileRack, false);
                                if (isMoveVerified) {
                                    return move;
                                }
                            }
                        }
                    } // end of directions enhanced for-loop


                }


                // if not playable will not go into the if condition and j++ continues
                // j continues if wordIsFound is still false;


            } // end of j loop

        } // end of i loop

        return PASS;
    } // end of method

    private static String extractOccupiedSquaresLetters(List<Square> playableSquares) {
        StringBuilder extractedLetters = new StringBuilder();
        for (Square s : playableSquares) { // extract letters out of existing occupied squares
            if (s.isSquareOccupied()) {
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
        return "Computer Player";
    }
}
