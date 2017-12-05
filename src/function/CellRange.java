package function;

import exception.CellReferenceFormatException;
import exception.ParserException;
import main.Cell;
import main.Table;
import value.ComparableValue;
import value.ListValue;

import java.util.ArrayList;

@SuppressWarnings({"WeakerAccess", "unused"})
public class CellRange extends CellReference {
    private int leftCol;
    private int rightCol;
    private int topRow;
    private int bottomRow;

    @Override
    public void addDependant(Cell cell) {
        for (int i = leftCol - 1; i < rightCol; ++i) {
            for (int j = topRow - 1; j < bottomRow; ++j) {
                Cell ref = getTable().getCells().get(i).get(j);
                if (ref == null) getTable().addCell(i, j);
                ref = getTable().getCells().get(i).get(j);
                ref.addDependant(cell);
            }
        }
    }

    @Override
    public void removeDependant(Cell cell) {
        for (int i = leftCol - 1; i < rightCol; ++i) {
            for (int j = topRow - 1; j < bottomRow; ++j) {
                Cell ref = getTable().getCells().get(i).get(j);
                if (ref == null) getTable().addCell(i, j);
                ref = getTable().getCells().get(i).get(j);
                ref.removeDependant(cell);
            }
        }
    }

    public CellRange(Table table, int leftCol, int topRow, int rightCol, int bottomRow) {
        super(table);
        this.leftCol = Math.min(leftCol, rightCol);
        this.rightCol = Math.max(leftCol, rightCol);
        this.topRow = Math.min(topRow, bottomRow);
        this.bottomRow = Math.max(topRow, bottomRow);
    }

    public CellRange(Table table, String representation) throws ParserException{
        super(table);
        if (representation.matches("[A-Za-z]+[0-9]+:[A-Za-z]+[0-9]+")) {
            int rowNumber1, colNumber1;
            int rowNumber2, colNumber2;
            String cellRangeName, sheetName;
            String[] cellTokens = representation.split("(?<=[A-Za-z])(?=[0-9])|:");
            if (cellTokens.length != 4) throw new CellReferenceFormatException("Invalid cell name");
            colNumber1 = convertColumnLetterToNumber(cellTokens[0].toUpperCase());
            rowNumber1 = Integer.valueOf(cellTokens[1]);
            colNumber2 = convertColumnLetterToNumber(cellTokens[2].toUpperCase());
            rowNumber2 = Integer.valueOf(cellTokens[3]);
            this.leftCol = Math.min(colNumber1, colNumber2);
            this.rightCol = Math.max(colNumber1, colNumber2);
            this.topRow = Math.min(rowNumber1, rowNumber2);
            this.bottomRow = Math.max(rowNumber1, rowNumber2);
        }
    }

    @Override
    public String getRepresentation() {
        return convertColumnNumberToLetter(leftCol) + String.valueOf(topRow) + ":" +
                convertColumnNumberToLetter(rightCol) + String.valueOf(bottomRow);
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

    public int getRowSize() {
        return bottomRow - topRow + 1;
    }

    public int getColSize() {
        return rightCol - leftCol + 1;
    }

    public int getSize() {
        return getColSize() * getRowSize();
    }

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
        for (int i = leftCol - 1; i < rightCol; ++i) {
            for (int j = topRow - 1; j < bottomRow; ++j) {
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

    public ListValue getColumn(int index) {
        ArrayList<ComparableValue> values = getValue().getValue();
        ListValue column = new ListValue();
        column.addAll(values.subList(getRowSize() * index, getRowSize() * (index + 1)));
        return column;
    }

    public ListValue getRow(int index) {
        ArrayList<ComparableValue> values = getValue().getValue();
        ListValue row = new ListValue();
        for (int i = 0; i < getColSize(); ++i) {
            row.add(values.get(i * getRowSize() + index));
        }
        return row;
    }

    public ComparableValue getValueAt(int col, int row) {
        return getValue().getValue().get(col * getRowSize() + row);
    }
}
