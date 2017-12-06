package chart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import java.util.ArrayList;

public class Line extends GenerateChart{
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private LineChart line;

    private ObservableList<XYChart.Series<String, Double>> listSD  = FXCollections.observableArrayList();

    LineChart getLine() {
        return line;
    }

    public Line () {
        setLine();
    }

    public Line (String title, ArrayList<Series> seriesList, ArrayList<Category> catList) {
        setLine();
        setChartTitle(title);
        generateChart (seriesList, catList, true);
        setListSD(getListSD());
        addSeries();
    }

    public Line (String xLabel, String yLabel) {
        setLine();
        setXYLabel(xLabel, yLabel);
    }

    Line(String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
        setLine();
        setChartTitle(title);
        setXYLabel(xLabel, yLabel);
        generateChart (seriesList, catList, true);
        setListSD(getListSD());
        addSeries();
    }

    private void setLine() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        line = new LineChart<>(xAxis, yAxis);
    }

    private void setChartTitle(String title) {
        line.setTitle (title);
    }

    private void setXYLabel(String xLabel, String yLabel) {
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
    }

    private void setListSD(ObservableList<XYChart.Series<String, Double>> listSD) {
        this.listSD = listSD;
    }

    private void addSeries () {
        for (XYChart.Series<String, Double> aListSD : listSD) {
            line.getData().add(aListSD);
        }
    }
}
