package chart;

import javafx.scene.chart.*;

import java.util.ArrayList;

public class Scatter extends GenerateChart{
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private ScatterChart scatter;

    public ScatterChart getScatter() {
        return scatter;
    }

    public Scatter() {
        setScatter();
    }

    public Scatter (String title, ArrayList<Series> seriesList, ArrayList<Category> catList) {
        setScatter();
        setChartTitle(title);
        generateChart (seriesList, catList, false);
    }

    public Scatter (String xLabel, String yLabel) {
        setScatter();
        setXYLabel(xLabel, yLabel);
    }

    public Scatter (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
        setScatter();
        setChartTitle(title);
        setXYLabel(xLabel, yLabel);
        generateChart (seriesList, catList, false);
    }

    private void setScatter() {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        scatter = new ScatterChart<>(xAxis, yAxis);
    }

    public void setChartTitle (String title) {
        scatter.setTitle (title);
    }

    public void setXYLabel (String xLabel, String yLabel) {
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
    }
}
