package pij.main.GameRunner;

import pij.main.GameBoard.GameBoard;
import pij.main.TileBag.Tile;
import pij.main.TileBag.TileRack;

import java.util.ArrayList;
import java.util.List;


public class MoveValidator {
    private final int LONGEST_PERMITTED_WORD_STRING = 7;
    private final int LONGEST_PERMITTED_SQUARE_STRING = 3;
    boolean isLegal;
    boolean rackHasMove;
    boolean moveIsInBound;
    Move currentMove;

    public boolean splittableMove(String move) {
        if (move.split(",").length != 2) {
            GameTextPrinter.printIllegalMoveFormat();
            return false;
        }
        return true;
    }

    public String getWordMove(String move) {
        return move.split(",")[0];
    }

    public String getSquareMove(String move) {
        return move.split(",")[1];
    }

    public String getSquareMoveDirection(String move) {
        boolean firstCharIsDigit = Character.isDigit(getSquareMove(move).charAt(0));
        if (firstCharIsDigit) return "RIGHTWARD";
        return "DOWNWARD";
    }

    public boolean legalMoveValidation(String move) {
        // the bare minimum requirement for a string must include one comma
        if (!move.contains(",")) {
            GameTextPrinter.printIllegalMoveFormat();
            return false;
        }

        // split the input into 2 parts as below
        // if it is not 2 parts, means there are >1 comma included
        if (!this.splittableMove(move)) {
            GameTextPrinter.printIllegalMoveFormat();
            return false;
        }

        String wordMove = getWordMove(move); // i.e. "HI"
        String squareMove = getSquareMove(move); // i.e. "f4"

        // check both moves lengths, empty string is not allowed
        // longest permitted word move is always 7, shortest 1
        // e.g. "BOOLEAN" or "A"
        // longest permitted square move is always 3, shortest 2
        // e.g. "16a" or "e1"
        if (wordMove.length() > LONGEST_PERMITTED_WORD_STRING || wordMove.isEmpty() || squareMove.length() > LONGEST_PERMITTED_SQUARE_STRING || squareMove.length() < 2) {
            GameTextPrinter.printIllegalMoveFormat();
            System.out.println("why here");
            return false;
        }

        if (squareMove.length() == LONGEST_PERMITTED_SQUARE_STRING) {
            // square move cannot be - 1a6, 1b4...
            if (Character.isLetter(squareMove.charAt(1))) {
                System.out.println("here?");
                GameTextPrinter.printIllegalMoveFormat();
                return false;
            }

            int letterCount = 0;
            for (char ch : squareMove.toCharArray()) {
                // square move cannot be - aa1, 1bc...
                // must be exactly one letter - 16a, 1a, a13, a3...
                if (Character.isLetter(ch)) letterCount++;
                if (letterCount != 1) {
                    GameTextPrinter.printIllegalMoveFormat();
                    return false;
                }
            }
        }

        this.isLegal = true;
        return true;
    }

    public boolean rackContainsMove(String move, TileRack rack) {
        if (!this.isLegal) return false;
        // create a list of only the letters of a tile (excluding score)
        List<String> list = rack.getPlayersRack().stream().map(Tile::getDisplayAsLetter).toList();
        // make a shallow copy of the above
        List<String> listOfLettersFromRack = new ArrayList<>(list);

        String wordMove = getWordMove(move);
        List<String> wordMoveArray = List.of(wordMove.split("")); // [H, I]

        for (String s : wordMoveArray) {
            // lowercase letter is to check against wildcard in rack
            boolean isLowerCase = Character.isLowerCase(s.charAt(0));

            // wildcard case, if one of the letter is lowercase, check if wildcard("_") is available
            if (isLowerCase && !listOfLettersFromRack.contains("_")) {
                GameTextPrinter.printTilesNotInRack(rack, s);
                return false;
            }

            // normal capital letter case
            if (!isLowerCase && !listOfLettersFromRack.contains(s)) {
                GameTextPrinter.printTilesNotInRack(rack, move);
                return false;
            }

            String toBeRemoved = isLowerCase ? "_" : s;
            // if the letter is allowed, remove the letter from the shallow copy
            // so duplicated words played will be checked against rack availability
            // e.g "HH,a1" is not allowed for rack [H4, S1, T1, I1, E1, T1, Y5]
            listOfLettersFromRack.remove(toBeRemoved);
        }
        this.rackHasMove = true;
        return true;
    }

    // helper method - split squareMove into letter and digit

    public boolean isMoveInBound(String move, GameBoard gb) {
        if (!this.isLegal || !this.rackHasMove) return false;
        String wordMove = this.getWordMove(move);
        String squareMove = this.getSquareMove(move); // e.g. "a16"
        // this value is used to check if the word played will exceed the board bounds
        int lengthOfTravel = wordMove.length() - 1;
        char letter = 'a'; // 'a' is a dummy char, will be replaced
        StringBuilder digitBuilder = new StringBuilder();
        for (char ch : squareMove.toCharArray()) {
            if (Character.isLetter(ch)) {
                letter = ch;
            } else {
                if (Character.isDigit(ch)) digitBuilder.append(ch);
            }
        }
        int digit = Integer.parseInt(digitBuilder.toString()); // e.g. 16

        boolean moveInBound;
        String moveDirection = getSquareMoveDirection(move);
        // if move direction is rightward, check if the letter will be in bound*
        // if move direction is downward, check if the digit will be in bound*
        // *with consideration of the length of travel
        if (getSquareMoveDirection(move).equals("RIGHTWARD")) {
            moveInBound = ((letter + lengthOfTravel) < 'a' + gb.getGameBoardSize());
        } else {
            moveInBound = (digit + lengthOfTravel <= gb.getGameBoardSize());
        }

        if (moveInBound) {
            this.moveIsInBound = true;
            this.currentMove = new Move(moveDirection, wordMove, letter, digit, lengthOfTravel);
            return true;
        }

        System.out.println("Illegal move, move result out of bounds");
        return false;
    }

    public boolean moveIsVerified(){
        return isLegal && rackHasMove && moveIsInBound;
    }
}
