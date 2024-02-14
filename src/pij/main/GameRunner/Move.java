package pij.main.GameRunner;

import pij.main.GameBoard.GameBoard;
import pij.main.Square.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Move {

    String moveDirection;
    String wordMove; // "DOG"
    String squareMove; // "a1"
    List<String> wordMoveList; // ["D", "O", "G"]
    List<Square> listOfSquaresToBeOccupied;
    List<Square> listOfOccupiedSquares;

    int startSquareX;
    int startSquareY;
    boolean isPass;

    public Move(String wordMove, String squareMove, int startSquareX, int startSquareY) {
        this.wordMove = wordMove;
        this.squareMove = squareMove;
        this.setMoveDirection();
        this.startSquareX = startSquareX - 1;
        this.startSquareY = startSquareY - 97;

        this.wordMoveList = this.createWordMoveList(wordMove);
        this.isPass = false;
    }


    public void setMoveDirection() {
        boolean firstCharIsDigit = Character.isDigit(this.squareMove.charAt(0));
        this.moveDirection = firstCharIsDigit ? "RIGHTWARD" : "DOWNWARD";
    }

    public void setMoveDirectionDown(){
        this.moveDirection = "DOWNWARD";
    }

    public void setMoveDirectionRight(){
        this.moveDirection = "RIGHTWARD";
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

    public Square getStartSquare(GameBoard gb) {
        return gb.getSquareByIndex(this.getStartSquareX(), this.getStartSquareY());
    }

    // this is the list of squares that will be occupied by tiles played
    public String getWordFormed(GameBoard gb) {
        if (this.isPass()) return null;
        Square startSquare = this.getStartSquare(gb);
        Square leftPointer = startSquare.getLeftNeighbour() == null ? null : startSquare.getLeftNeighbour();
        Square topPointer = startSquare.getTopNeighbour() == null ? null : startSquare.getTopNeighbour();
        StringBuilder stringBuilderLeft = new StringBuilder();
        StringBuilder stringBuilderRight = new StringBuilder();

        // for score calculations
        List<Square> listOfSquaresToBeOccupied = new ArrayList<>();
        List<Square> listOfOccupiedSquares = new ArrayList<>();

        if(this.getTravelLength() == 1) {
            if(startSquare.hasNoNeighbour()) return null;
            if(startSquare.hasTopBottomNeighbour()) {
                this.setMoveDirectionDown();
            }
            if(startSquare.hasLeftRightNeighbour()) {
                this.setMoveDirectionRight();
            }
        }

        while (this.getMoveDirection().equals("RIGHTWARD") && leftPointer != null && leftPointer.isSquareOccupied()) {
            stringBuilderLeft.append(leftPointer.getTileOnSquare().getDisplayAsLetter());
            listOfOccupiedSquares.add(leftPointer);
            leftPointer = leftPointer.getLeftNeighbour();
        }

        while (this.getMoveDirection().equals("DOWNWARD") && topPointer != null && topPointer.isSquareOccupied()) {
            stringBuilderLeft.append(topPointer.getTileOnSquare().getDisplayAsLetter());
            listOfOccupiedSquares.add(topPointer);
            topPointer = topPointer.getTopNeighbour();
        }

        List<String> wordList = new ArrayList<>(this.wordMoveList);

        while(!wordList.isEmpty() || startSquare.isSquareOccupied()){
            if(startSquare.isSquareOccupied()) {
                stringBuilderRight.append(startSquare.getTileOnSquare().getDisplayAsLetter());
                listOfOccupiedSquares.add(startSquare);
            }else{
                String word = wordList.getFirst();
                stringBuilderRight.append(word);
                listOfSquaresToBeOccupied.add(startSquare);
                wordList.remove(word);
            }
            startSquare = this.getMoveDirection().equals("RIGHTWARD") ? startSquare.getRightNeighbour() : startSquare.getBottomNeighbour();
        }

        this.listOfOccupiedSquares = listOfOccupiedSquares;
        this.listOfSquaresToBeOccupied = listOfSquaresToBeOccupied;
        return stringBuilderLeft.reverse() + stringBuilderRight.toString();
    }

    private List<String> createWordMoveList(String s) {
        return Arrays.stream(s.split("")).toList();
    }


    public List<Square> getListOfSquaresToBeOccupied() {
        return listOfSquaresToBeOccupied;
    }

    public List<Square> getListOfOccupiedSquares() {
        return listOfOccupiedSquares;
    }
}
