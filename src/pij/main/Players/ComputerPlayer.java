package pij.main.Players;

import pij.main.FileReader.FileProcessor;
import pij.main.GameBoard.GameBoard;
import pij.main.Move.Direction;
import pij.main.Move.MoveValidator;
import pij.main.Square.Square;
import pij.main.Tile.Tile;
import pij.main.Tile.TileBag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pij.main.Move.Direction.RIGHTWARD;
import static pij.main.Move.Move.PASS;
import static pij.main.Move.Move.getSquareMoveByIndex;

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
        // if human player passed the first round - gameBoard will be empty on round 2
        if (gameBoard.isGameBoardEmpty()) {
            return findWordForEmptyBoard(this.getTileRack().getPlayersTiles());
        }

        List<Square> playableSquares;
        Square startSquare;

        for (int i = 0; i < gameBoard.getGameBoardSize(); i++) {
            for (int j = 0; j < gameBoard.getGameBoardSize(); j++) {
                startSquare = gameBoard.getSquareByIndex(i, j);

                // find start square first, must be unoccupied
                // if occupied, skips through this if-condition and j++
                // Get directions to test
                if (!startSquare.isSquareOccupied()) {

                    List<Direction> directionToTest = getDirectionToTest(startSquare);

                    // for both directions, run the playability check
                    for (Direction dir : directionToTest) {
                        playableSquares = new ArrayList<>(); // each direction will have its list of playable squares
                        boolean isThisSquarePlayable = false;
                        boolean hasInvalidNeighbour = false;

                        // Get/capture all letters to the left/top of the start square, if any
                        Square leftOrTopNeighbour = dir.equals(RIGHTWARD) ? startSquare.getLeftNeighbour() : startSquare.getTopNeighbour();
                        while (leftOrTopNeighbour != null && leftOrTopNeighbour.isSquareOccupied()) {
                            playableSquares.add(leftOrTopNeighbour); // captured an occupied square adjacent to our start square
                            leftOrTopNeighbour = dir.equals(RIGHTWARD) ? leftOrTopNeighbour.getLeftNeighbour() : leftOrTopNeighbour.getTopNeighbour(); // keep going till unoccupied square or null
                        }

                        // Find all playable squares for n playersTiles
                        Square startSquarePtr = startSquare;
                        for (int k = 0; k < this.getTileRack().getPlayersTiles().size(); k++) {
                            if (startSquarePtr == null) break;

                            if (!startSquarePtr.isSquareOccupied()) { // for unoccupied square in this particular direction

                                boolean isIllegalDirection = dir.equals(RIGHTWARD) ? startSquarePtr.hasTopOccupiedNeighbour() || startSquarePtr.hasBtmOccupiedNeighbour() : startSquarePtr.hasLeftOccupiedNeighbour() || startSquarePtr.hasRightOccupiedNeighbour();
                                if (isIllegalDirection) {
                                    hasInvalidNeighbour = true;
                                    // If square is unoccupied, based on its direction, break the for-k loop if the iteration comes to an invalid neighbour
                                    break;
                                } else {
                                    // if no illegal neighbouring squares for that particular direction
                                    playableSquares.add(startSquarePtr);
                                }

                            } else { // occupied square also to be added to playable squares
                                isThisSquarePlayable = true; // this boolean is only set here because every move the computer plays must have an overlap i.e. 'touches' an occupied square
                                playableSquares.add(startSquarePtr);
                                k--;
                            }

                            startSquarePtr = dir.equals(RIGHTWARD) ? startSquarePtr.getRightNeighbour() : startSquarePtr.getBottomNeighbour();
                        } // end of k loop

                        // end of 7 tiles possible placement, check right/bottom square again, get/capture any subsequent occupied squares
                        // for the sake of simplicity, only capture one square
                        if (!hasInvalidNeighbour) {
                            if (startSquarePtr != null && startSquarePtr.isSquareOccupied() && (dir.equals(RIGHTWARD) ? !startSquarePtr.hasRightOccupiedNeighbour() : !startSquarePtr.hasBtmOccupiedNeighbour())) {
                                isThisSquarePlayable = true; // in case the current move did not have any occupied square before, but encountered one at the end of the last possible placement of tile
                                playableSquares.add(startSquarePtr);
                            }
                        }
                        if (isThisSquarePlayable) {
                            String availableLetters = getAvailableLetters(playableSquares, this.getTileRack().getPlayersTiles()); // all occupied squares and players tiles letters, might contain "_"

                            List<String> possibleWords = FileProcessor.possibleWordsGenerator(availableLetters).reversed();

                            String occupiedSquaresLetters = extractOccupiedSquaresLetters(playableSquares);

                            // for each possible word
                            for (String word : possibleWords) {

                                // form a move, WILL not always be playable, how to know?
                                // we are still in the for loops, i, j -> x and y for start square

                                String move;
                                List<String> wordAsArray = new ArrayList<>(Arrays.asList(word.toUpperCase().split(""))); // [ D, O, G ]

                                for (char letterOnBoard : occupiedSquaresLetters.toCharArray()) {
                                    wordAsArray.remove(String.valueOf(letterOnBoard)); // clean up
                                }


                                String wordToPlay = String.join("", wordAsArray);

                                if(availableLetters.contains("_") && !wordToPlay.isEmpty()) {
                                    String replacement = wordToPlay.substring(0,1).toLowerCase();
                                    wordToPlay = wordToPlay.replaceFirst(wordToPlay.substring(0,1), replacement);
                                }
                                move = wordToPlay + "," + getSquareMoveByIndex(i, j, dir);

                                MoveValidator moveValidator = new MoveValidator(move, gameBoard); // new unverified move created here
                                boolean isMoveVerified = moveValidator.isMovePermitted(this.getTileRack(), false);

                                System.out.println("Move " + move);
                                if (isMoveVerified) return move;

                            }
                        }
                    } // end of directions enhanced for-loop
                }
            } // end of j loop
        } // end of i loop
        return PASS;
    } // end of method

    private static List<Direction> getDirectionToTest(Square startSquare) {
        List<Direction> directionToTest = new ArrayList<>(Arrays.asList(Direction.DOWNWARD, RIGHTWARD));
        // START unoccupied - check which direction to go?
        // go for the start square that does not have invalid neighbours
        // if the start square has no neighbours, both directions are tested
        if (startSquare.hasTopOccupiedNeighbour() || startSquare.hasBtmOccupiedNeighbour()) {
            directionToTest.remove(RIGHTWARD);
        } else if (startSquare.hasLeftOccupiedNeighbour() || startSquare.hasRightOccupiedNeighbour()) {
            directionToTest.remove(Direction.DOWNWARD);
        }
        return directionToTest;
    }

    private String findWordForEmptyBoard(List<Tile> playersTiles) {
        String squareMove = gameBoard.getCentreSquare().getSquareCoordinates();
        int wildCardCount = 0;
        StringBuilder availableLetters = new StringBuilder(); // all letters from player's tiles and occupied square
        StringBuilder wordToPlay = new StringBuilder();

        for (Tile t : playersTiles) { // extract letters out of player's tiles
            if (t.getDisplayAsLetter().equals("_")) wildCardCount++;
            availableLetters.append(t.getDisplayAsLetter());
        }
        String possibleWords = FileProcessor.possibleWordsGenerator(availableLetters.toString()).getLast().toUpperCase();

        for (char ch : possibleWords.toCharArray()) {
            // if that word has character, but player does not have it available
            // but if there is a wildcard
            if (availableLetters.toString().indexOf(ch) == -1 && wildCardCount > 0) {
                wordToPlay.append(Character.toLowerCase(ch));
            } else {
                wordToPlay.append(ch);
            }
        }
        return wordToPlay + "," + squareMove;
    }

    private static String extractOccupiedSquaresLetters(List<Square> playableSquares) {
        StringBuilder extractedLetters = new StringBuilder();
        for (Square s : playableSquares) { // extract letters out of existing occupied squares
            if (s.isSquareOccupied()) {
                extractedLetters.append(s.getTileOnSquare().getDisplayAsLetter().toUpperCase());
                // for wildcard case, "a" or "o" or any small letter is extracted
            }
        }
        return extractedLetters.toString().toUpperCase(); // e.g. "ABED"
    }

    private static String getAvailableLetters(List<Square> playableSquares, List<Tile> playersTiles) {
        StringBuilder availableLetters = new StringBuilder(); // all letters from player's tiles and occupied square

        availableLetters.append(extractOccupiedSquaresLetters(playableSquares));

        for (Tile t : playersTiles) { // extract letters out of player's tiles
            availableLetters.append(t.getDisplayAsLetter());
            // for wildcard case, "_" is extracted
        }
        return availableLetters.toString();
    }

    @Override
    public String toString() {
        return "Computer Player";
    }
}
