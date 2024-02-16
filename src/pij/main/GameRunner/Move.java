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
    List<Square> listOfAllPlayableSquares;
    GameBoard gameBoard;

    int startSquareX;
    int startSquareY;
    boolean isPass;

    public Move(String wordMove, String squareMove, int startSquareX, int startSquareY, GameBoard gameBoard) {
        this.wordMove = wordMove;
        this.squareMove = squareMove;
        this.setMoveDirection();
        this.startSquareX = startSquareX;
        this.startSquareY = startSquareY;
        this.gameBoard = gameBoard;
        this.wordMoveList = this.createWordMoveList(wordMove);
        this.getWordFormed();

        this.isPass = false;
    }

    @Override
    public String toString() {
        return "Move " + this.listOfAllPlayableSquares;
    }

    private void setMoveDirection() {
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
    public String getWordFormed() {
        if (this.isPass()) return null;
        Square startSquare = this.getStartSquare(this.gameBoard);
        Square leftPointer = startSquare.getLeftNeighbour() == null ? null : startSquare.getLeftNeighbour();
        Square topPointer = startSquare.getTopNeighbour() == null ? null : startSquare.getTopNeighbour();
        StringBuilder stringBuilderLeft = new StringBuilder();
        StringBuilder stringBuilderRight = new StringBuilder();

        // for score calculations
        List<Square> listOfSquaresToBeOccupied = new ArrayList<>();
        List<Square> listOfOccupiedSquares = new ArrayList<>();
        List<Square> listOfAllPlayableSquares = new ArrayList<>();

        // TODO: travel length should be in constructor to set direction etc
        // start square on occupied tile error
//        if(this.getTravelLength() == 1) {
//            if(startSquare.hasTopOccupiedNeighbour() || startSquare.hasBtmOccupiedNeighbour()) {
//                this.setMoveDirectionDown();
//            }else if(startSquare.hasLeftOccupiedNeighbour() || startSquare.hasRightOccupiedNeighbour()) {
//                this.setMoveDirectionRight();
//            } else return null;
//        }

        while (this.getMoveDirection().equals("RIGHTWARD") && leftPointer != null && leftPointer.isSquareOccupied()) {
            stringBuilderLeft.append(leftPointer.getTileOnSquare().getDisplayAsLetter());
            listOfOccupiedSquares.add(leftPointer);
            listOfAllPlayableSquares.add(leftPointer);
            leftPointer = leftPointer.getLeftNeighbour();
        }

        while (this.getMoveDirection().equals("DOWNWARD") && topPointer != null && topPointer.isSquareOccupied()) {
            stringBuilderLeft.append(topPointer.getTileOnSquare().getDisplayAsLetter());
            listOfOccupiedSquares.add(topPointer);
            listOfAllPlayableSquares.add(topPointer);
            topPointer = topPointer.getTopNeighbour();
        }

        List<String> wordList = new ArrayList<>(this.wordMoveList); // shallow copy

        while(!wordList.isEmpty() || startSquare.isSquareOccupied()){
            if(startSquare.isSquareOccupied()) {
                String tileDisplayAsLetter = startSquare.getTileOnSquare().getDisplayAsLetter();
                stringBuilderRight.append(tileDisplayAsLetter);
                listOfOccupiedSquares.add(startSquare);
                listOfAllPlayableSquares.add(startSquare);
            }else{
                String word = wordList.getFirst();
                stringBuilderRight.append(word);
                listOfSquaresToBeOccupied.add(startSquare);
                listOfAllPlayableSquares.add(startSquare);
                wordList.remove(word);
            }
            // TODO: edge error
            startSquare = this.getMoveDirection().equals("RIGHTWARD") ?
                    (startSquare.isSquareOnRightEdge() ? startSquare : startSquare.getRightNeighbour()) :
                    (startSquare.isSquareOnBottomEdge() ? startSquare :startSquare.getBottomNeighbour());
        }
        this.listOfAllPlayableSquares = listOfAllPlayableSquares;
        this.listOfOccupiedSquares = listOfOccupiedSquares;
        this.listOfSquaresToBeOccupied = listOfSquaresToBeOccupied;
        return stringBuilderLeft.reverse() + stringBuilderRight.toString();
    }

    private List<String> createWordMoveList(String s) {
        return Arrays.asList(s.split("")) ;
    }

    public List<Square> getListOfSquaresToBeOccupied() {
        return listOfSquaresToBeOccupied;
    }

    public List<Square> getListOfAllPlayableSquares() {
        return this.listOfAllPlayableSquares;
    }

    public List<Square> getListOfOccupiedSquares() {
        return listOfOccupiedSquares;
    }
}
