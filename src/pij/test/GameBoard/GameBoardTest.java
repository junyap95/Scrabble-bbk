package pij.test.GameBoard;

import org.junit.jupiter.api.Test;
import pij.main.GameBoard.GameBoard;
import pij.main.Square.Square;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameBoardTest {

    @Test
    void getGameBoardSize() {
        GameBoard gameBoard = GameBoard.getGameBoard();
        int gameBoardSize = 14;
        gameBoard.setGameBoardSize(gameBoardSize);
        assertEquals(gameBoard.getGameBoardSize(), gameBoardSize);
    }


    @Test
    void getCentreSquareIndex() {
        GameBoard gameBoard = GameBoard.getGameBoard();
        gameBoard.setGameBoardSize(12);
        assertEquals(5, gameBoard.getCentreSquareIndex());
        gameBoard.setGameBoardSize(13);
        assertEquals(6,gameBoard.getCentreSquareIndex());
    }

    @Test
    void getSquareByIndex() {
        GameBoard gameBoard = GameBoard.getGameBoard();
        List<List<Square>> allSquaresOnBoard = new ArrayList<>();
        List<Square> row1 = new ArrayList<>();
        Square mockSquare1 = mock(Square.class);
        Square mockSquare2 = mock(Square.class);
        Square mockSquare3 = mock(Square.class);

        row1.add(mockSquare1);
        row1.add(mockSquare2);
        row1.add(mockSquare3);

        allSquaresOnBoard.add(row1);
        gameBoard.setAllSquaresOnBoard(allSquaresOnBoard);

        Square result = gameBoard.getSquareByIndex(0,1);
        assertEquals(result, mockSquare2);
    }



    @Test
    void isGameBoardNotEmpty() {
        GameBoard gameBoard = GameBoard.getGameBoard();
        List<List<Square>> allSquaresOnBoard = new ArrayList<>();
        List<Square> row1 = new ArrayList<>();
        Square mockSquare1 = mock(Square.class);
        Square mockSquare2 = mock(Square.class);
        Square mockSquare3 = mock(Square.class);

        when(mockSquare2.isSquareOccupied()).thenReturn(true);

        row1.add(mockSquare1);
        row1.add(mockSquare2);
        row1.add(mockSquare3);

        allSquaresOnBoard.add(row1);
        gameBoard.setAllSquaresOnBoard(allSquaresOnBoard);

        assertFalse(gameBoard.isGameBoardEmpty());
    }

    @Test
    void isGameBoardEmpty() {
        GameBoard gameBoard = GameBoard.getGameBoard();
        List<List<Square>> allSquaresOnBoard = new ArrayList<>();
        List<Square> row1 = new ArrayList<>();
        Square mockSquare1 = mock(Square.class);
        Square mockSquare2 = mock(Square.class);
        Square mockSquare3 = mock(Square.class);

        row1.add(mockSquare1);
        row1.add(mockSquare2);
        row1.add(mockSquare3);

        allSquaresOnBoard.add(row1);
        gameBoard.setAllSquaresOnBoard(allSquaresOnBoard);

        assertTrue(gameBoard.isGameBoardEmpty());
    }
}