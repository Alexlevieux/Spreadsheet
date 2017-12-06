package chart;

import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import value.ComparableValue;
import value.NumberValue;

import java.util.ArrayList;

public class Area extends GenerateChart {
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private AreaChart area;

    ObservableList<XYChart.Series<String, Double>> listSD;

    AreaChart getArea() {
        return area;
    }

    public Area () {
        setArea();
    }

    public Area (String title, ArrayList<Series> seriesList, ArrayList<Category> catList) {
        setArea();
        setChartTitle(title);
        generateChart (seriesList, catList, true);
        setListSD(getListSD());
        addSeries();
    }

    public Area (String xLabel, String yLabel) {
        setArea();
        setXYLabel(xLabel, yLabel);
    }

    Area(String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
        setArea();
        setChartTitle(title);
        setXYLabel(xLabel, yLabel);
        generateChart (seriesList, catList, true);
        setListSD(getListSD());
        addSeries();
    }

    private void setArea() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        area = new AreaChart<>(xAxis, yAxis);
    }

    private void setChartTitle(String title) {
        area.setTitle(title);
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
            area.getData().add(aListSD);
        }
    }
}
