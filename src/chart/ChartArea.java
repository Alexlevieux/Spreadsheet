package chart;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChartArea extends Pane implements Initializable {
    @FXML
    private TextField range;
    @FXML
    private Button add;
    @FXML
    private Button remove;
    @FXML
    private Button edit;
    @FXML
    private AnchorPane series;
    @FXML
    private AnchorPane cat;
    @FXML
    private Button ok;
    @FXML
    private Button cancel;

    private String rangeArea;


    public void setRangeArea(String rangeArea) {
        this.rangeArea = rangeArea;
    }

    public String getRangeArea() {
        return rangeArea;
    }

    public ChartArea() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChartArea.fxml"));
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
        range = new TextField();
        add = new Button();
        remove = new Button();
        edit = new Button();
        ok = new Button();
        series = new AnchorPane();
        cat = new AnchorPane();

        range.setOnAction(e -> {
            //update series dan cat
        });
        ok.setOnAction(e -> {
            setRangeArea(range.getText());
        });
    }
}