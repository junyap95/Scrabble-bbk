package pij.main.GameRunner;

import pij.main.GameBoard.GameBoard;
import pij.main.Square.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Move {

    String moveString;
    String moveDirection;
    String wordMove; // "DOG"
    String squareMove; // "a1"
    List<String> wordMoveList; // ["D", "O", "G"]
    char squareMoveLetter; // 'a'
    int squareMoveDigit; // 16
    List<Square> listOfSquaresToBeOccupied;


    int startSquareX;
    int startSquareY;
    boolean isPass;

    public Move(String moveString) {
        System.out.println("Move init");
        this.moveString = moveString;
        this.wordMove = moveString.split(",")[0];
        this.squareMove = moveString.split(",")[1];
        this.setMoveDirection();
        this.setSquareMoveLetterAndDigit();
        this.startSquareX = this.squareMoveDigit - 1;
        this.startSquareY = this.squareMoveLetter - 97;

        this.wordMoveList = this.createWordMoveList(wordMove);
        this.squareMoveLetter = squareMoveLetter;
        this.squareMoveDigit = squareMoveDigit;
        this.isPass = false;
    }

    public void setSquareMoveLetterAndDigit(){
        char letter = 'a'; // 'a' is a dummy to be replaced
        StringBuilder digitBuilder = new StringBuilder();
        for (char ch : this.squareMove.toCharArray()) {
            if (Character.isLetter(ch)) {
                letter = ch; // e.g. f
            } else {
                if (Character.isDigit(ch)) digitBuilder.append(ch);
            }
        }
        int digit = Integer.parseInt(digitBuilder.toString()); // e.g. 16

        this.squareMoveLetter = letter;
        this.squareMoveDigit = digit;
    }

    public void setMoveDirection() {
        boolean firstCharIsDigit = Character.isDigit(this.squareMove.charAt(0));
        this.moveDirection = firstCharIsDigit? "RIGHTWARD" : "DOWNWARD";
    }

    public void setIsPassTrue() {
        this.isPass = this.moveString.equals(",");
    }

    public boolean isPass() {
        return this.isPass;
    }

    public String getMoveDirection() {
        return this.moveDirection;
    }
    public String getWordMove() {
        return this.wordMove;
    }
    public String getSquareMove() {
        return this.squareMove;
    }

    public int getTravelLength() {
        return this.wordMove.length();
    }

    public List<String> getWordMoveList() {
        return wordMoveList;
    }

    public int getStartSquareX() {
        return startSquareX;
    }

    public int getStartSquareY() {
        return startSquareY;
    }

    // this is the list of squares that will be occupied by tiles played
    public String getWordFormed(GameBoard gb) {
        if (this.isPass()) return null;
        int travelLength = this.getTravelLength();
        Square startSquare = this.getStartSquare(gb);
        Square leftPointer = startSquare.getLeftNeighbour() == null ? null : startSquare.getLeftNeighbour();
        Square topPointer = startSquare.getTopNeighbour() == null ? null : startSquare.getTopNeighbour();
        StringBuilder stringBuilderLeft = new StringBuilder();
        StringBuilder stringBuilderRight = new StringBuilder();

        List<Square> listOfSquaresToBeOccupied = new ArrayList<>();

        System.out.println(this.getMoveDirection());
        while(this.getMoveDirection().equals("RIGHTWARD") && leftPointer != null && leftPointer.isSquareOccupied()){
            stringBuilderLeft.append(leftPointer.getTileOnSquare().getDisplayAsLetter());
            leftPointer = leftPointer.getLeftNeighbour();
        }

        while(this.getMoveDirection().equals("DOWNWARD") && topPointer != null && topPointer.isSquareOccupied()){
            stringBuilderLeft.append(topPointer.getTileOnSquare().getDisplayAsLetter());
            topPointer = topPointer.getBottomNeighbour();
        }


        System.out.println(this.moveString);
        for (int i = 0; i < travelLength; i++) {
            System.out.println("r"+i+stringBuilderRight);
            if (!startSquare.isSquareOccupied()) {
                System.out.println("not occupied");
                listOfSquaresToBeOccupied.add(startSquare);
                stringBuilderRight.append(this.moveString.charAt(i));
            }

            if(startSquare.isSquareOccupied()) {
                System.out.println("occupied");
                stringBuilderRight.append(startSquare.getTileOnSquare().getDisplayAsLetter());
                i--;
            }
            startSquare = this.getMoveDirection().equals("RIGHTWARD") ? startSquare.getRightNeighbour() : startSquare.getBottomNeighbour();

        }
        this.listOfSquaresToBeOccupied = listOfSquaresToBeOccupied;
        System.out.println("word formed: " + stringBuilderLeft.reverse() + stringBuilderRight);
        return stringBuilderLeft.reverse() + stringBuilderRight.toString();
    }

    private List<String> createWordMoveList(String s) {
        return Arrays.stream(s.split("")).toList();
    }

    public Square getStartSquare(GameBoard gb) {
        return gb.getSquareByIndex(this.getStartSquareX(), this.getStartSquareY());
    }

    public List<Square> getListOfSquaresToBeOccupied() {
        return listOfSquaresToBeOccupied;
    }
}
