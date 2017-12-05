package chart;

import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class Bubble {
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private BubbleChart bubble;
    private GenerateChart gc;

    public Bubble() {
        setScatter();
    }

    public Bubble (String title, ArrayList<Series> seriesList, ArrayList<Category> catList) {
        setTitle(title);
        setScatter();
        gc.generateChart (seriesList, catList, false);
    }

    public Bubble (String xLabel, String yLabel) {
        setScatter();
        gc.setXYLabel(xLabel, yLabel, false);
    }

    public Bubble (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
        setTitle(title);
        setScatter();
        gc.setXYLabel(xLabel, yLabel, false);
        gc.generateChart (seriesList, catList, false);
    }

    private void setScatter() {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        bubble = new BubbleChart<>(xAxis, yAxis);
    }

    public void setTitle (String title) {
        bubble.setTitle (title);
    }
}
