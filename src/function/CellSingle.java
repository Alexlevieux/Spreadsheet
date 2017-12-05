package function;

import exception.CellReferenceFormatException;
import exception.ParserException;
import table.Cell;
import table.Table;
import value.Value;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CellSingle extends CellReference {
    private int col;
    private int row;

    public CellSingle(Table table, int col, int row) {
        super(table);
        setCol(col);
        setRow(row);
    }
    public CellSingle(Table table, String reference) throws ParserException{
        super(table);
        if(reference.matches("[A-Za-z]+[0-9]+")){
            int rowNumber, colNumber;
            String cellName, sheetName;
            cellName = reference;
            String[] cellTokens = cellName.split("(?<=[A-Za-z])(?=[0-9])");
            if (cellTokens.length != 2) throw new CellReferenceFormatException("Invalid cell name");
            rowNumber = Integer.valueOf(cellTokens[1]);
            colNumber = convertColumnLetterToNumber(cellTokens[0].toUpperCase());
            setCol(colNumber);
            setRow(rowNumber);
        } else {
            throw new CellReferenceFormatException("Invalid cell name");
        }
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
    public String getRepresentation() {
        return convertColumnNumberToLetter(col) + String.valueOf(row);
    }

    public Cell getCell(){
        Cell ref = getTable().getCells().get(col-1).get(row-1);
        if(ref == null) getTable().addCell(col-1, row-1);
        ref = getTable().getCells().get(col-1).get(row-1);
        return ref;
    }

    @Override
    public void addDependant(Cell cell){
//        System.out.println(getTable());
        Cell ref = getTable().getCells().get(col-1).get(row-1);
        if(ref == null) getTable().addCell(col-1, row-1);
        ref = getTable().getCells().get(col-1).get(row-1);
//        System.out.println(ref);
        ref.addDependant(cell);
    }

    @Override
    public void removeDependant(Cell cell) {
        Cell ref = getTable().getCells().get(col-1).get(row-1);
        if(ref == null) getTable().addCell(col-1, row-1);
        ref = getTable().getCells().get(col-1).get(row-1);
        ref.removeDependant(cell);
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
