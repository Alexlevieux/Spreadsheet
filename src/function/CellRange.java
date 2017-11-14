package function;

import java.util.ArrayList;

public class CellRange {
    private int leftCol;
    private int rightCol;
    private int topRow;
    private int bottomRow;
    public int getLeftCol() {
        return leftCol;
    }

    public void setLeftCol(int leftCol) {
        this.leftCol = Math.min(leftCol, rightCol);
        this.rightCol = Math.max(leftCol, rightCol);
    }

    public int getRightCol() {
        return rightCol;
    }

    public void setRightCol(int rightCol) {
        this.leftCol = Math.min(leftCol, rightCol);
        this.rightCol = Math.max(leftCol, rightCol);
    }

    public int getTopRow() {
        return topRow;
    }

    public void setTopRow(int topRow) {
        this.topRow = Math.min(topRow, bottomRow);
        this.bottomRow = Math.max(topRow, bottomRow);
    }

    public int getBottomRow() {
        return bottomRow;
    }

    public void setBottomRow(int bottomRow) {
        this.topRow = Math.min(topRow, bottomRow);
        this.bottomRow = Math.max(topRow, bottomRow);
    }

    public CellRange(int leftCol, int topRow, int rightCol, int bottomRow){
        this.leftCol = Math.min(leftCol, rightCol);
        this.rightCol = Math.max(leftCol, rightCol);
        this.topRow = Math.min(topRow, bottomRow);
        this.bottomRow = Math.max(topRow, bottomRow);
    }
    public ArrayList<DataType>

}
