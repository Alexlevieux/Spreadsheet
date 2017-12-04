package chart;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import main.Cell;

import java.util.ArrayList;

// TODO: 04-Dec-17 Fix bar, create histogram, scatter plot and line chart
public class Bar {
        private CategoryAxis xAxis;
        private NumberAxis yAxis;
        private BarChart bar;
        private Series series;
        private ChartArea ca;

        public Bar () {
            setBar();
        }

        public Bar (ArrayList<chart.Series> seriesList, ArrayList<Category> catList) {
            setBar();
            generateBar (seriesList, catList);
        }

        public Bar (String xLabel, String yLabel) {
            setBar();
            xAxis.setLabel(xLabel);
            yAxis.setLabel(yLabel);
        }

        public Bar (ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
            setBar();
            xAxis.setLabel(xLabel);
            yAxis.setLabel(yLabel);
            generateBar (seriesList, catList);
        }

        public void generateBar (ArrayList<chart.Series> seriesList, ArrayList<Category> catList) {

        }

        private void setBar() {
            xAxis = new CategoryAxis();
            yAxis = new NumberAxis();
            bar = new BarChart(xAxis, yAxis);
        }

        public void setTitle (String title) {
            bar.setTitle (title);
        }

        public void setSeriesName (String name) {
            series.setName(name);
        }

        public void addData (String category, Cell value) {
            series.getData().add(new XYChart.Data(category, value));
        }
}
