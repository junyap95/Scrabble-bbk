package pij.main;

import pij.main.GameBoard.GameBoard;
import pij.main.GameRunner.GameCounters;
import pij.main.GameRunner.GameRunner;
import pij.main.Players.Player;
import pij.main.TileBag.*;

public class Main {

    public static void main(String[] args) {
        GameRunner gr = new GameRunner();
        Player humanPlayer = gr.getHumanPlayer();
        Player computerPlayer = gr.getComputerPlayer();
        GameBoard gameBoard = gr.getGameItems().getGameBoard();
        GameCounters gameCounters = gr.getGameCounters();
        TileRack humansTileRack = gr.getGameItems().getHumansTileRack();
        String PASS = ",";

        //        while(!gr.getGameCounters().isGameOver()){
        // refill both players' racks
        gr.tileRackRunner();

        // human player plays
        String humansMove;
        boolean isMoveLegal = false;
        boolean isWordInRack = false;
        boolean isSquareMoveInBound = false;

        // 1 - check is move is in legal format(able to be split)
        // 2 - check if move is available in rack and in board bounds
        while ((!isMoveLegal || !isWordInRack || !isSquareMoveInBound)) {
            humansMove = humanPlayer.move();
            if (humansMove.equals(PASS)) {
                gr.getCurrentMove().setIsPassTrue();
                break;
            }
            isMoveLegal = gr.legalMoveValidation(humansMove);
            isWordInRack = gr.rackContainsMove(humansMove, humansTileRack);
            isSquareMoveInBound = gr.isMoveInBound(humansMove, gameBoard);
        }
        if (gr.isMovePlayableOnBoard()) gr.updateGameBoard(gameBoard);

        System.out.println("Move legal now check if play is allowed and modify board?");

        //        if(gameCounters.getRoundCounter() == 1) {
        //
        //        }

        //        scanner.close();

    }

}

