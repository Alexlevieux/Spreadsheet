package main;

import function.Evaluator;
import function.ParserException;
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
import value.Value;

import javax.swing.text.html.parser.Parser;
import java.util.ArrayList;
import java.util.Set;


public class Cell extends TextField {
    Value value;
    String valueUnprocessed;
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


    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public void compute() {
        try {
            Evaluator.evaluate(this);
        } catch(ParserException e) {
            System.out.println("Parser error!");
        }
    }


    public void addRef(Cell target) {
        this.dependants.add(target);
    }


}
