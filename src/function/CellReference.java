package function;

import main.Table;

public class CellReference implements Token{
    private Table table;
    private int col;
    private int row;
    public CellReference(Table table, int col, int row){

        setCol(col);
        setRow(row);
    }


    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setTable(Table table) {
        this.table = table;
    }
    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Table getTable() {
        return table;
    }
    public Valuee getValue(){
        return new Valuee<Comparable>((Comparable)table.getCells().get(row).get(col).getValue());
    }
}
