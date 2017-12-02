package function;

import main.Cell;
import main.Table;
import value.Value;

@SuppressWarnings({"WeakerAccess", "unused"})
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
            return getTable().getCells().get(col-1).get(row-1).getValue();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return getTable() + ": " + col + ", " + row;
    }

    @Override
    public void addDependant(Cell cell){
        System.out.println(getTable());
        Cell ref = getTable().getCells().get(col).get(row);
        if(ref == null) getTable().addCell(col, row);
        ref = getTable().getCells().get(col).get(row);
        System.out.println(ref);
        ref.addDependant(cell);
    }

    @Override
    public void removeDependant(Cell cell) {
        Cell ref = getTable().getCells().get(col).get(row);
        if(ref == null) getTable().addCell(col, row);
        ref = getTable().getCells().get(col).get(row);
        ref.removeDependant(cell);
    }

    public Cell getCell(){
        Cell ref = getTable().getCells().get(col).get(row);
        if(ref == null) getTable().addCell(col, row);
        ref = getTable().getCells().get(col).get(row);
        return ref;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellSingle that = (CellSingle) o;

        if (getCol() != that.getCol()) return false;
        return getRow() == that.getRow();
    }
}
