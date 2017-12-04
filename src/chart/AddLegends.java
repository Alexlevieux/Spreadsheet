package chart;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AddLegends extends Pane implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextField value;
    @FXML
    private Button ok;
    @FXML
    private Button cancel;

    private ArrayList <Cell> nameArray;
    private ArrayList <Cell> valueArray;
    private String nameRange;
    private String valueRange;

    public ArrayList<Cell> getNameArray() {
        return nameArray;
    }

    public void setNameArray(ArrayList<Cell> name) {
        this.nameArray = name;
    }

    public ArrayList<Cell> getValueArray() {
        return valueArray;
    }

    public void setValueArray(ArrayList<Cell> value) {
        this.valueArray = value;
    }

    public String getNameRange() {
        return nameRange;
    }

    public void setNameRange(String nameRange) {
        this.nameRange = nameRange;
    }

    public String getValueRange() {
        return valueRange;
    }

    public void setValueRange(String valueRange) {
        this.valueRange = valueRange;
    }

    public AddLegends() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddLegends.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name = new TextField();
        value = new TextField();
        ok = new Button();
        cancel = new Button();

        ok.setOnAction(e -> {
            setNameRange(name.getText());
            setValueRange(value.getText());
        });
    }
}
