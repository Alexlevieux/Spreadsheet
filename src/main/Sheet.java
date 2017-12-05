package main;

import function.CellReference;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;

public class Sheet extends GridPane implements InvalidationListener {
    private Table table;
    protected ScrollPane tableScroll, rowScroll, colScroll;
    private VBox rowNumbers;
    private HBox colNumbers;

    public Sheet() {
        setPrefWidth(800);
        setPrefHeight(600);
        table = new Table();
        rowNumbers = new VBox();
        colNumbers = new HBox();

        tableScroll = new ScrollPane(table);
        rowScroll = new ScrollPane(rowNumbers);
        colScroll = new ScrollPane(colNumbers);

        rowScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rowScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        colScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        colScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        tableScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        tableScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        add(rowScroll, 0, 1);
        add(colScroll, 1, 0);
        add(tableScroll, 1, 1);

        rowScroll.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaX() != 0 || event.getDeltaY() != 0) {
                event.consume();
            }
        });
        colScroll.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaX() != 0) {
                event.consume();
            }
        });
//        tableScroll.addEventFilter(ScrollEvent.SCROLL, e -> {
//            if (e.getDeltaX() != 0 || e.getDeltaY() != 0) {
////                e.consume();
//            }
//        });

        // setGridLinesVisible(true);

        for (int i = 0; i < Table.PREF_ROW; i++) {
            Label x = new Label(String.valueOf(i + 1));
            x.setPrefHeight(Table.PREF_ROW_HEIGHT);
            x.setPrefWidth(Table.PREF_ROW_HEIGHT);
            x.setStyle(
                    "-fx-border-style: none none solid none;" +
                            "-fx-border-color: black;" +
                            "-fx-font: normal 12 'segoe ui';" +
                            "-fx-border-width: inherit");
            x.setAlignment(Pos.CENTER);
            rowNumbers.getChildren().add(x);
        }

        for (int i = 0; i < Table.PREF_COLUMN; i++) {
            Label x = new Label(CellReference.convertColumnNumberToLetter(i + 1));
            x.setStyle(
                    "-fx-border-style: none solid none none;" +
                            "-fx-border-color: black;" +
                            "-fx-font: normal 12 'segoe ui';" +
                            "-fx-border-width: inherit");
            x.setPrefWidth(Table.PREF_COLUMN_WIDTH);
            x.setPrefHeight(Table.PREF_ROW_HEIGHT);
            x.setAlignment(Pos.CENTER);
            colNumbers.getChildren().add(x);

        }

        rowScroll.vminProperty().bind(tableScroll.vminProperty());
        rowScroll.vmaxProperty().bind(tableScroll.vmaxProperty());
        colScroll.hminProperty().bind(tableScroll.hminProperty());
        colScroll.hmaxProperty().bind(tableScroll.vmaxProperty());
        rowScroll.vvalueProperty().bind(tableScroll.vvalueProperty());
        colScroll.hvalueProperty().bind(tableScroll.hvalueProperty());
        //  tableScroll.vvalueProperty().bind(rowScroll.vvalueProperty());
        //  tableScroll.hvalueProperty().bind(colScroll.hvalueProperty());
    }

    public Table getTable() {
        return table;
    }

    @Override
    public void invalidated(Observable observable) {

    }
}
