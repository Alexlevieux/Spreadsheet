package chart;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import java.util.ArrayList;

// TODO: 04-Dec-17 Fix bar, create histogram, scatter plot and line chart
public class Bar extends GenerateChart{
        private CategoryAxis xAxis;
        private NumberAxis yAxis;
        private BarChart bar;

    public BarChart getBar() {
        return bar;
    }

    public Bar () {
            setBar();
        }

        public Bar (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList) {
            setBar();
            setChartTitle(title);
            generateChart (seriesList, catList, true);
        }

        public Bar (String xLabel, String yLabel) {
            setBar();
            setXYLabel(xLabel, yLabel);
        }

        public Bar (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
            setBar();
            setChartTitle(title);
            setXYLabel(xLabel, yLabel);
            generateChart (seriesList, catList, true);
        }

        private void setBar() {
            xAxis = new CategoryAxis();
            yAxis = new NumberAxis();
            bar = new BarChart<>(xAxis, yAxis);
        }

        public void setChartTitle (String title) {
            bar.setTitle(title);
        }

    public void setXYLabel (String xLabel, String yLabel) {
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
    }
}
