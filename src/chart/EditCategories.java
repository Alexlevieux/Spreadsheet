package chart;

import exception.ParserException;
import function.CellRange;
import function.Evaluator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditCategories extends Pane implements Initializable {
    @FXML
    private TextField range;
    @FXML
    private Button ok;
    @FXML
    private Button cancel;

    private String catText;
    private CellRange catRange;

    public void setCatText (String rt) {
        this.catText = rt;
    }

    public String getCatText() {
        return catText;
    }

    public CellRange getCatRange() {return catRange;}

    public EditCategories() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCategories.fxml"));
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
        ok = new Button();
        range = new TextField();
        cancel = new Button();
        ok.setOnAction(e -> {
            setCatText(range.getText());
            try {
                catRange = Evaluator.cellNameToRange(Main.getSheetWindow().getSheet().getTable(), catText);
            } catch (ParserException e1) {
                e1.printStackTrace();
            }
        });
    }
}
