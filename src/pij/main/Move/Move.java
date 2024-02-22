package pij.main.Move;

import pij.main.GameBoard.GameBoard;
import pij.main.Square.Square;
import pij.main.Tile.Tile;
import pij.main.Tile.TileRack;

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
    }

    public static String getSquareMoveByIndex(int x, int y, Direction direction) {
        String xCoord = Integer.valueOf(x + 1).toString();
        String yCoord = Character.toString(y + 97);

        return direction.equals(Direction.RIGHTWARD) ? xCoord + yCoord : yCoord + xCoord;
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

    public int getStartSquareX() {
        return startSquareX;
    }

    public int getStartSquareY() {
        return startSquareY;
    }

    private Square getStartSquare() {
        return this.gameBoard.getSquareByIndex(this.getStartSquareX(), this.getStartSquareY());
    }

    public String getWordFormed() {
        Square startSquare = this.getStartSquare();
        StringBuilder stringBuilderLeft = new StringBuilder();
        StringBuilder stringBuilderRight = new StringBuilder();

        Direction moveDirection = this.getMoveDirection();
        Square leftOrTopPointer = moveDirection.equals(Direction.RIGHTWARD) ? startSquare.getLeftNeighbour() : startSquare.getTopNeighbour();

        while (leftOrTopPointer != null && leftOrTopPointer.isSquareOccupied()) {
            stringBuilderLeft.append(leftOrTopPointer.getTileOnSquare().getDisplayAsLetter());
            leftOrTopPointer = moveDirection.equals(Direction.RIGHTWARD) ? leftOrTopPointer.getLeftNeighbour() : leftOrTopPointer.getTopNeighbour();
        }

        List<String> wordList = new ArrayList<>(this.wordMoveList); // shallow copy

        while ((!wordList.isEmpty()  || (startSquare != null &&  startSquare.isSquareOccupied())) && startSquare != null) {
            if (startSquare.isSquareOccupied()) {
                String tileDisplayAsLetter = startSquare.getTileOnSquare().getDisplayAsLetter();
                stringBuilderRight.append(tileDisplayAsLetter);
            } else {
                String word = wordList.getFirst();
                stringBuilderRight.append(word);
                wordList.remove(word);
            }
            startSquare = this.getMoveDirection().equals(Direction.RIGHTWARD) ? startSquare.getRightNeighbour() : startSquare.getBottomNeighbour();
        }

        return stringBuilderLeft.reverse() + stringBuilderRight.toString();
    }

    private List<String> createWordMoveList(String s) {
        return Arrays.asList(s.split(""));
    }

    public List<Square> getListOfAllPlayableSquares() {
        if (this.listOfAllPlayableSquares != null) return this.listOfAllPlayableSquares;
        Square startSquare = this.getStartSquare();

        // for score calculations
        List<Square> listOfAllPlayableSquares = new ArrayList<>();

        Direction moveDirection = this.getMoveDirection();
        Square leftOrTopPointer = moveDirection.equals(Direction.RIGHTWARD) ? startSquare.getLeftNeighbour() : startSquare.getTopNeighbour();

        while (leftOrTopPointer != null && leftOrTopPointer.isSquareOccupied()) {
            listOfAllPlayableSquares.add(leftOrTopPointer);
            leftOrTopPointer = moveDirection.equals(Direction.RIGHTWARD) ? leftOrTopPointer.getLeftNeighbour() : leftOrTopPointer.getTopNeighbour();
        }

        List<String> wordList = new ArrayList<>(this.wordMoveList); // shallow copy

        while ((!wordList.isEmpty()  || (startSquare != null &&  startSquare.isSquareOccupied())) && startSquare != null) {
            if (startSquare.isSquareOccupied()) {
                listOfAllPlayableSquares.add(startSquare);
            } else {
                listOfAllPlayableSquares.add(startSquare);
                wordList.removeFirst();
            }

                startSquare = this.getMoveDirection().equals(Direction.RIGHTWARD) ? startSquare.getRightNeighbour() : startSquare.getBottomNeighbour();

        }
        this.listOfAllPlayableSquares = listOfAllPlayableSquares;
        return listOfAllPlayableSquares;
    }

    public List<Square> getListOfOccupiedSquares() {
        if (this.listOfOccupiedSquares != null) return this.listOfOccupiedSquares;
        Square startSquare = this.getStartSquare();

        List<Square> listOfOccupiedSquares = new ArrayList<>();

        Direction moveDirection = this.getMoveDirection();
        Square leftOrTopPointer = moveDirection.equals(Direction.RIGHTWARD) ? startSquare.getLeftNeighbour() : startSquare.getTopNeighbour();

        while (leftOrTopPointer != null && leftOrTopPointer.isSquareOccupied()) {
            listOfOccupiedSquares.add(leftOrTopPointer);
            leftOrTopPointer = moveDirection.equals(Direction.RIGHTWARD) ? leftOrTopPointer.getLeftNeighbour() : leftOrTopPointer.getTopNeighbour();
        }

        List<String> wordList = new ArrayList<>(this.wordMoveList); // shallow copy

        while ((!wordList.isEmpty()  || (startSquare != null &&  startSquare.isSquareOccupied())) && startSquare != null) {
            if (startSquare.isSquareOccupied()) {
                listOfOccupiedSquares.add(startSquare);
            } else {
                String word = wordList.getFirst();
                wordList.remove(word);
            }
            startSquare = this.getMoveDirection().equals(Direction.RIGHTWARD) ? startSquare.getRightNeighbour() : startSquare.getBottomNeighbour();
        }
        this.listOfOccupiedSquares = listOfOccupiedSquares;
        return listOfOccupiedSquares;
    }

    public List<Square> getListOfSquaresToBeOccupied() {
        Square startSquare = this.getStartSquare();

        List<Square> listOfSquaresToBeOccupied = new ArrayList<>();
        List<String> wordList = new ArrayList<>(this.wordMoveList); // shallow copy

        while ((!wordList.isEmpty()  || (startSquare != null &&  startSquare.isSquareOccupied())) && startSquare != null) {
            if (!startSquare.isSquareOccupied()) {
                listOfSquaresToBeOccupied.add(startSquare);
                wordList.removeFirst();
            }
            startSquare = this.getMoveDirection().equals(Direction.RIGHTWARD) ? startSquare.getRightNeighbour() : startSquare.getBottomNeighbour();
        }
        this.listOfSquaresToBeOccupied = listOfSquaresToBeOccupied;
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
        return tilesToBeSetOnSquare;
    }

    @Override
    public String toString() {
        return "Move " + this.listOfAllPlayableSquares;
    }
}
