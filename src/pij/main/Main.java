package pij.main;

import pij.main.FileProcessor.FileProcessor;
import pij.main.GameBoard.GameBoard;
import pij.main.GameRunner.GameCounters;
import pij.main.GameRunner.GameRunner;
import pij.main.GameRunner.GameTextPrinter;
import pij.main.Move.Move;
import pij.main.Move.MoveValidator;
import pij.main.Players.ComputerPlayer;
import pij.main.Players.HumanPlayer;
import pij.main.Players.Player;
import pij.main.Square.Square;
import pij.main.Tile.Tile;
import pij.main.Tile.TileBag;
import pij.main.Tile.TileRack;
import java.util.List;

import static pij.main.Move.Move.PASS;

public class Main {

    public static void main(String[] args) {
        TileBag tileBag = TileBag.getTileBag();
        GameBoard gameBoard = GameBoard.getGameBoard();
        Player humanPlayer = new HumanPlayer(tileBag); // this can be changed to a computer player and 2 computer players can play among themselves
        Player computerPlayer = new ComputerPlayer(tileBag, gameBoard);
        GameCounters gameCounters = new GameCounters();
        GameRunner gr = new GameRunner(gameBoard, humanPlayer, computerPlayer, gameCounters);

        gr.initNewGame();
        boolean isHumanPlayer = true; // used for swapping players at each round

        while (!gr.isGameOver()) {
            System.out.println("ROUND " + gameCounters.getRoundCounter()); // keeps track of how many rounds have been played
            Player currentPlayer = isHumanPlayer ? humanPlayer : computerPlayer; // for every new round, swap between players
            TileRack playerRack = currentPlayer.getTileRack();

            if (isHumanPlayer) {
                if (gr.isGameOpen()) {
                    System.out.println("OPEN GAME: The computer's tiles:");
                    System.out.println("OPEN GAME: " + gr.getComputerPlayer().getTileRack());
                }
                System.out.println("It's your turn! Your tiles:");
                System.out.println(playerRack);
            } // displays players tile racks

            boolean isMoveVerified = false; // every new round, new move will have to be verified

            while (!isMoveVerified) {
                String playersMove = currentPlayer.move();
                if (playersMove.equals(PASS)) {
                    System.out.println("The " + currentPlayer + " passes this round!\n");
                    gameCounters.incrementPassCounter();
                    break;
                }

                MoveValidator mv = new MoveValidator(playersMove, gameBoard); // new unverified move created here
                mv.createMove();

                // 3 conditional checks before a move is verified to be played on the board
                if (mv.isMoveFormatLegal()) {
                    if (mv.rackContainsMove(playerRack)) {
                        if (mv.isMovePlayableOnBoard(gameCounters.getRoundCounter() == 1)) {
                            isMoveVerified = true;
                            Move move = mv.getCurrentMove();
                            System.out.println("The " + currentPlayer + " move is:   Word: " + move.getWordMove() + " at position " + move.getSquareMove());
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

                        } else {
                            GameTextPrinter.printWordPermittedAtPosition(mv.getCurrentMove());
                        }
                    } else {
                        GameTextPrinter.printTilesNotInRack(playerRack, playersMove);
                    }
                } else {
                    GameTextPrinter.printIllegalMoveFormat();
                }
            }
            currentPlayer.getTileRack().refillPlayerRack(); // refill current player's tile rack at the end of the round
            gameCounters.incrementRoundCounter();
            isHumanPlayer = !isHumanPlayer; // swap the player
        }
        GameTextPrinter.printGameOver(humanPlayer, computerPlayer);
    }


}

