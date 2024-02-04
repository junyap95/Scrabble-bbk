package pij.main;

import FileReader.FileProcessor;
import GameBoard.GameBoard;
import Square.Square;
import TileBag.TileBag;
import TileBag.TileRack;
import java.util.List;
import java.util.Scanner;

public class GameRunner {
    static Scanner scanner;
    // game counter
    private int roundCounter;
    private int passCounter;
    private boolean isGameOver;

    // player related fields
    TileBag tileBag;
    TileRack humanRow;
    TileRack computerRow;
    GameBoard gameBoard;

    // constructor, initialises a new game
    public GameRunner(){
        this.bannerPrinter();

        this.roundCounter = 1;
        this.isGameOver = false;
        this.passCounter = 0;

        this.tileBag = new TileBag();
        this.humanRow = new TileRack();
        this.computerRow = new TileRack();
        this.gameBoard = new GameBoard();

        this.loadBoard();
    }

    // increments roundCounter every new round to keep track (additional feature)
    public void setRoundCounter(int roundCounter) {
        this.roundCounter++;
    }

    public void loadBoard(){
        scanner = new Scanner(System.in);
        String playersChoice;
        System.out.println("Would you like to _l_oad a board or use the _d_efault board?" + "\n" + "Please enter your choice (l/d): ");
        playersChoice = scanner.nextLine();
        if (playersChoice.equals("d")) {
            FileProcessor.fileProcessor(this.gameBoard, "defaultBoard.txt");
            this.gameBoard.printGameBoard();
        }

        //TODO load board
        scanner.close();
    }

    public void bannerPrinter() {
        System.out.println("============" + "                     " + "============");
        System.out.println("============" + "  S k r a B B k l e  " + "============");
        System.out.println("============" + "                     " + "============");
    }

    public boolean openCloseGameOption() {
        System.out.println("Would you like to play an _o_pen or a _c_losed game?" + "\n" + "Please enter your choice (o/c): ");
        scanner = new Scanner(System.in);
        String openOrClose = scanner.nextLine().toLowerCase();
        return openOrClose.equals("o");
    }

    public void tileRackLauncher(TileRack computer, TileRack human, boolean openOrClose, TileBag tileBag) {
        computer.refillUserRack(tileBag);
        human.refillUserRack(tileBag);
        if (openOrClose) {
            System.out.println("OPEN GAME: The computer's tiles:");
            computer.printUserRow();
        } else {
            System.out.println("CLOSED GAME: The computer's tiles are not shown");
        }
        System.out.println("It's your turn! Your tiles:");
        human.printUserRow();
    }

    public String userMove() {
        System.out.println("Please enter your move in the format: \"word,square\" (without the quotes) " + "\n"
                           + "For example, for suitable tile rack and board configuration, a downward move could be \"HI,f4\" and a rightward move could be \"HI,4f\"."
                           + "\n\n" + "In the word, upper-case letters are standard tiles"
                           + "and lower-case letters are wildcards."
                           + "\n" + "Entering \",\" passes the turn.");
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public boolean tileVerifier(TileRack tileRack, String userMove, GameBoard gameBoard) {
        boolean validTile = true;
        boolean validPosition = true;


        String[] userInput = userMove.split(",");
        // verify if tilerack contains tile played
        String tileMove = userInput[0]; //HI
        String positionMove = userInput[1];

        List<String> input = List.of(tileMove.split("")); // H, I
        List<String> containsTile = tileRack.getUserRow().stream() // 7 tiles owned
                .map(t -> String.valueOf((t.getDisplayOnBoard().charAt(0))))
                .map(String::toUpperCase)
                .toList();
        for (String s : input) {
            if (!containsTile.contains(s)) {
                System.out.println("With tiles " + tileRack + " you cannot play word " + userMove);
                validTile = false;
                return validTile;
            }
        }

        // verifies if position played allowed, managed by board?
        char columnChar = 0;
        StringBuilder rowChar = new StringBuilder();
        for (int i = 0; i < positionMove.length(); i++) {
            if (Character.isDigit(positionMove.charAt(i))) {
                rowChar.append(positionMove.charAt(i));
            } else {
                columnChar += positionMove.charAt(i);
            }
        }

        //boundary check, managed by board?
        if (Integer.parseInt(rowChar.toString()) > gameBoard.getGameBoardSize() || columnChar >= 'a' + gameBoard.getGameBoardSize()) {
            System.out.println("Illegal move format");
            validPosition = false;
            return validPosition;
        }

        int rowIndex = Integer.parseInt(rowChar.toString()) - 1;
        int colIndex = columnChar - 97;
        Square startSquare = gameBoard.getAllSquares().get(rowIndex).get(colIndex);
        int lengthOfTravel = tileMove.length();
        String directionOfTravel = Character.isDigit(positionMove.charAt(0)) ? "RIGHTWARD" : "DOWNWARD";
        System.out.println("length of travel " + lengthOfTravel);
        System.out.println(directionOfTravel);
        System.out.println("start square is " + startSquare);

        if (directionOfTravel.equals("RIGHTWARD")) {
            updateSquareList("RIGHTWARD", lengthOfTravel, rowIndex, colIndex, input, tileRack, gameBoard);
        } else {
            updateSquareList("DOWNWARD", lengthOfTravel, rowIndex, colIndex, input, tileRack, gameBoard);
        }

        gameBoard.printGameBoard();

        return true;

    }

    public void updateSquareList(String directionOfTravel, int lengthOfTravel, int rowIndex, int colIndex, List<String> input, TileRack tileRack, GameBoard gb) {
        List<String> tileReplaceSquare = tileRack.getUserRow().stream()
                .map(t -> (t.getDisplayOnBoard()).toUpperCase())
                .toList();
        System.out.println(tileReplaceSquare);
        List<List<Square>> squareList = gb.getAllSquares();
        for (int i = 0; i < lengthOfTravel; i++) {
            String current = input.get(i);
            List<String> toBeReplaced = tileReplaceSquare.stream()
                    .filter(s -> current.equals(String.valueOf(s.charAt(0)).toUpperCase()))
                    .toList();
            if (directionOfTravel.equals("RIGHTWARD")) {
                squareList.get(rowIndex).get(colIndex + i).setDisplay(toBeReplaced.getFirst().length() > 2 ? toBeReplaced.getFirst() : toBeReplaced.getFirst() + " ");

            }
            if (directionOfTravel.equals("DOWNWARD")) {
                squareList.get(rowIndex + i).get(colIndex).setDisplay(toBeReplaced.getFirst().length() > 2 ? toBeReplaced.getFirst() : toBeReplaced.getFirst() + " ");

            }


        }
    }
}
