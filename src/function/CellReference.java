package function;

public class CellReference implements Value{
    private int col;
    private int row;
    public CellReference(int col, int row){
        setCol(col);
        setRow(row);
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
