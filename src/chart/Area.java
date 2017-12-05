package chart;

import javafx.scene.chart.*;
import value.ComparableValue;
import value.NumberValue;

import java.util.ArrayList;

public class Area extends GenerateChart {
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private AreaChart area;

    public AreaChart getArea() {
        return area;
    }

    public Area () {
        setArea();
    }

    public Area (String title, ArrayList<Series> seriesList, ArrayList<Category> catList) {
        setArea();
        setChartTitle(title);
        generateChart (seriesList, catList, true);
    }

    public Area (String xLabel, String yLabel) {
        setArea();
        setXYLabel(xLabel, yLabel);
    }

    public Area (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
        setArea();
        setChartTitle(title);
        setXYLabel(xLabel, yLabel);
        generateChart (seriesList, catList, true);
    }

    private void setArea() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        area = new AreaChart<>(xAxis, yAxis);
    }

    private void setChartTitle(String title) {
        area.setTitle(title);
    }

    public void setXYLabel (String xLabel, String yLabel) {
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
    }
}
