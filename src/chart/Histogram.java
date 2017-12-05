package chart;

import function.CellRange;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Histogram {
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private BarChart histogram;
    private XYChart.Series series;
    private ChartArea ca;
    private CellRange data;

    public Histogram () {
        setHistogram();
    }

    public Histogram (String title, CellRange dataRange) {
        setTitle(title);
        setHistogram();
        setData(dataRange);
        generateHistogram ();
    }

    public Histogram (String xLabel, String yLabel) {
        setHistogram();
        setXYLabel(xLabel, yLabel);
    }

    public Histogram (String title, CellRange dataRange, String xLabel, String yLabel) {
        setTitle(title);
        setHistogram();
        setData(dataRange);
        setXYLabel(xLabel, yLabel);
        generateHistogram ();
    }

    public void setData(CellRange data) {
        this.data = data;
    }

    private void generateHistogram () {


    }

    private void setHistogram() {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        histogram = new BarChart<>(xAxis, yAxis);
        histogram.setCategoryGap(0);
        histogram.setBarGap(0);
    }

    public void setXYLabel (String xLabel, String yLabel) {
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
    }

    public void setTitle (String title) {
        histogram.setTitle (title);
    }

    private void addData (String category, Double value) {
        series.getData().add(new XYChart.Data<>(category, value));
    }
}
