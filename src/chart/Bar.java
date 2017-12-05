package chart;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import java.util.ArrayList;

// TODO: 04-Dec-17 Fix bar, create histogram, scatter plot and line chart
public class Bar {
        private CategoryAxis xAxis;
        private NumberAxis yAxis;
        private BarChart bar;
        private GenerateChart gc;

        public Bar () {
            setBar();
        }

        public Bar (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList) {
            setTitle(title);
            setBar();
            gc.generateChart (seriesList, catList, true);
        }

        public Bar (String xLabel, String yLabel) {
            setBar();
            gc.setXYLabel(xLabel, yLabel, true);
        }

        public Bar (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
            setTitle(title);
            setBar();
            gc.setXYLabel(xLabel, yLabel, true);
            gc.generateChart (seriesList, catList, true);
        }

        private void setBar() {
            xAxis = new CategoryAxis();
            yAxis = new NumberAxis();
            bar = new BarChart<>(xAxis, yAxis);
        }

        public void setTitle (String title) {
            bar.setTitle (title);
        }
}
