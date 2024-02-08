package pij.main.GameRunner;

import pij.main.TileBag.TileRack;

public class GameTextPrinter {

    public static void printWelcomeBanner() {
        System.out.println("============" + "                     " + "============");
        System.out.println("============" + "  S k r a B B k l e  " + "============");
        System.out.println("============" + "                     " + "============");
    }

    public static void printAlphabetsRow(int gameBoardSize) {
        System.out.print("  ");
        for (char i = 97; i < 97 + gameBoardSize; i++) {
            System.out.print(" " + i + " ");
        }
    }

    public static void printPlayersMoveInstruction(){
        System.out.println("Please enter your move in the format: \"word,square\" (without the quotes) " + "\n"
                           + "For example, for suitable tile rack and board configuration, a downward move could be \"HI,f4\" and a rightward move could be \"HI,4f\"."
                           + "\n\n" + "In the word, upper-case letters are standard tiles"
                           + "and lower-case letters are wildcards."
                           + "\n" + "Entering \",\" passes the turn.");
    }

    public static void printLoadBoard(){
        System.out.println("Would you like to _l_oad a board or use the _d_efault board?" + "\n" + "Please enter your choice (l/d): ");
    }

    public static void printOpenOrCloseGameText(){
        System.out.println("Would you like to play an _o_pen or a _c_losed game?" + "\n" + "Please enter your choice (o/c): ");
    }

    public static void printComputersRack(boolean isGameOpen){
        String openGame = "OPEN GAME: The computer's tiles:";
        System.out.println(openGame);
//        String closeGame = "CLOSED GAME: The computer's tiles are not shown";
    }

    public static void printItsYourTurn(){
        System.out.println("It's your turn! Your tiles:");
    }

    public static void printIllegalMoveFormat(){
        System.out.println("Illegal Move Format");
    }

    public static void printTilesNotInRack(TileRack t, String s){
        System.out.println("With tiles " + t + " you cannot play word " + s);
    }
}
