package main;

import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.lang.reflect.Array;
import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public class Table extends GridPane {
    private static final int MAX_COLUMN = 32767;
    private static final int MAX_ROW = 32767;
    public static final int PREF_COLUMN = 100;
    public static final int PREF_ROW = 100;
    public static final double PREF_COLUMN_WIDTH = 100;
    public static final double PREF_ROW_HEIGHT = 30;
    private int focusedColumn;
    private int focusedRow;
    private ArrayList<ArrayList<Cell>> cells;


    public Table() {
        setGridLinesVisible(true);
        setFocusedColumn(-1);
        setFocusedRow(-1);
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
        focusedColumn = 0;
        focusedRow = 0;
        setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.ENTER) {
//                setFocusedRow(getFocusedRow() + 1);
//                cells.get(getFocusedColumn()).get(getFocusedRow()).requestFocus();
//            }
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
            if (cells.get(col).get(row) == null) {
                int colt = getFocusedColumn();
                int rowt = getFocusedRow();
                if (cells.get(colt).get(rowt) != null && cells.get(colt).get(rowt).getText().length() == 0) {
                    getChildren().remove(cells.get(colt).get(rowt));
                    cells.get(colt).set(rowt, null);
                    setFocusedRow(-1);
                    setFocusedColumn(-1);
                }
                cells.get(col).set(row, new Cell());
                Cell cell = cells.get(col).get(row);
                setFocusedColumn(col);
                setFocusedRow(row);
                cell.requestFocus();
                cell.setEditable(true);
                cell.setPrefHeight(PREF_ROW_HEIGHT);
                cell.setPrefWidth(PREF_COLUMN_WIDTH);
                cells.get(col).get(row).prefWidthProperty().bind(getColumnConstraints().get(col).prefWidthProperty());
                cells.get(col).get(row).prefHeightProperty().bind(getRowConstraints().get(row).prefHeightProperty());
                add(cells.get(col).get(row), col, row);
            }
        });
    }

    public void setFocusedColumn(int focusedColumn) {
        if (focusedColumn >= 0) this.focusedColumn = focusedColumn;
        //    System.out.println(focusedColumn);
    }

    public void setFocusedRow(int focusedRow) {
        if (focusedRow >= 0) this.focusedRow = focusedRow;
        //  System.out.println(focusedRow);
    }

    public int getFocusedColumn() {
        return focusedColumn;
    }

    public int getFocusedRow() {
        return focusedRow;
    }

    public ArrayList<ArrayList<Cell>> getCells() {
        return cells;
    }

    public void addCell(int col, int row) {
        if (cells.get(col).get(row) != null) {
            Cell temp = new Cell();
            cells.get(col).set(row, temp);
            add(cells.get(col).get(row), col, row);
        }
    }
}
