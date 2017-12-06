package chart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;

import java.util.ArrayList;

public class Scatter extends GenerateChart{
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private ScatterChart scatter;

    private ObservableList<XYChart.Series<Integer, Double>> listID  = FXCollections.observableArrayList();

    ScatterChart getScatter() {
        return scatter;
    }

    public Scatter() {
        setScatter();
    }

    public Scatter (String title, ArrayList<Series> seriesList, ArrayList<Category> catList) {
        setScatter();
        setChartTitle(title);
        generateChart (seriesList, catList, false);
        setListID(getListID());
        addSeries();
    }

    public Scatter (String xLabel, String yLabel) {
        setScatter();
        setXYLabel(xLabel, yLabel);
    }

    Scatter(String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
        setScatter();
        setChartTitle(title);
        setXYLabel(xLabel, yLabel);
        generateChart (seriesList, catList, false);
        setListID(getListID());
        addSeries();
    }

    private void setScatter() {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        scatter = new ScatterChart<>(xAxis, yAxis);
    }

    private void setChartTitle(String title) {
        scatter.setTitle (title);
    }

    private void setXYLabel(String xLabel, String yLabel) {
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
    }

    private void setListID(ObservableList<XYChart.Series<Integer, Double>> listSD) {
        this.listID = listID;
    }

    private void addSeries () {
        for (XYChart.Series<Integer, Double> aListSD : listID) {
            scatter.getData().add(aListSD);
        }
    }
}
