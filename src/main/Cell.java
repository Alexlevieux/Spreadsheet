package main;

import exception.*;
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
import value.Value;

import javax.swing.text.html.parser.Parser;
import java.util.ArrayList;
import java.util.Set;


public class Cell extends TextField {
    ComparableValue value;
    String unprocessedValue;
    ArrayList<Cell> dependants;


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
        try {
            setValue(Evaluator.evaluate(this));
        } catch (DivisionByZeroException e) {
            setText("#DIV/0");
        } catch (InvalidArgumentException e) {
            setText("#ARGS!");
        } catch (InvalidReferenceException e) {
            setText("#REF!");
        } catch (InvalidTokenException e) {
            setText("#NAME?");
        } catch (InvalidValueException e) {
            setText("#VALUE!");
        } catch (NumberException e) {
            setText("#NUM!");
        } catch (ParserException e) {
            System.out.println(e.getMessage());
        }
    }


    public void addDependant(Cell target) {
        this.dependants.add(target);
    }

    public String getUnprocessedValue() {
        return unprocessedValue;
    }


}
