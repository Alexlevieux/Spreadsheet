package main;

import exception.*;
import function.CellReference;
import function.Evaluator;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import value.ComparableValue;
import java.util.ArrayList;


public class Cell extends TextField {
    ComparableValue value;
    String unprocessedValue;
    ArrayList<Cell> dependants;
    ArrayList<CellReference> precedents;


    public Cell() {
        setText("");
        setEditable(false);
        setCursor(Cursor.DEFAULT);
        dependants = new ArrayList<>();
        precedents = new ArrayList<>();
        setStyle("-fx-background-radius: 0");
        setOnAction(e -> {

        });
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && isEditable()) {
                setUnprocessedValue(getText());
                compute();
                setEditable(false);
            } else if (event.getCode() == KeyCode.ENTER && !isEditable()) {
                setText(unprocessedValue);
                selectAll();
                setEditable(true);
            }
        });

        setOnMouseClicked((MouseEvent e) -> {
            if (isFocused()) {
                setText(unprocessedValue);
                setEditable(true);
                selectAll();
            } else {
                requestFocus();
            }
        });

       setOnMouseExited(e -> {
           if (value == null) setUnprocessedValue(getText());
           else setText(value.toString());
        });
    }


    public ComparableValue getValue() {
        return value;
    }

    public void setValue(ComparableValue value) {
        if (value == null) {
            if (this.value != null) {
                this.value = value;
                notifyDependants();
            }
            setText("");
        } else if (!value.equals(this.value)) {
            this.value = value;
            notifyDependants();
            setText(this.value.toString());
        }
    }

    public void compute() {
        ComparableValue val = null;
        try {
            val = Evaluator.evaluate(this);
            setValue(val);
        } catch (ParserException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid formula");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (NullPointerException e) {
            System.out.println("Please enter a value.");
        }
    }

    public void setUnprocessedValue(String unprocessedValue) {
        this.unprocessedValue = unprocessedValue;
    }

    public void addDependant(Cell target) {
        this.dependants.add(target);
    }

    public void removeDependant(Cell target) {
        this.dependants.remove(target);
    }

    public void removeAllDependants() {
        this.dependants.clear();
    }

    public void addPrecedent(CellReference precedent) {
        this.precedents.add(precedent);
    }

    public void removePrecedent(CellReference precedent) {
        this.precedents.remove(precedent);
    }

    public void removeAllPrecedents() {
        this.dependants.clear();
    }

    public String getUnprocessedValue() {
        return unprocessedValue;
    }

    public ArrayList<CellReference> getPrecedents() {
        return new ArrayList<>(precedents);
    }

    public ArrayList<Cell> getDependants() {
        return new ArrayList<>(dependants);
    }

    public void notifyDependants() {
        for (Cell c : dependants) {
            c.compute();
        }
    }
}

