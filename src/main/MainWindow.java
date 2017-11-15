package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow extends BorderPane implements Initializable{
    @FXML
    private MenuBar menuBar;
    @FXML
    private ScrollBar hScroll;
    @FXML
    private ScrollBar vScroll;
    private Sheet sheet;
    public MainWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
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
        //System.out.println(hScroll);
        //System.out.println(vScroll);
        sheet = new Sheet();
       // System.out.println(sheet);
        setCenter(sheet);
        sheet.tableScroll.hminProperty().bind(hScroll.minProperty());
        sheet.tableScroll.vminProperty().bind(vScroll.minProperty());
        sheet.tableScroll.hmaxProperty().bind(hScroll.maxProperty());
        sheet.tableScroll.vmaxProperty().bind(vScroll.maxProperty());
        sheet.tableScroll.hvalueProperty().bind(hScroll.valueProperty());
        sheet.tableScroll.vvalueProperty().bind(vScroll.valueProperty());

    }
}
