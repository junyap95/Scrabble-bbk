package pij.main.GameRunner;

import pij.main.GameBoard.GameBoard;
import pij.main.Square.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Move {

    String moveDirection;
    String wordMove; // "DOG"
    List<String> wordMoveList; // ["D", "O", "G"]
    char squareMoveLetter; // 'a'
    int squareMoveDigit; // 16
    int travelLength;
    List<Square> moveList;
    int startSquareX;
    int startSquareY;
    boolean isPass;

    public void setIsPassTrue() {
        this.isPass = true;
    }

    public boolean isPass() {
        return this.isPass;
    }

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

    public List<String> getWordMoveList() {
        return wordMoveList;
    }

    public List<Square> getMoveList() {
        return moveList;
    }

    public int getStartSquareX() {
        return startSquareX;
    }

    public int getStartSquareY() {
        return startSquareY;
    }

    public Move(String moveDirection, String wordMove, char squareMoveLetter, int squareMoveDigit, int travelLength) {
        this.moveDirection = moveDirection;
        this.wordMove = wordMove;
        this.wordMoveList = this.createWordMoveList(wordMove);
        this.travelLength = travelLength;
        this.squareMoveLetter = squareMoveLetter;
        this.squareMoveDigit = squareMoveDigit;
        this.startSquareX = this.getSquareMoveDigit() -1;
        this.startSquareY = this.getSquareMoveLetter()-97;
        this.isPass = false;
    }

    // this is the list of squares that will be used to
    // check if it contains centre square or
    // check if there is any overlap
    public List<Square> createMoveList(GameBoard gb) {
        if(this.isPass()) return null;
        // 2D arraylist of all the squares
        int x = this.getStartSquareX();
        // System.out.println(x);
        int y = this.getStartSquareY();
        // System.out.println(y);
        int travelLength = this.getTravelLength();
        List<Square> moveList = new ArrayList<>();
        for (int i = 0; i < travelLength; i++) {
            if(this.getMoveDirection().equals("RIGHTWARD")) {
                moveList.add(gb.getSquareByIndex(x, y + i));
            } else {
                moveList.add(gb.getSquareByIndex(x + i, y));
            }
        }
        this.moveList = moveList;
        return this.getMoveList();
    }

    private List<String> createWordMoveList(String s) {
        return Arrays.stream(s.split("")).toList();
    }
}
