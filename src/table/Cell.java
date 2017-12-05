package table;

import exception.*;
import function.CellReference;
import function.Evaluator;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import main.Main;
import value.ComparableValue;
import java.util.ArrayList;


public class Cell extends TextField {
    private ComparableValue value;
    private String unprocessedValue;
    private ArrayList<Cell> dependants;
    private ArrayList<CellReference> precedents;


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
                setText(getUnprocessedValue());
                selectAll();
                setEditable(true);
            }
        });

        setOnMouseClicked((MouseEvent e) -> {
            if (isFocused()) {
                setText(getUnprocessedValue());
                setEditable(true);
                selectAll();
            } else {
                requestFocus();
            }
        });
        focusedProperty().addListener((src, ov, nv) -> {
            if(!nv){
                if(isEditable()) {
                    String temp = getText();
                    if (temp != null) {
                        if (!temp.equals(getUnprocessedValue())) {
                            setUnprocessedValue(temp);
                            compute();
                        }
                    }
                    setText(getValue() == null ? "" : getValue().toString());
//                    System.out.println(getUnprocessedValue());
//                    System.out.println(getValue() == null ? "" : getValue().toString());
                    setEditable(false);
                }
            }
        });

       setOnMouseExited(e -> {
        //   setEditable(false);
         //  if (value == null) setUnprocessedValue(getText());
        //   else setText(value.toString());
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
        } else setText(this.value.toString());
    }

    public void compute() {
        Main.getWindow().setSaved(false);
        ComparableValue val = null;
        try {
            val = Evaluator.evaluate(this);
            setValue(val);
        } catch (ParserException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid formula");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (NullPointerException ignored) {
//            System.out.println("Please enter a value.");
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

