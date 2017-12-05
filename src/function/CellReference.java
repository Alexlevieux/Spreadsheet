package function;

import exception.InvalidArgumentException;
import table.Cell;
import table.Table;

public abstract class CellReference implements Token{
    private Table table;
    protected CellReference(Table table){
        setTable(table);
    }
    public void setTable(Table table) {
        this.table = table;
    }
    public Table getTable() {
        return table;
    }

    public abstract String getRepresentation();
    public abstract void addDependant(Cell cell);
    public abstract void removeDependant(Cell cell);
    
    public static String convertColumnNumberToLetter(int col){
        int C = col;
        StringBuilder res = new StringBuilder();
        while(C > 0)
        {
            if(C % 26 == 0) { res.insert(0, 'Z'); C /= 26; C--; continue; }
            res.insert(0, (char) ((C % 26) + 'A' - 1));
            C /= 26;
        }
        return res.toString();
    }
    public static int convertColumnLetterToNumber(String colLetter) throws InvalidArgumentException{
        colLetter = colLetter.toUpperCase();
        if(colLetter.matches("[A-Z]")){
            int colNumber = 0;
            for (int i = 0; i < colLetter.length(); ++i) {
                colNumber = colNumber * 26 + (colLetter.charAt(i) - 'A' + 1);
            }
            return colNumber;
        } else
            throw new InvalidArgumentException("Invalid column letter");
    }
}
