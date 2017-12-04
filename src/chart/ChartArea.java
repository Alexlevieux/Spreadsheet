package chart;

import exception.ParserException;
import function.CellRange;
import function.Evaluator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.Main;

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
    private CellRange selected;
    private CellRange seriesRange;
    private CellRange catRange;
    private CellRange dataRange;
    private ArrayList<Series> seriesArray = new ArrayList<>();
    private ArrayList<Category> catArray = new ArrayList<>();


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
            try {
                selected = Evaluator.cellNameToRange(Main.getMainWindow().getSheet().getTable(), range.getText());
            } catch (ParserException e1) {
                e1.printStackTrace();
            }

            seriesRange = new CellRange(
                    selected.getTable(),
                    selected.getLeftCol() + 1,
                    selected.getTopRow(),
                    selected.getRightCol(),
                    selected.getTopRow()
            );

            catRange = new CellRange(
                    selected.getTable(),
                    selected.getLeftCol(),
                    selected.getTopRow() + 1,
                    selected.getLeftCol(),
                    selected.getBottomRow()
            );

            dataRange = new CellRange(
                    selected.getTable(),
                    selected.getLeftCol() + 1,
                    selected.getTopRow() + 1,
                    selected.getRightCol(),
                    selected.getBottomRow()
            );

            for (int i = 0; i<seriesRange.getColSize(); i++) {
              seriesArray.add(
                      new Series(
                              seriesRange.getValue().get(i).toString(),
                              new CellRange(
                                      dataRange.getTable(),
                                      dataRange.getLeftCol()+i,
                                      dataRange.getTopRow(),
                                      dataRange.getLeftCol()+i,
                                      dataRange.getBottomRow()
                              )
                      )
              );
            }

            for (int i = 0; i<catRange.getRowSize(); i++) {
                catArray.add(
                        new Category(
                                catRange.getValue().get(i).toString(),
                                new CellRange(
                                        dataRange.getTable(),
                                        dataRange.getLeftCol(),
                                        dataRange.getTopRow()+i,
                                        dataRange.getRightCol(),
                                        dataRange.getTopRow()+i
                                )
                        )
                );
            }


            //update series dan cat
        });
        ok.setOnAction(e -> {
            setRangeArea(range.getText());
        });
    }
}