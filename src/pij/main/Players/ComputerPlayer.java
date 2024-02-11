package pij.main.Players;

public class ComputerPlayer implements Player {

    @Override
    public String move() {
        return ",";
    }

    @Override
    public int getPlayerScore() {
        return 0;
    }

    @Override
    public void updateScore(int playerScore) {

    }
}
