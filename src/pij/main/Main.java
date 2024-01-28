package pij.main;
import GameBoard.GameBoard;
import FileReader.*;

public class Main {
    public static void main(String[] args) {
        FileProcessor fp = new FileProcessor();
        GameBoard gb = new GameBoard();
        fp.bannerPrinter();
        fp.readFile("defaultBoard.txt");
        fp.fileProcessor(gb);

    }

}

