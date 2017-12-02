package function;

import main.Cell;
import main.Table;
import value.ListValue;

@SuppressWarnings({"WeakerAccess", "unused"})
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
        for (int i = leftCol-1; i < rightCol; ++i) {
            for (int j = topRow-1; j < bottomRow; ++j) {
                Cell cell = getTable().getCells().get(i).get(j);
                if (cell == null) {
                    getTable().addCell(i, j);
                }
                cell = getTable().getCells().get(i).get(j);
                list.add(cell.getValue());
            }
        }
        return list;
    }

    @Override
    public void addDependant(Cell cell) {
        for (int i = leftCol-1; i < rightCol; ++i) {
            for (int j = topRow-1; j < bottomRow; ++j) {
                Cell ref = getTable().getCells().get(i).get(j);
                if(ref == null) getTable().addCell(i, j);
                ref = getTable().getCells().get(i).get(j);
                ref.addDependant(cell);
            }
        }
    }

    @Override
    public void removeDependant(Cell cell) {
        for (int i = leftCol-1; i < rightCol; ++i) {
            for (int j = topRow-1; j < bottomRow; ++j) {
                Cell ref = getTable().getCells().get(i).get(j);
                if(ref == null) getTable().addCell(i, j);
                ref = getTable().getCells().get(i).get(j);
                ref.removeDependant(cell);
            }
        }
    }

    public CellRange(Table table, int leftCol, int topRow, int rightCol, int bottomRow) {
        setTable(table);
        this.leftCol = Math.min(leftCol, rightCol);
        this.rightCol = Math.max(leftCol, rightCol);
        this.topRow = Math.min(topRow, bottomRow);
        this.bottomRow = Math.max(topRow, bottomRow);
        System.out.println(this);
    }

    public int getSize() {
        return (rightCol - leftCol + 1) * (bottomRow - topRow + 1);
    }

    @Override
    public String toString() {
        return getTable() + ": (" + leftCol + ", " + topRow + "):(" + rightCol + ", " + bottomRow + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellRange cellRange = (CellRange) o;

        if (getLeftCol() != cellRange.getLeftCol()) return false;
        if (getRightCol() != cellRange.getRightCol()) return false;
        if (getTopRow() != cellRange.getTopRow()) return false;
        return getBottomRow() == cellRange.getBottomRow();
    }
}
