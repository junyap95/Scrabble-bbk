package pij.test.FileProcessor;

import org.junit.jupiter.api.Test;
import pij.main.FileProcessor.FileProcessor;
import pij.main.GameBoard.GameBoard;
import pij.main.Square.Square;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {

    @Test
    void boardProcessorForInvalidBoardSize() {
        GameBoard gameBoard = GameBoard.getGameBoard();
        List <List< Square >> sortedTiles = new ArrayList<>();
        gameBoard.setAllSquaresOnBoard(sortedTiles);

        String invalidBoard = "mockBoard-S11.txt";
        String validBoard = "mockBoard-S17.txt";

        assertThrows(RuntimeException.class,()-> FileProcessor.boardProcessor(gameBoard, invalidBoard));
        assertDoesNotThrow(()-> FileProcessor.boardProcessor(gameBoard, validBoard));
    }
}