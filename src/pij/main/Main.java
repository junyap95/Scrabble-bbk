package pij.main;

import pij.main.FileReader.FileProcessor;
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
            System.out.println("round " + gameCounters.getRoundCounter());
            System.out.println("which player now? " + currentPlayer);
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
                if (isMoveVerified) {
                    System.out.println("The move is: " + "   Word: " + moveValidator.getCurrentMove().getWordMove() + " at position " + moveValidator.getCurrentMove().getSquareMove());
                    FileProcessor.addToWordAlreadyPlayed(moveValidator.getCurrentMove().getWordFormed());
                    gr.updateGameBoard(moveValidator.getCurrentMove(), playerRack, currentPlayer);
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

