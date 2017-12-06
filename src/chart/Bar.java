package chart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class Bar extends GenerateChart{
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarChart bar;

    private ObservableList<XYChart.Series<String, Double>> listSD  = FXCollections.observableArrayList();

    BarChart getBar() {
        return bar;
    }

    public Bar () {
            setBar();
        }

        public Bar (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList) {
            setBar();
            setChartTitle(title);
            generateChart (seriesList, catList, true);
            setListSD(getListSD());
            addSeries();
        }

        public Bar (String xLabel, String yLabel) {
            setBar();
            setXYLabel(xLabel, yLabel);
        }

        Bar(String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
            setBar();
            setChartTitle(title);
            setXYLabel(xLabel, yLabel);
            generateChart (seriesList, catList, true);
            setListSD(getListSD());
            addSeries();
        }

        private void setBar() {
            xAxis = new CategoryAxis();
            yAxis = new NumberAxis();
            bar = new BarChart<>(xAxis, yAxis);
        }

    private void setChartTitle(String title) {
            bar.setTitle(title);
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
            bar.getData().add(aListSD);
        }
    }
}