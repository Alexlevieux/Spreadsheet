package chart;

import javafx.scene.chart.*;
import value.ComparableValue;
import value.NumberValue;

import java.util.ArrayList;

public class Scatter {
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private ScatterChart scatter;
    private XYChart.Series<Integer, Double> series;

    public Scatter() {
        setScatter();
    }

    public Scatter (String title, ArrayList<Series> seriesList, ArrayList<Category> catList) {
        setTitle(title);
        setScatter();
        generateScatter (seriesList, catList);
    }

    public Scatter (String xLabel, String yLabel) {
        setScatter();
        setXYLabel(xLabel, yLabel);
    }

    public Scatter (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
        setTitle(title);
        setScatter();
        setXYLabel(xLabel, yLabel);
        generateScatter (seriesList, catList);
    }

    private void generateScatter (ArrayList<chart.Series> seriesList, ArrayList<Category> catList) {
        for (chart.Series aSeriesList : seriesList) {
            series = new XYChart.Series<Integer, Double>();
            setSeriesName(aSeriesList.getName());
            for (int j = 0; j < catList.size(); j++) {
                ComparableValue temp = aSeriesList.getValues().getValue().get(j);
                if (temp instanceof NumberValue) {
                    addData(Integer.valueOf(catList.get(j).getName()), ((NumberValue) temp).getValue());
                }


            }
        }
    }

    private void setScatter() {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        scatter = new ScatterChart<>(xAxis, yAxis);
    }

    public void setXYLabel (String xLabel, String yLabel) {
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
    }

    public void setTitle (String title) {
        scatter.setTitle (title);
    }

    private void setSeriesName (String name) { series.setName(name); }

    private void addData (Integer category, Double value) {
        series.getData().add(new XYChart.Data<>(category, value));
    }
}
