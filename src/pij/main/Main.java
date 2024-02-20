package pij.main;

import pij.main.FileReader.FileProcessor;
import pij.main.GameBoard.GameBoard;
import pij.main.GameRunner.GameCounters;
import pij.main.GameRunner.GameRunner;
import pij.main.GameRunner.GameTextPrinter;
import pij.main.Move.Move;
import pij.main.Move.MoveValidator;
import pij.main.Players.Player;
import pij.main.Square.Square;
import pij.main.Tile.Tile;
import pij.main.Tile.TileRack;

import java.util.List;

import static pij.main.Move.Move.PASS;

public class Main {

    public static void main(String[] args) {
        GameRunner gr = new GameRunner();
        Player humanPlayer = gr.getHumanPlayer();
        Player computerPlayer = gr.getComputerPlayer();
        GameBoard gameBoard = gr.getGameItems().getGameBoard();
        GameCounters gameCounters = gr.getGameCounters();

        boolean isHumanPlayer = true;

        while(!gr.isGameOver()){
            // for every new round, swap players
            Player currentPlayer = isHumanPlayer? humanPlayer : computerPlayer;
            System.out.println("Round " + gameCounters.getRoundCounter());
            TileRack playerRack = isHumanPlayer ? gr.getHumanPlayer().getTileRack() : gr.getComputerPlayer().getTileRack();

            if(isHumanPlayer){
                if (gameCounters.isGameOpen()) {
                    GameTextPrinter.printOpenGameMessage();
                    System.out.println("OPEN GAME: " + gr.getComputerPlayer().getTileRack());
                }

                GameTextPrinter.printItsYourTurn();
                System.out.println(playerRack);
            } // tile racks printer

            boolean isMoveVerified = false; // every new round, new move will have to be verified

            while (!isMoveVerified) {
                String playersMove = currentPlayer.move();
                if (playersMove.equals(PASS)) {
                    System.out.println("The " + currentPlayer + " passes this round!");
                    gameCounters.incrementPassCounter();
                    break;
                }

                MoveValidator moveValidator = new MoveValidator(playersMove, gameBoard); // new unverified move created here
                boolean isMoveFormatLegal = moveValidator.isMoveFormatLegal();
                if(!isMoveFormatLegal) {
                    GameTextPrinter.printIllegalMoveFormat();
                    continue;
                }
                boolean rackContainsMove = moveValidator.rackContainsMove(playerRack);
                if(!rackContainsMove) {
                    GameTextPrinter.printTilesNotInRack(playerRack, playersMove);
                    continue;
                }
                boolean isMovePlayableOnBoard = moveValidator.isMovePlayableOnBoard(gameCounters.getRoundCounter() == 1);
                if(!isMovePlayableOnBoard && moveValidator.getCurrentMove() != null) {
                    GameTextPrinter.printWordPermittedAtPosition(moveValidator.getCurrentMove());
                    continue;
                }

                if (isMovePlayableOnBoard) {
                    isMoveVerified = true;
                    Move move = moveValidator.getCurrentMove();
                    System.out.println("The move is:   Word: " + move.getWordMove() + " at position " + move.getSquareMove());
                    FileProcessor.addToWordAlreadyPlayed(move.getWordFormed());

                    List<Tile> tilesToBeSetOnSquare = move.getTilesToBeSetOnSquare(playerRack);
                    List<Square> squaresToBeOccupied = move.getListOfSquaresToBeOccupied();
                    List<Square> occupiedSquares = move.getListOfOccupiedSquares();

                    gr.updateGameBoard(squaresToBeOccupied, tilesToBeSetOnSquare);

                    gr.updatePlayerScore(tilesToBeSetOnSquare, squaresToBeOccupied, occupiedSquares, currentPlayer);
                    System.out.println("Human player score: " + humanPlayer.getPlayerScore());
                    System.out.println("Computer player score: " + computerPlayer.getPlayerScore() + "\n");

                    gameCounters.resetPassCounter();
                    gameBoard.printGameBoard();
                }
            }

            currentPlayer.getTileRack().refillUserRack();
            gameCounters.incrementRoundCounter();
            isHumanPlayer = !isHumanPlayer;
        }
        GameTextPrinter.printGameOver(humanPlayer, computerPlayer);
    }

}

