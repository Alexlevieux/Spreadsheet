package chart;

import function.CellRange;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowChart extends StackPane implements Initializable {
    @FXML
    private BarChart bar;
    @FXML
    private AreaChart area;
    @FXML
    private BubbleChart bubble;
    @FXML
    private LineChart line;
    @FXML
    private ScatterChart scatter;
    @FXML
    private Pane blank;

    private String selectedChoice;
    private String titleText;
    private CellRange dataRange;
    private String xAxis;
    private String yAxis;
    private ArrayList<Category> catArray;
    private ArrayList<Series> seriesArray;

    private String getSelectedChoice() {
        return selectedChoice;
    }

    private void setSelectedChoice(String selectedChoice) {
        this.selectedChoice = selectedChoice;
    }

    private String getTitleText() {
        return titleText;
    }

    private void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    private CellRange getDataRange() {
        return dataRange;
    }

    private void setDataRange(CellRange dataRange) {
        this.dataRange = dataRange;
    }

    private String getxAxis() {
        return xAxis;
    }

    private void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    private String getyAxis() {
        return yAxis;
    }

    public void setyAxis(String yAxis) {
        this.yAxis = yAxis;
    }

    private ArrayList<Category> getCatArray() {
        return catArray;
    }

    private void setCatArray(ArrayList<Category> catArray) {
        this.catArray = catArray;
    }

    private ArrayList<Series> getSeriesArray() {
        return seriesArray;
    }

    private void setSeriesArray(ArrayList<Series> seriesArray) {
        this.seriesArray = seriesArray;
    }

    private void initChart() {
        bar.setVisible(false);
        area.setVisible(false);
        bubble.setVisible(false);
        line.setVisible(false);
        scatter.setVisible(false);
    }

    public Scene showChart() {
        AddLegends al = new AddLegends();
        Scene scene = new Scene(al);
        switch (getSelectedChoice()) {
            case "Area":
                area.setVisible(true);
                Area a = new Area(getTitleText(), getSeriesArray(), getCatArray(), getxAxis(), getyAxis());
                scene = new Scene(a.getArea());
                break;

            case "Histogram":
                bar.setVisible(true);
                Histogram h = new Histogram(getTitleText(), getDataRange(), getxAxis(), getyAxis());
                scene = new Scene(h.getHistogram());
                break;

            case "Bar Chart":
                bar.setVisible(true);
                Bar b = new Bar(getTitleText(), getSeriesArray(), getCatArray(), getxAxis(), getyAxis());
                scene = new Scene(b.getBar());
                break;

            case "Line Chart":
                line.setVisible(true);
                Line l = new Line(getTitleText(), getSeriesArray(), getCatArray(), getxAxis(), getyAxis());
                scene = new Scene(l.getLine());
                break;

            case "Scatter Chart":
                scatter.setVisible(true);
                Scatter s = new Scatter(getTitleText(), getSeriesArray(), getCatArray(), getxAxis(), getyAxis());
                scene = new Scene(s.getScatter());
                break;

            case "Bubble Chart":
                bubble.setVisible(true);
                Bubble bu = new Bubble(getTitleText(), getSeriesArray(), getCatArray(), getxAxis(), getyAxis());
                scene = new Scene(bu.getBubble());
                break;
        }
        return scene;
    }

    public ShowChart(String selectedChoice, String titleText, CellRange dataRange, String xAxis, String yAxis, ArrayList<Category> catArray, ArrayList<Series> seriesArray) {
        setSelectedChoice(selectedChoice);
        setTitleText(titleText);
        setDataRange(dataRange);
        setxAxis(xAxis);
        setyAxis(yAxis);
        setCatArray(catArray);
        setSeriesArray(seriesArray);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowChart.fxml"));
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
        initChart();
        showChart();
    }
}
