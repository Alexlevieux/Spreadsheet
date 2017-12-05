package chart;

import javafx.scene.chart.*;
import java.util.ArrayList;

public class Line extends GenerateChart{
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private LineChart line;

    public LineChart getLine() {
        return line;
    }

    public Line () {
        setLine();
    }

    public Line (String title, ArrayList<Series> seriesList, ArrayList<Category> catList) {
        setLine();
        setChartTitle(title);
        generateChart (seriesList, catList, true);
    }

    public Line (String xLabel, String yLabel) {
        setLine();
        setXYLabel(xLabel, yLabel);
    }

    public Line (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
        setLine();
        setChartTitle(title);
        setXYLabel(xLabel, yLabel);
        generateChart (seriesList, catList, true);
    }

    private void setLine() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        line = new LineChart<>(xAxis, yAxis);
    }

    public void setChartTitle (String title) {
        line.setTitle (title);
    }

    public void setXYLabel (String xLabel, String yLabel) {
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
    }
}
