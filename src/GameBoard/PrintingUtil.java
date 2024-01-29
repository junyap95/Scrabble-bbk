package GameBoard;

public class PrintingUtil {

    public static void printAlphabetsRow(int gameBoardSize) {
        System.out.print("  ");
        for (char i = 97; i < 97 + gameBoardSize; i++) {
            System.out.print(" " + i + " ");
        }
    }
}
