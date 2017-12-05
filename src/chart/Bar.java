package chart;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import main.Cell;
import value.ComparableValue;
import value.NumberValue;

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

        public Bar (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList) {
            setTitle(title);
            setBar();
            generateBar (seriesList, catList);
        }

        public Bar (String xLabel, String yLabel) {
            setBar();
            setXYLabel(xLabel, yLabel);
        }

        public Bar (String title, ArrayList<chart.Series> seriesList, ArrayList<Category> catList, String xLabel, String yLabel) {
            setTitle(title);
            setBar();
            setXYLabel(xLabel, yLabel);
            generateBar (seriesList, catList);
        }

        private void generateBar (ArrayList<chart.Series> seriesList, ArrayList<Category> catList) {
            for (chart.Series aSeriesList : seriesList) {
                series = new Series<String, Double>();
                setSeriesName(aSeriesList.getName());
                for (int j = 0; j < catList.size(); j++) {
                    ComparableValue temp = aSeriesList.getValues().getValue().get(j);
                    if (temp instanceof NumberValue) {
                        addData(catList.get(j).getName(), ((NumberValue) temp).getValue());
                    }


                }
            }
        }

        private void setBar() {
            xAxis = new CategoryAxis();
            yAxis = new NumberAxis();
            bar = new BarChart<>(xAxis, yAxis);
        }

        public void setXYLabel (String xLabel, String yLabel) {
            xAxis.setLabel(xLabel);
            yAxis.setLabel(yLabel);
        }

        public void setTitle (String title) {
            bar.setTitle (title);
        }

        private void setSeriesName (String name) { series.setName(name); }

        private void addData (String category, Double value) {
            series.getData().add(new XYChart.Data<>(category, value));
        }
}
