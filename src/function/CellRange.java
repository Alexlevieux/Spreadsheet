package function;

import main.Table;

import java.util.ArrayList;

public class CellRange extends ArrayList implements Token{
    private Table table;
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
    public ArrayList<Valuee> getvalue(){
        return null;
    }
    public CellRange(Table table, int leftCol, int topRow, int rightCol, int bottomRow) {
        this.table = table;
        this.leftCol = Math.min(leftCol, rightCol);
        this.rightCol = Math.max(leftCol, rightCol);
        this.topRow = Math.min(topRow, bottomRow);
        this.bottomRow = Math.max(topRow, bottomRow);
    }

}
