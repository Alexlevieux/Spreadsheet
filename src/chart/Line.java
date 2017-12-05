package chart;

import javafx.scene.chart.*;
import java.util.ArrayList;

public class Line {
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private LineChart line;
    private GenerateChart gc;

    public Line () {
        setLine();
    }

    public Line (String title, ArrayList<Series> seriesList, ArrayList<Category> catList) {
        setTitle(title);
        setLine();
        gc.generateChart (seriesList, catList, true);
    }

    public Line (String xLabel, String yLabel) {
        setLine();
        gc.setXYLabel(xLabel, yLabel, true);
    }

    public Line (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
        setTitle(title);
        setLine();
        gc.setXYLabel(xLabel, yLabel, true);
        gc.generateChart (seriesList, catList, true);
    }

    private void setLine() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        line = new LineChart<>(xAxis, yAxis);
    }

    public void setTitle (String title) {
        line.setTitle (title);
    }
}
