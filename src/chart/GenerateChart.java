package chart;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import value.ComparableValue;
import value.NumberValue;

import java.util.ArrayList;

public class GenerateChart {
    private XYChart.Series<String, Double> seriesSD;
    private XYChart.Series<Integer, Double> seriesID;

    public void generateChart (ArrayList<Series> seriesList, ArrayList<Category> catList, boolean SD) {
        //SD is a boolean to check if the input is (String, Double) or (Integer, Double)
        if (SD) {
            for (chart.Series aSeriesList : seriesList) {
                seriesSD = new XYChart.Series<>();
                setSeriesName(aSeriesList.getName(), SD);
                for (int j = 0; j < catList.size(); j++) {
                    ComparableValue temp = aSeriesList.getValues().getValue().get(j);
                    if (temp instanceof NumberValue) {
                        addDataSD(catList.get(j).getName(), ((NumberValue) temp).getValue());
                    }
                }
            }
        }
        else {
            for (chart.Series aSeriesList : seriesList) {
                seriesID = new XYChart.Series<>();
                setSeriesName(aSeriesList.getName(), SD);
                for (int j = 0; j < catList.size(); j++) {
                    ComparableValue temp = aSeriesList.getValues().getValue().get(j);
                    if (temp instanceof NumberValue) {
                        addDataID(Integer.valueOf(catList.get(j).getName()), ((NumberValue) temp).getValue());
                    }
                }
            }
        }
    }

    private void setSeriesName (String name, boolean SD) {
        if(SD) seriesSD.setName(name);
        else seriesID.setName(name);
    }

    private void addDataSD (String category, Double value) {
        seriesSD.getData().add(new XYChart.Data<>(category, value));
    }

    private void addDataID (Integer category, Double value) {
        seriesID.getData().add(new XYChart.Data<>(category, value));
    }

}
