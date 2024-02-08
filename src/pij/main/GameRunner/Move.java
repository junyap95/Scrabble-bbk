package pij.main.GameRunner;

import pij.main.GameBoard.GameBoard;
import pij.main.Square.Square;

import java.util.ArrayList;
import java.util.List;

public class Move {
    public String getMoveDirection() {
        return moveDirection;
    }

    public String getWordMove() {
        return wordMove;
    }

    public int getTravelLength() {
        return travelLength;
    }


    public char getSquareMoveLetter() {
        return squareMoveLetter;
    }

    public int getSquareMoveDigit() {
        return squareMoveDigit;
    }

    String moveDirection;
    String wordMove;
    char squareMoveLetter;
    int squareMoveDigit;
    int travelLength;

    public Move(String moveDirection, String wordMove, char squareMoveLetter,int squareMoveDigit, int travelLength) {
        this.moveDirection = moveDirection;
        this.wordMove = wordMove;
        this.travelLength = travelLength;
        this.squareMoveLetter = squareMoveLetter;
        this.squareMoveDigit = squareMoveDigit;
    }

    // this is the list of squares that will be used to
    // check if it contains centre square or
    // check if there is any overlap
    public List<Square> createMoveList(GameBoard gb) {
        // 2D arraylist of all the squares
        var allSquares = gb.getAllSquaresOnBoard();
        int x = getSquareMoveDigit();
        int y = getSquareMoveLetter()-97;
        int travelLength = this.getTravelLength()-1;
        List<Square> moveList = new ArrayList<>();
        for (int i = 0; i < travelLength; i++) {
            if(this.getMoveDirection().equals("RIGHTWARD")) {
                moveList.add(gb.getSquare(x, y + i));
            } else {
                moveList.add(gb.getSquare(x + i, y));
            }
            
        }
        return moveList;
    }
}
