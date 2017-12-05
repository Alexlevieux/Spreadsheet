package table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@SuppressWarnings("WeakerAccess")
public class Table extends GridPane implements Initializable {
    private static final int MAX_COLUMN = 32767;
    private static final int MAX_ROW = 32767;
    public static final int PREF_COLUMN = 100;
    public static final int PREF_ROW = 100;
    public static final double PREF_COLUMN_WIDTH = 100;
    public static final double PREF_ROW_HEIGHT = 30;
    private IntegerProperty focusedColumn = new SimpleIntegerProperty();
    private IntegerProperty focusedRow = new SimpleIntegerProperty();
    private IntegerProperty selectedColumn = new SimpleIntegerProperty();
    private IntegerProperty selectedRow = new SimpleIntegerProperty();
    private ArrayList<ArrayList<Cell>> cells;

    private Cell getCellByIndex(final int column, final int row) {
        Cell result = null;
        //   ObservableList<Node> childrens = getChildren();
        for (Node cell : getChildren()) {
            if (cell == cells.get(column).get(row)) {
                // if (cell == null) continue;
                result = (Cell) cell;
                break;
            }
        }
        return result;
    }


    public Table() {
        setGridLinesVisible(true);

        cells = new ArrayList<>(PREF_COLUMN);
        for (int i = 0; i < PREF_COLUMN; ++i) {
            cells.add(new ArrayList<>(PREF_ROW));
            for (int j = 0; j < PREF_ROW; j++) {
                cells.get(i).add(null);
            }
        }
        for (int i = 0; i < PREF_COLUMN; ++i) {
            ColumnConstraints columnConstraints = new ColumnConstraints(PREF_COLUMN_WIDTH);
            getColumnConstraints().add(columnConstraints);
        }
        for (int i = 0; i < PREF_ROW; ++i) {
            RowConstraints rowConstraints = new RowConstraints(PREF_ROW_HEIGHT);
            getRowConstraints().add(rowConstraints);
        }
        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                try {
                    setFocusedRow(getFocusedRow() + 1);
                    getCellByIndex(getFocusedColumn(), getFocusedRow()).requestFocus();
                } catch (NullPointerException nulled) {
                    addCell(getFocusedColumn(), getFocusedRow());
                    cells.get(getFocusedColumn()).get(getFocusedRow()).requestFocus();
                }

                //setFocusedRow(getFocusedRow() + 1);
                //  cells.get(getFocusedColumn()).get(getFocusedRow()).requestFocus();
            }
            if (e.getCode() == KeyCode.UP) {
                setFocusedRow(getFocusedRow() - 1);
                cells.get(getFocusedColumn()).get(getFocusedRow()).requestFocus();
            }
            if (e.getCode() == KeyCode.DOWN) {
                setFocusedRow(getFocusedRow() + 1);
                cells.get(getFocusedColumn()).get(getFocusedRow()).requestFocus();
            }
            if (e.getCode() == KeyCode.LEFT) {
                //fokus gerak ke kiri
                setFocusedColumn(getFocusedColumn() - 1);
                cells.get(getFocusedColumn()).get(getFocusedRow()).requestFocus();
            }
            if (e.getCode() == KeyCode.RIGHT) {
                //fokus gerak ke kanan
                setFocusedColumn(getFocusedColumn() + 1);
                cells.get(getFocusedColumn()).get(getFocusedRow()).requestFocus();
            }
        });
        setOnMouseClicked(e -> {
            int col = (int) e.getX() / (int) PREF_COLUMN_WIDTH;
            int row = (int) e.getY() / (int) PREF_ROW_HEIGHT;
            int colt = getFocusedColumn();
            int rowt = getFocusedRow();
            //System.out.println("FOCUSED :" + colt + "," + rowt);
            if (getFocusedRow() != -1 || getFocusedColumn() != -1) {
                try {
                    Cell temp = getCellByIndex(colt, rowt);
//                    System.out.println(GridPane.getColumnIndex(temp) + ", " + GridPane.getRowIndex(temp));
                    if (temp.getLength() == 0 && temp.getDependants().isEmpty() && temp.getPrecedents().isEmpty()) {
                        getChildren().remove(temp);
                        cells.get(colt).set(rowt, null);
                        setFocusedRow(-1);
                        setFocusedColumn(-1);
                    }
                } catch (NullPointerException ignored) {
//                    System.out.println("(" + colt + "," + rowt + ") is null lol");
                }
            }


            //  System.out.println("clicked : " + col + "," + row);
            Cell cell = new Cell();
            if (getRowIndex(cell) == null || getColumnIndex(cell) == null) {
                //  System.out.print("masuk pls \n");
                cells.get(col).set(row, cell);
                add(cell, col, row);
                setFocusedColumn(col);
                setFocusedRow(row);
                cell.setPrefHeight(PREF_ROW_HEIGHT);
                cell.setPrefWidth(PREF_COLUMN_WIDTH);
                cells.get(col).get(row).prefWidthProperty().bind(getColumnConstraints().get(col).prefWidthProperty());
                cells.get(col).get(row).prefHeightProperty().bind(getRowConstraints().get(row).prefHeightProperty());
            }

        });
        setFocusedColumn(-1);
        setFocusedRow(-1);
    }

    public void setFocusedColumn(int focusedColumn) {
        this.focusedColumn.setValue(focusedColumn);

    }

    public void setFocusedRow(int focusedRow) {
        this.focusedRow.setValue(focusedRow);
    }

    public int getFocusedColumn() {
        // System.out.println(focusedColumn);
        return focusedColumn.get();

    }

    public int getFocusedRow() {
        // System.out.println(focusedRow);
        return focusedRow.get();
    }

    public String getFocusedCellText() {
        int col = getFocusedColumn();
        int row = getFocusedRow();
        if (row == -1 || col == -1)
            return null;
        if (cells.get(col).get(row) == null)
            return null;
        else {
            System.out.println(cells.get(col).get(row).getValue());
            return cells.get(col).get(row).getUnprocessedValue();
        }
    }

    public IntegerProperty focusedColumnProperty() {
        return focusedColumn;
    }

    public IntegerProperty focusedRowProperty() {
        return focusedRow;
    }

    public ArrayList<ArrayList<Cell>> getCells() {
        return cells;
    }

    public void addCell(int col, int row) {
        if (cells.get(col).get(row) == null) {
            Cell temp = new Cell();
            temp.setPrefHeight(PREF_ROW_HEIGHT);
            temp.setPrefWidth(PREF_COLUMN_WIDTH);
            cells.get(col).set(row, temp);
            add(cells.get(col).get(row), col, row);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public IntegerProperty selectedColumnProperty() {
        return selectedColumn;
    }

    public void setSelectedColumn(int selectedColumn) {
        this.selectedColumn.set(selectedColumn);
    }

    public IntegerProperty selectedRowProperty() {
        return selectedRow;
    }

    public void setSelectedRow(int selectedRow) {
        this.selectedRow.set(selectedRow);
    }

    public int getSelectedColumn() {
        return selectedColumn.get();
    }

    public int getSelectedRow() {
        return selectedRow.get();
    }
}
