package pij.main.Square;

public class SquareNeighbours {
    private Square topNeighbour;
    private Square bottomNeighbour;
    private Square leftNeighbour;
    private Square rightNeighbour;

    public Square getTopNeighbour() {
        return topNeighbour;
    }

    public void setTopNeighbour(Square topNeighbour) {
        this.topNeighbour = topNeighbour;
    }

    public Square getBottomNeighbour() {
        return bottomNeighbour;
    }

    public void setBottomNeighbour(Square bottomNeighbour) {
        this.bottomNeighbour = bottomNeighbour;
    }

    public Square getLeftNeighbour() {
        return leftNeighbour;
    }

    public void setLeftNeighbour(Square leftNeighbour) {
        this.leftNeighbour = leftNeighbour;
    }

    public Square getRightNeighbour() {
        return rightNeighbour;
    }

    public void setRightNeighbour(Square rightNeighbour) {
        this.rightNeighbour = rightNeighbour;
    }

    public boolean hasTopBottomNeighbour() {
        return this.getTopNeighbour().isSquareOccupied() || this.getBottomNeighbour().isSquareOccupied();
    }

    public boolean hasLeftRightNeighbour() {
        return this.getLeftNeighbour().isSquareOccupied() || this.getRightNeighbour().isSquareOccupied();
    }

    public boolean hasNoNeighbour() {
        return !this.getTopNeighbour().isSquareOccupied() && !this.getBottomNeighbour().isSquareOccupied() && !this.getLeftNeighbour().isSquareOccupied() && !this.getRightNeighbour().isSquareOccupied();
    }
}
