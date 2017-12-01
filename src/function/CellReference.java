package function;

import function.Token;
import main.Cell;
import main.Table;
import value.Value;

public abstract class CellReference implements Token{
    private Table table;
    public void setTable(Table table) {
        this.table = table;
    }
    public Table getTable() {
        return table;
    }
    public abstract void addDependant(Cell cell);
    public abstract void removeDependant(Cell cell);
}
