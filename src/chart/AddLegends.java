package chart;

import exception.ParserException;
import function.CellRange;
import function.CellSingle;
import function.Evaluator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import main.Main;

import java.io.IOException;
import java.net.URL;
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

    private String nameText;
    private String valueText;
    private CellSingle nameCell;
    private CellRange valueRange;
    private Series newLegend;

    public String getNameText() {
        return nameText;
    }

    private void setNameText(String nameRange) {
        this.nameText = nameRange;
    }

    public String getValueText() {
        return valueText;
    }

    private void setValueText(String valueRange) {
        this.valueText = valueRange;
    }

    Series getNewLegend() { return newLegend; }

    AddLegends() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddLegend.fxml"));
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
        ok.setOnAction(e -> {
            setNameText(name.getText());
            setValueText(value.getText());

            try {
                nameCell = Evaluator.cellNameToReference(Main.getMainWindow().getSheetWindow().getSheet().getTable(), nameText);
            } catch (ParserException e1) {
                Alert alert  = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid selection");
                alert.setContentText(e1.getMessage());
                alert.setHeaderText(null);
                alert.showAndWait();
                e1.printStackTrace();
            }

            try {
                valueRange = Evaluator.cellNameToRange(Main.getMainWindow().getSheetWindow().getSheet().getTable(), valueText);
            } catch (ParserException e1) {
                Alert alert  = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid selection");
                alert.setContentText(e1.getMessage());
                alert.setHeaderText(null);
                alert.showAndWait();
                e1.printStackTrace();
            }

            newLegend = new Series(nameCell.getValue().toString(), valueRange);
        });
    }
}
