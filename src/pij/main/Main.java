package pij.main;

import pij.main.GameBoard.GameBoard;
import pij.main.GameRunner.GameCounters;
import pij.main.GameRunner.GameRunner;
import pij.main.GameRunner.GameTextPrinter;
import pij.main.GameRunner.MoveValidator;
import pij.main.Players.Player;
import pij.main.TileBag.TileRack;

public class Main {

    public static void main(String[] args) {
        GameRunner gr = new GameRunner();
        Player humanPlayer = gr.getHumanPlayer();
        Player computerPlayer = gr.getComputerPlayer();
        GameBoard gameBoard = gr.getGameItems().getGameBoard();
        GameCounters gameCounters = gr.getGameCounters();
        String PASS = ",";

        boolean isHumanPlayer = true;

        while(!gr.isGameOver()){
            Player currentPlayer = isHumanPlayer? humanPlayer : computerPlayer;
            TileRack playerRack = isHumanPlayer ? gr.getHumanPlayer().getTileRack() : gr.getComputerPlayer().getTileRack();
            gr.refillTileRacks();

            if(isHumanPlayer){
                if (gameCounters.isGameOpen()) {
                    GameTextPrinter.printComputersRack();
                    System.out.println(gr.getComputerPlayer().getTileRack());
                }

                GameTextPrinter.printItsYourTurn();
                System.out.println(playerRack);
            } // tile racks printer

            boolean isMoveVerified = false; // every new round, new move will have to be verified

            while (!isMoveVerified) {
                String playersMove = currentPlayer.move();
                if (playersMove.equals(PASS)) {
                    gameCounters.incrementPassCounter();
                    break;
                }
                // TODO: pass these in moveIsVerified function
                MoveValidator moveValidator = new MoveValidator(playersMove, gameBoard); // new unverified move created here

                isMoveVerified = moveValidator.isMovePermitted(playerRack, gameCounters);
                System.out.println("The move is: " + "   Word: " + moveValidator.getCurrentMove().getWordMove() + " at position " + moveValidator.getCurrentMove().getSquareMove());
                if (isMoveVerified) {
                    gr.updateGameBoard(moveValidator.getCurrentMove(), playerRack);
                    System.out.println("Human player score: " + humanPlayer.getPlayerScore());
                    System.out.println("Computer player score: " + computerPlayer.getPlayerScore() + "\n");
                    gameCounters.refreshPassCounter();
                    gameBoard.printGameBoard();
                }
            }

            gameCounters.incrementRoundCounter();
            isHumanPlayer = !isHumanPlayer;
        }

//                scanner.close();

    }

}

