package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SheetWindow extends GridPane implements Initializable{
    @FXML
    private ScrollBar hScroll;
    @FXML
    private ScrollBar vScroll;

    private Sheet sheet;
    public SheetWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SheetWindow.fxml"));
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
        add(sheet,0,0);
        sheet.tableScroll.hminProperty().bind(hScroll.minProperty());
        sheet.tableScroll.vminProperty().bind(vScroll.minProperty());
        sheet.tableScroll.hmaxProperty().bind(hScroll.maxProperty());
        sheet.tableScroll.vmaxProperty().bind(vScroll.maxProperty());
        hScroll.valueProperty().addListener((src, ov, nv) -> {
            double max = sheet.tableScroll.getHmax();
            sheet.tableScroll.setHvalue(nv.doubleValue());
        });
        vScroll.valueProperty().addListener((src, ov, nv) -> {
            double max = sheet.tableScroll.getVmax();
            sheet.tableScroll.setVvalue(nv.doubleValue());
        });
        sheet.tableScroll.hvalueProperty().addListener((src, ov, nv) -> {
            double max = hScroll.getMax();
            hScroll.setValue(nv.doubleValue());
        });
        sheet.tableScroll.vvalueProperty().addListener((src, ov, nv) -> {
            double max = vScroll.getMax();
            vScroll.setValue(nv.doubleValue());
        });

//        vScroll.valueProperty().bind(sheet.tableScroll.vvalueProperty());
//        sheet.tableScroll.hvalueProperty().bind(hScroll.valueProperty());
//        sheet.tableScroll.vvalueProperty().bind(vScroll.valueProperty());


    }

    public Sheet getSheet() {
        return sheet;
    }
}
