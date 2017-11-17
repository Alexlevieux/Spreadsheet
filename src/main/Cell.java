package main;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Set;


public class Cell extends TextField implements Observable{
    Property value;
    String temp;
    String computedValue;
    Set<Cell> references;


    public Cell() {
        setText("");
        setEditable(false);
        setCursor(Cursor.DEFAULT);
        setStyle("-fx-background-radius: 0");
        setOnAction(e -> {
            temp = getText();
            computedValue = temp;
        });

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && isEditable()) {
             //   System.out.println(getText());
                setValue(getText());
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


    public Object getValue() {
        try {
            SimpleDoubleProperty d = (SimpleDoubleProperty) valueProperty();
            return d.get();
        } catch (ClassCastException e) {
            SimpleStringProperty d = (SimpleStringProperty) valueProperty();
            return d.get();
        }
    }

    public Property valueProperty() {
        try {
            SimpleDoubleProperty d = (SimpleDoubleProperty) value;
            return d;
        }  catch (ClassCastException e) {
            SimpleStringProperty s  = (SimpleStringProperty) value;
            return s;
        }
    }

    public void setValue(String value) {
        try {
            this.value = new SimpleDoubleProperty(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            this.value = new SimpleStringProperty(value);
        };
    }

    public void addRef(Cell target) {
        this.references.add(target);
    }

}
