package chart;

import javafx.scene.chart.*;

import java.util.ArrayList;

public class Area {
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private AreaChart area;
    private GenerateChart gc;

    public Area () {
        setArea();
    }

    public Area (String title, ArrayList<Series> seriesList, ArrayList<Category> catList) {
        setTitle(title);
        setArea();
        gc.generateChart (seriesList, catList, true);
    }

    public Area (String xLabel, String yLabel) {
        setArea();
        gc.setXYLabel(xLabel, yLabel, true);
    }

    public Area (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
        setTitle(title);
        setArea();
        gc.setXYLabel(xLabel, yLabel, true);
        gc.generateChart (seriesList, catList, true);
    }

    private void setArea() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        area = new AreaChart<>(xAxis, yAxis);
    }

    private void setTitle(String title) {
        area.setTitle(title);
    }
}
