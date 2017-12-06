package chart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import value.ComparableValue;
import value.NumberValue;

import java.util.ArrayList;

class GenerateChart {
    private XYChart.Series<String, Double> seriesSD;
    private XYChart.Series<Integer, Double> seriesID;

    private ObservableList<XYChart.Series<String, Double>> listSD  = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<Integer, Double>> listID  = FXCollections.observableArrayList();

    ObservableList<XYChart.Series<Integer, Double>> getListID() {
        return listID;
    }

    ObservableList<XYChart.Series<String, Double>> getListSD() {
        return listSD;
    }

    void generateChart(ArrayList<Series> seriesList, ArrayList<Category> catList, boolean SD) {
        //SD is a boolean to check if the input is (String, Double) or (Integer, Double)
        listID.clear();
        listSD.clear();
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
                listSD.add(seriesSD);
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
                listID.add(seriesID);
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
