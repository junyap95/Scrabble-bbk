package pij.main.Move;

import pij.main.GameBoard.GameBoard;
import pij.main.Square.Square;
import pij.main.TileBag.Tile;
import pij.main.TileBag.TileRack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Move {

    Direction moveDirection;
    String wordMove; // "DOG"
    String squareMove; // "a1"
    List<String> wordMoveList; // ["D", "O", "G"]
    List<Square> listOfSquaresToBeOccupied;
    List<Square> listOfOccupiedSquares;
    List<Square> listOfAllPlayableSquares;
    GameBoard gameBoard;

    int startSquareX;
    int startSquareY;

    public static final String PASS = ",";

    public Move(String wordMove, String squareMove, int startSquareX, int startSquareY, GameBoard gameBoard) {
        this.wordMove = wordMove;
        this.squareMove = squareMove;
        this.setMoveDirection();
        this.startSquareX = startSquareX;
        this.startSquareY = startSquareY;
        this.gameBoard = gameBoard;
        this.wordMoveList = this.createWordMoveList(wordMove);
        this.getWordFormed();
    }

    public static String getSquareMoveByIndex(int x, int y, Direction direction) {
        String xCoord = Integer.valueOf(x + 1).toString();
        String yCoord = Character.toString(y + 97);

        return direction.equals(Direction.RIGHTWARD) ? xCoord + yCoord : yCoord + xCoord;
    }

    @Override
    public String toString() {
        return "Move " + this.listOfAllPlayableSquares;
    }

    private void setMoveDirection() {
        boolean firstCharIsDigit = Character.isDigit(this.squareMove.charAt(0));
        this.moveDirection = firstCharIsDigit ? Direction.RIGHTWARD : Direction.DOWNWARD;
    }

    public Direction getMoveDirection() {
        return this.moveDirection;
    }

    public String getWordMove() {
        return this.wordMove;
    }

    public String getSquareMove() {
        return this.squareMove;
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

    public Square getStartSquare() {
        return this.gameBoard.getSquareByIndex(this.getStartSquareX(), this.getStartSquareY());
    }

    // this is the list of squares that will be occupied by tiles played
    public String getWordFormed() {
        Square startSquare = this.getStartSquare();
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
        // TODO: Remove duplication
        while (this.getMoveDirection().equals(Direction.RIGHTWARD) && leftPointer != null && leftPointer.isSquareOccupied()) {
            stringBuilderLeft.append(leftPointer.getTileOnSquare().getDisplayAsLetter());
            listOfOccupiedSquares.add(leftPointer);
            listOfAllPlayableSquares.add(leftPointer);
            leftPointer = leftPointer.getLeftNeighbour();
        }

        while (this.getMoveDirection().equals(Direction.DOWNWARD) && topPointer != null && topPointer.isSquareOccupied()) {
            stringBuilderLeft.append(topPointer.getTileOnSquare().getDisplayAsLetter());
            listOfOccupiedSquares.add(topPointer);
            listOfAllPlayableSquares.add(topPointer);
            topPointer = topPointer.getTopNeighbour();
        }

        List<String> wordList = new ArrayList<>(this.wordMoveList); // shallow copy

        while(!wordList.isEmpty() || (startSquare != null && startSquare.isSquareOccupied())){
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
            // TODO: edge error(fixed?)
            startSquare = this.getMoveDirection().equals(Direction.RIGHTWARD) ? startSquare.getRightNeighbour() : startSquare.getBottomNeighbour();
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
        Square startSquare = this.getStartSquare();
        List<Square> listOfSquaresToBeOccupied = new ArrayList<>();
        List<String> wordList = new ArrayList<>(this.wordMoveList); // shallow copy

        while(!wordList.isEmpty() || startSquare.isSquareOccupied()) {
            System.out.println("wordList " + wordList);
            if (!startSquare.isSquareOccupied()) {
                String word = wordList.getFirst();
                listOfSquaresToBeOccupied.add(startSquare);
                wordList.remove(word);
            }
            startSquare = this.getMoveDirection().equals(Direction.RIGHTWARD) ? startSquare.getRightNeighbour() : startSquare.getBottomNeighbour();
        }
        return listOfSquaresToBeOccupied;
    }

    public List<Tile> getTilesToBeSetOnSquare(TileRack tileRack) {
        List<Tile> playerRack = tileRack.getPlayersTiles(); // 7 existing tiles
        List<Tile> tilesToBeSetOnSquare = new ArrayList<>();

        // for every letter in wordMoveList, loop through each tile in player's rack
        // check if there tile's letter value equals to it
        // if true 1. add the tile's display string to an array
        // 2. add the corresponding tile to an array. These 2 arrays are in the same order of when they're added
        for (String s : this.wordMoveList) {
            boolean charIsLowerCase = Character.isLowerCase(s.charAt(0));
            for (Tile tile : playerRack) {
                if (charIsLowerCase && tile.getDisplayAsLetter().equals("_")) {
                    String wildCardNewDisplay = tile.getDisplayOnBoard().replace("_", s);
                    tile.setDisplayOnBoard(wildCardNewDisplay);
                    tilesToBeSetOnSquare.add(tile);
                    playerRack.remove(tile);
                    break;
                } else if (s.equals(tile.getDisplayAsLetter())) {
                    tilesToBeSetOnSquare.add(tile);
                    playerRack.remove(tile);
                    break;
                }
            }
        }
        return  tilesToBeSetOnSquare;
    }

    public List<Square> getListOfAllPlayableSquares() {
        return this.listOfAllPlayableSquares;
    }

    public List<Square> getListOfOccupiedSquares() {
        return listOfOccupiedSquares;
    }
}
