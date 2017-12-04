package chart;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import main.Cell;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditCategories extends Pane implements Initializable {
    @FXML
    private TextField range;
    @FXML
    private Button ok;
    @FXML
    private Button cancel;

    private ArrayList<Cell> cat;
    private String rangeText;

    public void setRangeText (String rt) {
        this.rangeText = rt;
    }


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
            setRangeText(range.getText());
        });
    }

    public String getRangeText() {
        return rangeText;
    }
}
