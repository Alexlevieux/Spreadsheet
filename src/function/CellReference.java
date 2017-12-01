package function;

import function.Token;
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
}
