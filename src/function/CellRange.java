package function;

import java.util.ArrayList;

public class CellRange extends ArrayList implements Token{
    private int leftCol;
    private int rightCol;
    private int topRow;
    private int bottomRow;

    public int getLeftCol() {
        return leftCol;
    }

    public int getRightCol() {
        return rightCol;
    }

    public int getTopRow() {
        return topRow;
    }

    public int getBottomRow() {
        return bottomRow;
    }

    // TODO: get values for CellRange 11/14/2017
    @Deprecated
    public ArrayList<Value> getvalue(){
        return null;
    }
    public CellRange(int leftCol, int topRow, int rightCol, int bottomRow) {

        this.leftCol = Math.min(leftCol, rightCol);
        this.rightCol = Math.max(leftCol, rightCol);
        this.topRow = Math.min(topRow, bottomRow);
        this.bottomRow = Math.max(topRow, bottomRow);
    }

}
