package FileReader;

import GameBoard.GameBoard;

import java.util.Scanner;

public class FileLoader {
    public static void loadBoard(GameBoard gb){
        //ask user load or default
        Scanner scanner = new Scanner(System.in);
        String userChoice;
        System.out.println("Would you like to _l_oad a board or use the _d_efault board?" + "\n" + "Please enter your choice (l/d): ");
        userChoice = scanner.nextLine();
        if (userChoice.equals("d")) {
            FileProcessor.fileProcessor(gb, "defaultBoard.txt");
            gb.printGameBoard();
        }
    }
}
