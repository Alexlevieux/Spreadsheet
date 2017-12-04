package chart;

import exception.ParserException;
import function.CellRange;
import function.Evaluator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
    private ChoiceBox choice;
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

    public ArrayList<Series> getSeriesArray() {
        return seriesArray;
    }

    public void setSeriesArray(ArrayList<Series> seriesArray) {
        this.seriesArray = seriesArray;
    }

    public void setSeriesArray() {
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
    }

    public ArrayList<Category> getCatArray() {
        return catArray;
    }

    public void setCatArray(ArrayList<Category> catArray) {
        this.catArray = catArray;
        selected.setBottomRow(selected.getBottomRow()+1);
    }

    public void setCatArray() {
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
    }

    public void addSeries (Series series) {
        seriesArray.add(series);
        selected.setRightCol(selected.getRightCol()+1);
    }

    public void setSelected() {
        try {
            selected = Evaluator.cellNameToRange(Main.getMainWindow().getSheet().getTable(), getRangeArea());
        } catch (ParserException e1) {
            // TODO: 04-Dec-17 Add alert to exception
            e1.printStackTrace();
        }
    }

    public void setSeriesRange() {
        seriesRange = new CellRange(
                selected.getTable(),
                selected.getLeftCol() + 1,
                selected.getTopRow(),
                selected.getRightCol(),
                selected.getTopRow()
        );
    }

    public void setCatRange() {
        catRange = new CellRange(
                selected.getTable(),
                selected.getLeftCol(),
                selected.getTopRow() + 1,
                selected.getLeftCol(),
                selected.getBottomRow()
        );
    }

    public void setCatRange(CellRange catRange) {
        this.catRange = catRange;
    }

    public void setDataRange() {
        dataRange = new CellRange(
                selected.getTable(),
                selected.getLeftCol() + 1,
                selected.getTopRow() + 1,
                selected.getRightCol(),
                selected.getBottomRow()
        );
    }

    public void generateChart () {
        setSeriesRange();
        setCatRange();
        setDataRange();
        if (seriesArray == null) setSeriesArray();
        if (catArray == null) setCatArray();
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
            setSelected();
            generateChart();

            // TODO: 04-Dec-17 Update series and cat
        });

        add.setOnAction(e -> {
            AddLegends al = new AddLegends();
            Stage stage = new Stage();
            stage.setTitle("Edit Series");
            stage.setScene(new Scene(al));
            stage.showAndWait();
            if(al.getNewLegend() != null) addSeries(al.getNewLegend());
            setSelected();
            generateChart();
        });

        edit.setOnAction(e -> {
            EditCategories ec = new EditCategories();
            Stage stage = new Stage();
            stage.setTitle("Axis Labels");
            stage.setScene(new Scene (ec));
            stage.showAndWait();
            if (ec.getCatRange() != null) setCatRange(ec.getCatRange());
            generateChart();
        });

        ok.setOnAction(e -> {
            setRangeArea(range.getText());
        });
    }
}