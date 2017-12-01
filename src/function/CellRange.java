package function;

import main.Cell;
import main.Table;
import value.ListValue;

public class CellRange extends CellReference{
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

    public ListValue getValue() {
        ListValue list = new ListValue();
        for (int i = leftCol; i <= rightCol; ++i) {
            for (int j = topRow; j <= bottomRow; ++j) {
                Cell cell = getTable().getCells().get(i).get(j);
                if (cell == null) {
                    getTable().addCell(i, j);
                }
                list.add(cell.getValue());
            }
        }
        return list;
    }

    public void addDependant(Cell cell) {
        for (int i = leftCol; i <= rightCol; ++i) {
            for (int j = topRow; j <= bottomRow; ++j) {
                getTable().getCells().get(i).get(j).addDependant(cell);
            }
        }
    }

    public CellRange(Table table, int leftCol, int topRow, int rightCol, int bottomRow) {
        setTable(table);
        this.leftCol = Math.min(leftCol, rightCol);
        this.rightCol = Math.max(leftCol, rightCol);
        this.topRow = Math.min(topRow, bottomRow);
        this.bottomRow = Math.max(topRow, bottomRow);
    }

    public int getSize() {
        return (rightCol - leftCol + 1) * (bottomRow - topRow + 1);
    }

    @Override
    public String toString() {
        return getTable() + ": (" + leftCol + ", " + topRow + "):(" + rightCol + ", " + bottomRow + ")";
    }
}
