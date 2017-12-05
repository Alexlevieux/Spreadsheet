package chart;

import java.lang.String;
import exception.ParserException;
import function.CellRange;
import function.Evaluator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
    @FXML
    private Label seriesLabel;
    @FXML
    private Label catLabel;
    @FXML
    private TextField xLabel;
    @FXML
    private TextField yLabel;
    @FXML
    private TextField title;

    private String rangeArea;
    private CellRange selected;
    private CellRange seriesRange;
    private CellRange catRange;
    private CellRange dataRange;
    private ArrayList<Series> seriesArray = new ArrayList<>();
    private ArrayList<Category> catArray = new ArrayList<>();
    private String selectedChoice;
    private String xAxis = "";
    private String yAxis = "";
    private String titleText = "";

    private void setSelectedChoice () {
        selectedChoice = choice.getSelectionModel().getSelectedItem().toString();
    }

    public String getSelectedChoice () {
        return selectedChoice;
    }

    private boolean isHistogram () {
        if (selectedChoice.equalsIgnoreCase("histogram")) return true;
        else return false;
    }

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
        for (int i = 0; i < seriesRange.getColSize(); i++) {
            seriesArray.add(
                    new Series(
                            seriesRange.getValue().get(i).toString(),
                            new CellRange(
                                    dataRange.getTable(),
                                    dataRange.getLeftCol() + i,
                                    dataRange.getTopRow(),
                                    dataRange.getLeftCol() + i,
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
        selected = new CellRange(
                selected.getTable(),
                selected.getLeftCol(),
                selected.getTopRow(),
                selected.getRightCol(),
                selected.getBottomRow() + 1);
    }

    public void setCatArray() {
        for (int i = 0; i < catRange.getRowSize(); i++) {
            catArray.add(
                    new Category(
                            catRange.getValue().get(i).toString(),
                            new CellRange(
                                    dataRange.getTable(),
                                    dataRange.getLeftCol(),
                                    dataRange.getTopRow() + i,
                                    dataRange.getRightCol(),
                                    dataRange.getTopRow() + i
                            )
                    )
            );
        }
    }

    public void addSeries(Series series) {
        seriesArray.add(series);
        selected = new CellRange(
                selected.getTable(),
                selected.getLeftCol(),
                selected.getTopRow(),
                selected.getRightCol() + 1,
                selected.getBottomRow());
    }

    public void setSelected() {
        try {
            selected = Evaluator.cellNameToRange(Main.getSheetWindow().getSheet().getTable(), getRangeArea());
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
        if (!isHistogram()) {
            dataRange = new CellRange(
                    selected.getTable(),
                    selected.getLeftCol() + 1,
                    selected.getTopRow() + 1,
                    selected.getRightCol(),
                    selected.getBottomRow()
            );
        } else {
            dataRange = new CellRange(
                    selected.getTable(),
                    selected.getLeftCol(),
                    selected.getTopRow(),
                    selected.getRightCol(),
                    selected.getBottomRow()
            );
        }
    }

    public CellRange getDataRange() {
        return dataRange;
    }

    private void generateChart() {
        if (!isHistogram()) {
            setSeriesRange();
            setCatRange();
            showSeries();
            showCat();
        }
        setDataRange();
        if (seriesArray == null) setSeriesArray();
        else setSeriesArray(seriesArray);
        if (catArray == null) setCatArray();
        else setCatArray(catArray);
    }

    private void showSeries () {
        String temp = "";
        for (Series aSeriesArray : seriesArray) {
            temp = temp + "&#x2022; " + aSeriesArray.getName() + "\n";
        }

        seriesLabel.setText(temp);
    }

    private void showCat () {
        String temp = "";
        for (Category aCatArray : catArray) {
            temp = temp + "&#x2022; " + aCatArray.getName() + "\n";
        }

        catLabel.setText(temp);
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

    private void setHistogram() {
        add.setDisable(true);
        series.setDisable(true);
        remove.setDisable(true);
        edit.setDisable(true);
        cat.setDisable(true);
        xLabel.setDisable(true);
    }

    private void setNotHistogram() {
        add.setDisable(false);
        series.setDisable(false);
        remove.setDisable(false);
        edit.setDisable(false);
        cat.setDisable(false);
        xLabel.setDisable(false);
    }

    private void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public String getxAxis() {
        return xAxis;
    }

    public String getyAxis() {
        return yAxis;
    }

    private void setyAxis(String yAxis) {
        this.yAxis = yAxis;
    }

    private void setTitleText (String titleText) {
        this.titleText = titleText;
    }

    public String getTitleText() {
        return titleText;
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
        catLabel = new Label();
        seriesLabel = new Label();
        xLabel = new TextField();
        yLabel = new TextField();
        title = new TextField();
        choice = new ChoiceBox<>(FXCollections.observableArrayList("Bar Chart", "Scatter Chart", "Line Chart", "Histogram"));

        range.setOnAction(e -> {
            setSelected();
            setSelectedChoice();
            generateChart();
        });

        title.setOnAction(e -> {
            setTitleText(title.getText());
        });

        xLabel.setOnAction(e -> {
            setxAxis(xLabel.getText());
        });

        yLabel.setOnAction(e -> {
            setyAxis(yLabel.getText());
        });

        choice.setOnAction(e -> {
            if(isHistogram()) setHistogram();
            else setNotHistogram();
        });

        add.setOnAction(e -> {
            AddLegends al = new AddLegends();
            Stage stage = new Stage();
            stage.setTitle("Edit Series");
            stage.setScene(new Scene(al));
            stage.showAndWait();
            if (al.getNewLegend() != null) addSeries(al.getNewLegend());
            setSelected();
            generateChart();
        });

        edit.setOnAction(e -> {
            EditCategories ec = new EditCategories();
            Stage stage = new Stage();
            stage.setTitle("Axis Labels");
            stage.setScene(new Scene(ec));
            stage.showAndWait();
            if (ec.getCatRange() != null) setCatRange(ec.getCatRange());
            generateChart();
        });

        ok.setOnAction(e -> {
            setRangeArea(range.getText());
            setSelectedChoice();
            if (!isHistogram())setxAxis(xLabel.getText());
            else setxAxis("");
            setyAxis(yLabel.getText());
            setTitleText(title.getText());
        });
    }
}