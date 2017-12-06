package chart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class Bubble extends GenerateChart{
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private BubbleChart bubble;

    private ObservableList<XYChart.Series<Integer, Double>> listID  = FXCollections.observableArrayList();

    BubbleChart getBubble() {
        return bubble;
    }

    public Bubble() {
        setScatter();
    }

    public Bubble (String title, ArrayList<Series> seriesList, ArrayList<Category> catList) {
        setScatter();
        setChartTitle(title);
        generateChart (seriesList, catList, false);
        setListID(getListID());
        addSeries();
    }

    public Bubble (String xLabel, String yLabel) {
        setScatter();
        setXYLabel(xLabel, yLabel);
    }

    Bubble(String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
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
        bubble = new BubbleChart<>(xAxis, yAxis);
    }

    private void setChartTitle(String title) {
        bubble.setTitle (title);
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
            bubble.getData().add(aListSD);
        }
    }
}
