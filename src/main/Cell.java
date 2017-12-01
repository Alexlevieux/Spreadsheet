package main;

import exception.*;
import function.CellReference;
import function.Evaluator;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import value.ComparableValue;
import value.ErrorValue;
import value.Value;

import javax.swing.text.html.parser.Parser;
import java.util.ArrayList;
import java.util.Set;


public class Cell extends TextField {
    ComparableValue value;
    String unprocessedValue;
    ArrayList<Cell> dependants;
    ArrayList<CellReference> precedents;


    public Cell() {
        setText("");
        setEditable(false);
        setCursor(Cursor.DEFAULT);
        setStyle("-fx-background-radius: 0");
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && isEditable()) {
                //   System.out.println(getText());
                //   setValue();

                //   System.out.println(getValue());
                setEditable(false);
            } else if (event.getCode() == KeyCode.ENTER && !isEditable()) {
                setEditable(true);
            }
        });

        setOnMouseClicked((MouseEvent e) -> {
            if (isFocused()) {
                setEditable(true);
            } else {
                requestFocus();
            }
        });
    }


    public ComparableValue getValue() {
        return value;
    }

    public void setValue(ComparableValue value) {
        this.value = value;
        setText(this.value.toString());
    }

    public void compute() {
        ComparableValue val;
        try {
            val = Evaluator.evaluate(this);
        } catch (DivisionByZeroException e) {
            val = new ErrorValue(ErrorType.DIVZERO);
        } catch (InvalidArgumentException e) {
            val = new ErrorValue(ErrorType.ARGS);
        } catch (InvalidReferenceException e) {
            val = new ErrorValue(ErrorType.REF);
        } catch (InvalidTokenException e) {
            val = new ErrorValue(ErrorType.NAME);
        } catch (InvalidValueException e) {
            val = new ErrorValue(ErrorType.VALUE);
        } catch (NumberException e) {
            val = new ErrorValue(ErrorType.NUM);
        } catch (ParserException e) {
            val = new ErrorValue(ErrorType.ERROR);
            System.out.println(e.getMessage());
        }
        setValue(val);
        setText(val.toString());
    }


    public void addDependant(Cell target) {
        this.dependants.add(target);
    }
    public void removeDependant(Cell target){
        this.dependants.remove(target);
    }
    public void removeAllDependants(){
        this.dependants.clear();
    }

    public void addPrecedent(CellReference precedent){
        this.precedents.add(precedent);
    }
    public void removePrecedent(CellReference precedent){
        this.precedents.remove(precedent);
    }
    public void removeAllPrecedents(){
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
}
