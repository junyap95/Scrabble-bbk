package pij.main.Move;

import pij.main.FileReader.FileProcessor;
import pij.main.GameBoard.GameBoard;
import pij.main.Square.Square;
import pij.main.Tile.TileRack;
import java.util.ArrayList;
import java.util.List;


public class MoveValidator {
    private final int LONGEST_PERMITTED_WORD_STRING = 7;
    private final int LONGEST_PERMITTED_SQUARE_STRING = 3;
    Move currentMove;
    String moveString;
    GameBoard gameBoard;

    // CONSTRUCTOR
    public MoveValidator(String moveString, GameBoard gameBoard) {
        this.moveString = moveString;
        this.gameBoard = gameBoard;
        this.currentMove = this.createMove();
    }

    // constructor helper method - for new Move creation
    public Move createMove() {
        String move = this.moveString;

        // the bare minimum requirement for a string must include one comma
        if (!move.contains(",") || move.contains(" ")) {
            return null;
        }

        // if not 2-part splitting, there are >1 comma included
        if (move.split(",").length != 2 && !move.equals(",")) {
            return null;
        }

        String wordMove = moveString.split(",")[0];
        String squareMove = moveString.split(",")[1];

        char letter = '_'; // '_' is a dummy to be replaced
        StringBuilder digitBuilder = new StringBuilder();
        boolean hasDigit = false;
        boolean hasLetter = false;
        boolean hasInvalidChar = false;

        for (char ch : squareMove.toCharArray()) {
            if (Character.isLetter(ch)) {
                if (hasLetter) {
                    hasInvalidChar = true;
                    break;
                }
                hasLetter = true;
                letter = ch; // e.g. f
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
                digitBuilder.append(ch);
            } else {
                hasInvalidChar = true;
                break;
            }
        }

        if (hasInvalidChar || !hasLetter || !hasDigit) {
            return null;
        }
        int digit = Integer.parseInt(digitBuilder.toString());
        int xIndex = digit - 1;
        int yIndex = letter -97;

        if (xIndex < 0 || xIndex >= this.gameBoard.getGameBoardSize() || yIndex < 0 || yIndex >= this.gameBoard.getGameBoardSize()) {
            return null;
        }
        if(gameBoard.getSquareByIndex(xIndex, yIndex).isSquareOccupied()) {
            return null;
        } // if start square is already occupied

        return new Move(wordMove, squareMove, xIndex, yIndex, this.gameBoard);
    }

    public Move getCurrentMove() {
        return currentMove;
    }

    // check 1 - format
    public boolean isMoveFormatLegal() {
        if (this.currentMove == null) {
            return false;
        }

        String wordMove = this.currentMove.getWordMove(); // i.e. "HI"
        String squareMove = this.currentMove.getSquareMove(); // i.e. "f4"

        // check both split moves lengths, empty string is not allowed
        // longest permitted word move is always 7 letters, shortest 1 letter
        // e.g. "BOOLEAN" or "A"
        // longest permitted square move is always 3, shortest 2
        // e.g. "16a" or "e1"
        if (wordMove.length() > LONGEST_PERMITTED_WORD_STRING || wordMove.isEmpty() || squareMove.length() > LONGEST_PERMITTED_SQUARE_STRING || squareMove.length() < 2) {
            return false;
        }

        // square move must have small letters only - '8H' not allowe
        for (char ch : squareMove.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                return false;
            }
        }

        // guard case for square move of length 3 - will never allow letter in middle: 1a6, 1b4...
        // square move cannot be - aa1, 1bc...must contain exactly one letter: 16a, 1a, a13, a3...
        if (squareMove.length() == LONGEST_PERMITTED_SQUARE_STRING) {
            if (Character.isLetter(squareMove.charAt(1))) {
                return false;
            }

            int letterCount = 0;
            for (char ch : squareMove.toCharArray()) {
                if (Character.isLetter(ch)) letterCount++;
            }

            if (letterCount != 1) {
                return false;
            }
        }
        return true;
    }

    // check 2 - player's tile rack availability
    public boolean rackContainsMove(TileRack tileRack) {
        if (this.currentMove == null) {
            return false;
        }
        // obtain a list of only the letters of a tile (excluding score)
        List<String> listOfLettersFromRack = new ArrayList<>(tileRack.getPlayersTilesAsLetters());

        String[] wordMove = this.currentMove.getWordMove().split(""); // [H, I]

        for (String s : wordMove) {
            // lowercase letter is to check for wildcard in rack
            boolean isLowerCase = Character.isLowerCase(s.charAt(0));

            // if one of the letters is lowercase, check if wildcard("_") is available
            if (isLowerCase && !listOfLettersFromRack.contains("_")) {
                return false;
            }

            // normal capital letter case
            if (!isLowerCase && !listOfLettersFromRack.contains(s)) {
                return false;
            }

            String toBeRemoved = isLowerCase ? "_" : s;
            // if the letter is allowed, remove the letter from the shallow listOfLettersFromRack
            // so duplicated words played will be checked against rack availability, in this loop
            // e.g "HH,a1" is not allowed for rack [H4, S1, T1, I1, E1, T1, Y5] which only has one 'H'
            listOfLettersFromRack.remove(toBeRemoved);
        }

        return true;
    }

    public boolean isMovePermitted(TileRack playerRack, boolean isFirstRound) {
        return isMoveFormatLegal() && rackContainsMove(playerRack) && isMovePlayableOnBoard(isFirstRound);
    }

    public boolean isMovePlayableOnBoard(boolean isFirstRound) {
        if (this.currentMove == null || this.currentMove.getWordFormed() == null) {
            return false;
        }
        String wordFormedFromMove = this.currentMove.getWordFormed();
        List<Square> listOfPlayableSquares = this.currentMove.getListOfAllPlayableSquares();
        Direction direction = this.currentMove.getMoveDirection();
        Square centreSquare = this.gameBoard.getCentreSquare(); // needed for first round

        // in first round and if the board is still empty due to player passing round
        if (isFirstRound || this.gameBoard.isGameBoardEmpty())
            return this.moveContainsCentreSquare(listOfPlayableSquares, centreSquare, isFirstRound);

        boolean hasOverLap = false;
        for (Square sq : listOfPlayableSquares) {
            if (sq.isSquareOccupied()) {
                hasOverLap = true;
            }

            if(direction.equals(Direction.RIGHTWARD)) {
                if (!sq.isSquareOccupied() && (sq.hasTopOccupiedNeighbour() || sq.hasBtmOccupiedNeighbour())) {
                    return false;
                }
            }

            if(direction.equals(Direction.DOWNWARD)) {
                if (!sq.isSquareOccupied() && (sq.hasLeftOccupiedNeighbour() || sq.hasRightOccupiedNeighbour())) {
                    return false;
                }
            }

        }

        return hasOverLap && FileProcessor.wordListProcessor(wordFormedFromMove);
    }

    // helper method (for first move of the game) - check if moves contain centre square, and forms a legit word
    private boolean moveContainsCentreSquare(List<Square> moveList, Square centreSquare, boolean isFirstRound) {
        for (Square square : moveList) {
            if (square.equals(centreSquare)) {
                // after checking centre square just check if word is in word list
                return FileProcessor.wordListProcessor(currentMove.getWordMove());
            }
        }
        System.out.println(isFirstRound ? "Illegal move. In the first round, one of your tiles must be placed on the centre square in the first round." : "As the board is currently empty, one of your tiles must be placed on the centre square.");
        return false; // if the loop finished - the moveList does not contain centre square - invalid move
    }

}
