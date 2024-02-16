//package pij.test.GameBoard;
//
//import org.junit.jupiter.api.Test;
//import pij.main.GameBoard.GameBoard;
//import pij.main.Square.ISquare;
//import pij.main.Square.Square;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class GameBoardTest {
//
//    @Test
//    void assertGetGameBoardSize() {
//        GameBoard gameBoard = new GameBoard();
//        int gameBoardSize = 4;
//        gameBoard.setGameBoardSize(gameBoardSize);
//        assertEquals(gameBoard.getGameBoardSize(), gameBoardSize);
//    }
//
//
//    @Test
//    void assertEmptyGameboard() {
//        GameBoard gameBoard = new GameBoard();
//
//        List<List<ISquare>> mockSortedTiles = new ArrayList<>(new ArrayList<>());
//        gameBoard.setAllSquaresOnBoard(mockSortedTiles);
//        assertTrue(gameBoard.isGameBoardEmpty());
//    }
//
//    @Test
//    void assertNonEmptyGameboardWithUnoccupiedSquares() {
//        GameBoard gameBoard = new GameBoard();
//        ISquare unoccupiedSquare1 = new ISquare() {
//            @Override
//            public boolean isSquareOccupied() {
//                return false;
//            }
//        };
//
//
//        List<List<ISquare>> mockSortedTiles = new ArrayList<>();
//        List<ISquare> unoccupiedTile = new ArrayList<>();
//        unoccupiedTile.add(unoccupiedSquare1);
//        mockSortedTiles.add(unoccupiedTile);
//        gameBoard.setAllSquaresOnBoard(mockSortedTiles);
//        assertTrue(gameBoard.isGameBoardEmpty());
//    }
//
//    @Test
//    void assertNonEmptyGameboard() {
//        GameBoard gameBoard = new GameBoard();
//    }
//}