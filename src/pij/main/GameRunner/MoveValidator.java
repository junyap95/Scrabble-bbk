package pij.main.GameRunner;

import pij.main.FileReader.FileProcessor;
import pij.main.GameBoard.GameBoard;
import pij.main.Square.Square;
import pij.main.TileBag.TileRack;
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
        if (!move.contains(",")) {
            GameTextPrinter.printIllegalMoveFormat();
            return null;
        }

        // if not 2-part splitting, there are >1 comma included
        if (move.split(",").length != 2 && !move.equals(",")) {
            GameTextPrinter.printIllegalMoveFormat();
            return null;
        }

        String wordMove = moveString.split(",")[0];
        String squareMove = moveString.split(",")[1];

        char letter = '_'; // '_' is a dummy to be replaced
        StringBuilder digitBuilder = new StringBuilder();
        boolean hasDigit = false;
        boolean hasLetter = false;
        for (char ch : squareMove.toCharArray()) {
            if (Character.isLetter(ch)) {
                hasLetter = true;
                letter = ch; // e.g. f
            } else {
                hasDigit = true;
                digitBuilder.append(ch);
            }
        }
        if(!hasLetter || !hasDigit) return null;
        int digit = Integer.parseInt(digitBuilder.toString());
        if(digit <= gameBoard.getGameBoardSize() && letter < 'a' + gameBoard.getGameBoardSize() ) {
            return new Move(wordMove, squareMove, digit, letter);
        }
        return null;
    }

    public Move getCurrentMove() {
        return currentMove;
    }

    // check 1 - format
    public boolean isMoveFormatLegal() {
        if(this.currentMove == null) {
            GameTextPrinter.printIllegalMoveFormat();
            return false;
        }
        if(this.currentMove.isPass) return true;

        String wordMove = this.currentMove.getWordMove(); // i.e. "HI"
        String squareMove = this.currentMove.getSquareMove(); // i.e. "f4"

        // check both split moves lengths, empty string is not allowed
        // longest permitted word move is always 7 letters, shortest 1 letter
        // e.g. "BOOLEAN" or "A"
        // longest permitted square move is always 3, shortest 2
        // e.g. "16a" or "e1"
        if (wordMove.length() > LONGEST_PERMITTED_WORD_STRING || wordMove.isEmpty() || squareMove.length() > LONGEST_PERMITTED_SQUARE_STRING || squareMove.length() < 2) {
            GameTextPrinter.printIllegalMoveFormat();
            return false;
        }

        // guard case for square move of length 3 - will never allow letter in middle: 1a6, 1b4...
        // square move cannot be - aa1, 1bc...must contain exactly one letter: 16a, 1a, a13, a3...
        if (squareMove.length() == LONGEST_PERMITTED_SQUARE_STRING) {
            if (Character.isLetter(squareMove.charAt(1))) {
                GameTextPrinter.printIllegalMoveFormat();
                return false;
            }

            int letterCount = 0;
            for (char ch : squareMove.toCharArray()) {
                if (Character.isLetter(ch)) letterCount++;
            }

            if (letterCount != 1) {
                GameTextPrinter.printIllegalMoveFormat();
                return false;
            }
        }
        return true;
    }

    // check 2 - player's tile rack availability
    public boolean rackContainsMove(TileRack tileRack) {

        // obtain a list of only the letters of a tile (excluding score)
        List<String> listOfLettersFromRack = new ArrayList<>(tileRack.getPlayersTilesAsLetters());

        String[] wordMove = this.currentMove.getWordMove().split(""); // [H, I]

        for (String s : wordMove) {
            // lowercase letter is to check for wildcard in rack
            boolean isLowerCase = Character.isLowerCase(s.charAt(0));

            // if one of the letters is lowercase, check if wildcard("_") is available
            if (isLowerCase && !listOfLettersFromRack.contains("_")) {
                GameTextPrinter.printTilesNotInRack(tileRack, this.moveString);
                return false;
            }

            // normal capital letter case
            if (!isLowerCase && !listOfLettersFromRack.contains(s)) {
                GameTextPrinter.printTilesNotInRack(tileRack, this.moveString);
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

    public boolean isMovePermitted(TileRack playerRack, GameCounters gameCounters) {
        return isMoveFormatLegal() && rackContainsMove(playerRack) && isMovePlayableOnBoard(gameCounters);
    }

    public boolean isMovePlayableOnBoard(GameCounters gameCounters) {
        if(this.currentMove == null || this.currentMove.isPass()) return false;

        String wordFormedFromMove = this.getCurrentMove().getWordFormed(this.gameBoard);
        if(wordFormedFromMove == null) return false;
        Square centreSquare = this.gameBoard.getCentreSquare(); // needed for first round
        String direction = this.currentMove.getMoveDirection();
        List<Square> listOfSquaresToBeOccupied = this.currentMove.getListOfSquaresToBeOccupied();

        // in first round only
        if (gameCounters.getRoundCounter() == 1) return this.moveContainsCentreSquare(listOfSquaresToBeOccupied, centreSquare);

        // round 2 onwards
        // first check all squares to be occupied, at least one of them has to have one neighbour
        boolean sharedSquare= false;
        for (Square sq : listOfSquaresToBeOccupied) {
            // single square case
            if(listOfSquaresToBeOccupied.size() == 1) {
                if(sq.hasNoNeighbour()) return false;
                if(sq.hasTopBottomNeighbour()) this.currentMove.setMoveDirectionDown();
                if(sq.hasLeftRightNeighbour()) this.currentMove.setMoveDirectionRight();
            }
            if(direction.equals("RIGHTWARD") && (sq.hasTopBottomNeighbour())){
                GameTextPrinter.printWordPermittedAtPosition(this.currentMove.getWordMove(), this.currentMove.getSquareMove());
                return false;
            }
            if(direction.equals("DOWNWARD") && (sq.hasLeftRightNeighbour())){
                GameTextPrinter.printWordPermittedAtPosition(this.currentMove.getWordMove(), this.currentMove.getSquareMove());
                System.out.println("down move cannot have left right neighbour");
                return false;
            }
            if(direction.equals("RIGHTWARD") && (sq.hasLeftRightNeighbour())){
                System.out.println("right move has left right neighbour");
                sharedSquare = true;
            }
            if(direction.equals("DOWNWARD") && (sq.hasTopBottomNeighbour())){
                System.out.println("down move has top btm neighbour");
                sharedSquare = true;
            }
        }
        return sharedSquare && FileProcessor.wordListProcessor(wordFormedFromMove);
    }

    // helper method (for first move of the game) - check if moves contain centre square, and forms a legit word
    private boolean moveContainsCentreSquare(List<Square> moveList, Square centreSquare) {
        for (Square square : moveList) {
            if (square == centreSquare) {
                // after checking centre square just check if word is in word list
                return FileProcessor.wordListProcessor(currentMove.getWordMove());
            }
        }
        System.out.println("Illegal move. In the first round, one of your tiles must be placed on the centre square in the first round.");
        return false; // if the loop finished - the moveList does not contain centre square - invalid move
    }


    // check 3 - move in board's boundary of S*S
//    public boolean isMoveInBound(String move, GameBoard gb, TileRack tileRack) {
//
//        String wordMove = this.currentMove.getWordMove();
//        String squareMove = this.currentMove.getSquareMove(); // e.g. "a16"
//
//        // this value is used to check if the word played will exceed the board bounds
//        int lengthOfTravel = this.currentMove.getTravelLength();
//
//        char letter = 'a'; // 'a' is a dummy char, will be replaced
//        StringBuilder digitBuilder = new StringBuilder();
//        for (char ch : squareMove.toCharArray()) {
//            if (Character.isLetter(ch)) {
//                letter = ch; // e.g. f
//            } else {
//                if (Character.isDigit(ch)) digitBuilder.append(ch);
//            }
//        }
//        int digit = Integer.parseInt(digitBuilder.toString()); // e.g. 16
//
//        boolean moveInBound;
//        String moveDirection = getSquareMoveDirection(squareMove);
//        // if move direction is rightward, check if the letter will be in bound*
//        // if move direction is downward, check if the digit will be in bound*
//        // *with consideration of the length of travel
//        if (moveDirection.equals("RIGHTWARD")) {
//            moveInBound = ((letter + lengthOfTravel - 1) < 'a' + gb.getGameBoardSize());
//        } else {
//            moveInBound = (digit + lengthOfTravel - 1 <= gb.getGameBoardSize());
//        }
//
//        if (moveInBound) {
//            this.currentMove = new Move(moveDirection, wordMove, letter, digit, lengthOfTravel);
//            Square startSquare = this.getCurrentMove().getStartSquare(gb);
//            if (startSquare.isSquareOccupied()) {
//                System.out.println("Illegal move, the first tile cannot be placed on an occupied square");
//                return false;
//            }
//            return true;
//        }
//
//        System.out.println("Illegal move, move result out of bounds");
//        return false;
//    }

}
