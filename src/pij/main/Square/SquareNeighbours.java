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

    public boolean hasNoNeighbour() {
        return !hasBtmOccupiedNeighbour() && !hasLeftOccupiedNeighbour() && !hasRightOccupiedNeighbour() && !hasTopOccupiedNeighbour();
    }
}
