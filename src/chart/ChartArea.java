package chart;

import java.lang.String;
import exception.ParserException;
import function.CellRange;
import function.Evaluator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChartArea extends FlowPane implements Initializable {
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
    private ComboBox<String> choice;
    @FXML
    private Button ok;
    @FXML
    private Button cancel;
    @FXML
    private VBox seriesLabels;
    @FXML
    private VBox categoryLabels;
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
    private String selectedChoice = "Bar Chart";
    private String xAxis = "";
    private String yAxis = "";
    private String titleText = "";
    private ObservableList<String> choiceData = FXCollections.observableArrayList();

    private void setChoiceData () {
        choiceData.add("Bar Chart");
        choiceData.add("Line Chart");
        choiceData.add("Scatter Chart");
        choiceData.add("Bubble Chart");
        choiceData.add("Area Chart");
        choiceData.add("Histogram");
    }
    private void setSelectedChoice () {
        selectedChoice = choice.getSelectionModel().getSelectedItem();
    }

    private String getSelectedChoice() {
        return selectedChoice;
    }

    private boolean isHistogram () {
        return selectedChoice.equals("Histogram");
    }

    private void setRangeArea(String rangeArea) {
        if(rangeArea!=null) this.rangeArea = rangeArea;
    }

    private String getRangeArea() {
        return rangeArea;
    }

    private ArrayList<Series> getSeriesArray() {
        return seriesArray;
    }

    private void setSeriesArray(ArrayList<Series> seriesArray) {
        if(seriesArray!=null) this.seriesArray = seriesArray;
    }

    private void setSeriesArray() {
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

    private ArrayList<Category> getCatArray() {
        return catArray;
    }

    private void setCatArray(ArrayList<Category> catArray) {
        this.catArray = catArray;
        selected = new CellRange(
                selected.getTable(),
                selected.getLeftCol(),
                selected.getTopRow(),
                selected.getRightCol(),
                selected.getBottomRow() + 1);
    }

    private void setCatArray() {
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

    private void addSeries(Series series) {
        seriesArray.add(series);
        selected = new CellRange(
                selected.getTable(),
                selected.getLeftCol(),
                selected.getTopRow(),
                selected.getRightCol() + 1,
                selected.getBottomRow());
    }

    private void setSelected(boolean seriesChanged) {
       if (selected == null){
           try {
               if (getRangeArea()!=null)
                   selected = Evaluator.cellNameToRange(Main.getMainWindow().getSheetWindow().getSheet().getTable(), getRangeArea());
               else System.out.println("rangeArea is null");
           } catch (ParserException e1) {
               Alert alert  = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Invalid selection");
               alert.setContentText(e1.getMessage());
               alert.setHeaderText(null);
               alert.showAndWait();
           }
       }
       else {
           if (seriesChanged) {
               selected = new CellRange(
                       Main.getMainWindow().getSheetWindow().getSheet().getTable(),
                       selected.getLeftCol(),
                       selected.getTopRow(),
                       selected.getRightCol()+1,
                       selected.getBottomRow()
               );
           }
           else {
               selected = new CellRange(
                       Main.getMainWindow().getSheetWindow().getSheet().getTable(),
                       selected.getLeftCol(),
                       selected.getTopRow(),
                       selected.getRightCol(),
                       selected.getBottomRow()+1
               );
           }
       }
    }

    private void setSeriesRange() {
        seriesRange = new CellRange(
                selected.getTable(),
                selected.getLeftCol() + 1,
                selected.getTopRow(),
                selected.getRightCol(),
                selected.getTopRow()
        );
    }

    private void setCatRange() {
        catRange = new CellRange(
                selected.getTable(),
                selected.getLeftCol(),
                selected.getTopRow() + 1,
                selected.getLeftCol(),
                selected.getBottomRow()
        );
    }

    private void setCatRange(CellRange catRange) {
        this.catRange = catRange;
    }


    private void setDataRange() {
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

    private CellRange getDataRange() {
        return dataRange;
    }

    private void generateChart() {
        if (!isHistogram()) {
            setSeriesRange();
            if(catRange == null) setCatRange();
        }
        setDataRange();
        if (!isHistogram()) {
            if (seriesArray.size()==0) setSeriesArray();
            else setSeriesArray(seriesArray);
            if (catArray.size()==0) setCatArray();
            else setCatArray(catArray);
            showCat();
            showSeries();
        }
    }

    private void showSeries () {
        seriesLabels.getChildren().clear();
        for (Series aSeriesArray : seriesArray) {
            seriesLabels.getChildren().add(new Label(aSeriesArray.getName()));
        }
    }

    private void showCat () {
        categoryLabels.getChildren().clear();
        for (Category aCatArray : catArray) {
            categoryLabels.getChildren().add(new Label(aCatArray.getName()));
        }
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
        yLabel.setDisable(true);
    }

    private void setNotHistogram() {
        add.setDisable(false);
        series.setDisable(false);
        remove.setDisable(false);
        edit.setDisable(false);
        cat.setDisable(false);
        yLabel.setDisable(false);
    }

    private void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    private String getxAxis() {
        return xAxis;
    }

    private String getyAxis() {
        return yAxis;
    }

    private void setyAxis(String yAxis) {
        this.yAxis = yAxis;
    }

    private void setTitleText (String titleText) {
        this.titleText = titleText;
    }

    private String getTitleText() {
        return titleText;
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        setChoiceData();

        choice.setItems(choiceData);

        choice.getSelectionModel().selectFirst();
        choice.setCellFactory((comboBox) -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });

        choice.setOnAction((event) -> {
            setSelectedChoice();
            if(isHistogram()) setHistogram();
            else setNotHistogram();
            System.out.println("Choice pressed");
        });

        range.setOnAction(e -> {
            System.out.println("Range entered");
            setSelectedChoice();
            setRangeArea(range.getText());
            setSelected(false);
            generateChart();
        });

        title.setOnAction(e -> setTitleText(title.getText()));

        xLabel.setOnAction(e -> setxAxis(xLabel.getText()));

        yLabel.setOnAction(e -> setyAxis(yLabel.getText()));

        add.setOnAction(e -> {
            System.out.println("Add pressed");
            AddLegends al = new AddLegends();
            Stage stage = new Stage();
            stage.setTitle("Edit Series");
            stage.setScene(new Scene(al));
            stage.showAndWait();
            stage.close();
            System.out.println("Add pressed");
            if (al.getNewLegend() != null) {
                addSeries(al.getNewLegend());
                setSelected(true);
            }
            generateChart();
        });

        edit.setOnAction(e -> {
            EditCategories ec = new EditCategories();
            Stage stage = new Stage();
            stage.setTitle("Axis Labels");
            stage.setScene(new Scene(ec));
            stage.showAndWait();
            if (ec.getCatRange() != null) {
                setCatRange(ec.getCatRange());
                setSelected(false);
            }
            generateChart();
            System.out.println("Edit pressed");
        });

        ok.setOnAction(e -> {
            setSelectedChoice();
            setRangeArea(range.getText());
            setSelected(false);
            if (!isHistogram() && yLabel!=null)setxAxis(yLabel.getText());
            else setyAxis("");
            if (xLabel.getText()!=null) setxAxis(xLabel.getText());
            if (title.getText()!=null) setTitleText(title.getText());
            else setTitleText("");
            generateChart();

            ShowChart sc = new ShowChart(getSelectedChoice(), getTitleText(), getDataRange(), getxAxis(), getyAxis(), getCatArray(), getSeriesArray());
            Stage stage = new Stage();
            stage.setTitle("Chart");
            Scene scene = sc.showChart();
            stage.setScene(scene);
            stage.showAndWait();
            System.out.println("Ok pressed");
        });
    }
}