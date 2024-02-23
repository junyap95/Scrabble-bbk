package pij.main.Square;

/**
 * This class holds information for a square's neighbouring squares information
 * important for move validation
 */
public class SquareNeighbours {
    private Square topNeighbour;
    private Square bottomNeighbour;
    private Square leftNeighbour;
    private Square rightNeighbour;

    public Square getTopNeighbour() {
        return topNeighbour;
    }
    public Square getBottomNeighbour() {
        return bottomNeighbour;
    }
    public Square getLeftNeighbour() {
        return leftNeighbour;
    }
    public Square getRightNeighbour() {
        return rightNeighbour;
    }

    public void setTopNeighbour(Square topNeighbour) {
        this.topNeighbour = topNeighbour;
    }
    public void setBottomNeighbour(Square bottomNeighbour) {
        this.bottomNeighbour = bottomNeighbour;
    }
    public void setLeftNeighbour(Square leftNeighbour) {
        this.leftNeighbour = leftNeighbour;
    }
    public void setRightNeighbour(Square rightNeighbour) {
        this.rightNeighbour = rightNeighbour;
    }

    public boolean isSquareOnRightEdge(){
        return this.getRightNeighbour() == null;
    }
    public boolean isSquareOnBottomEdge(){
        return this.getBottomNeighbour() == null;
    }

    public boolean hasTopOccupiedNeighbour() {
        return this.getTopNeighbour() != null && this.getTopNeighbour().isSquareOccupied();
    }
    public boolean hasBtmOccupiedNeighbour() {
        return this.getBottomNeighbour() != null && this.getBottomNeighbour().isSquareOccupied();
    }
    public boolean hasLeftOccupiedNeighbour() {
        return this.getLeftNeighbour() != null && this.getLeftNeighbour().isSquareOccupied();
    }
    public boolean hasRightOccupiedNeighbour() {
        return this.getRightNeighbour() != null && this.getRightNeighbour().isSquareOccupied();
    }
}
