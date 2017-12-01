package function;

import main.Cell;
import main.Table;
import value.Value;

public class CellSingle extends CellReference {
    private int col;
    private int row;

    public CellSingle(Table table, int col, int row) {
        setTable(table);
        setCol(col);
        setRow(row);
    }

    private void setCol(int col) {
        this.col = col;
    }

    private void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Value getValue() {
        try {
            return (Value) getTable().getCells().get(col-1).get(row-1).getValue();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return getTable() + ": " + col + ", " + row;
    }

    public void addDependant(Cell cell){

    }
}
