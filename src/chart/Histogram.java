package chart;

import function.CellRange;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import value.ComparableValue;
import value.NumberValue;
import value.Value;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Histogram {
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarChart histogram;
    private XYChart.Series<String, Integer> series = new XYChart.Series<>();
    private ChartArea ca;
    private CellRange data;
    private ArrayList<Double> dataList = new ArrayList<>();
    private DecimalFormat df = new DecimalFormat("#.00");

    public Histogram () {
        setHistogram();
    }

    public Histogram (String title, CellRange dataRange) {
        setHistogram();
        setChartTitle(title);
        setData(dataRange);
        generateHistogram ();
    }

    public Histogram (String xLabel, String yLabel) {
        setHistogram();
        setXYLabel(xLabel, yLabel);
    }

    public Histogram (String title, CellRange dataRange, String xLabel) {
        setHistogram();
        setChartTitle(title);
        setData(dataRange);
        setXYLabel(xLabel, "Frequency");
        generateHistogram ();
    }

    public BarChart getHistogram() {
        return histogram;
    }

    public void setData(CellRange data) {
        this.data = data;
    }

    private void generateHistogram () {
        listData();
        createBins();
        histogram.getData().add(series);
    }

    private void setHistogram() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        histogram = new BarChart<>(xAxis, yAxis);
        histogram.setCategoryGap(0);
        histogram.setBarGap(0);
        histogram.setLegendVisible(false);
    }

    public void setXYLabel (String xLabel, String yLabel) {
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
    }

    public void setChartTitle (String title) {
        histogram.setTitle (title);
    }

    private void addData (String category, int value) {
        series.getData().add(new XYChart.Data<>(category, value));
    }

    private void listData () {
        if (dataList.isEmpty()) {
            for (int i=0; i<data.getSize(); i++) {
                ComparableValue temp = data.getValue().get(i);
                if (temp instanceof NumberValue) {
                    dataList.add(((NumberValue) temp).getValue());
                }
            }
        }
    }

    private void createBins () {
        Collections.sort(dataList);
        Double bins = Math.ceil (1 + (3.322)*Math.log(dataList.size()));
        Double gap = Math.ceil(dataList.get(dataList.size()-1)) - Math.floor(dataList.get(0));
        Double binsSize = gap/bins;
        Double start  = dataList.get(0);
        Double end = start+binsSize;

        for (int i = 0; i<bins; i++) {
            addData((df.format(start)) + "-" + (df.format(end)), countFreq(start, end));
            start = end;
            if (i + 2 == bins) end = dataList.get(dataList.size()-1);
            else end = start+binsSize;
        }
    }

    private int binarySearchForLeftRange(Double left_range)
    {
        int len = dataList.size()-1;
        if (dataList.get(len) < left_range)
            return -1;

        int low = 0;
        int high = len-1;

        while (low<=high)
        {
            int mid = low+((high-low)/2);

            if(dataList.get(mid) >= left_range)
                high = mid-1;
            else
                low = mid+1;
        }

        return high+1;
    }

    private int binarySearchForRightRange(Double right_range)
    {
        int len = dataList.size()-1;
        if (dataList.get(0) > right_range)
            return -1;

        int low = 0;
        int high = len-1;

        while (low<=high)
        {
            int mid = low+((high-low)/2);

            if(dataList.get(mid) > right_range)
                high = mid-1;
            else
                low = mid+1;
        }

        return low-1;
    }

    private int countFreq (Double left_range, Double right_range) {
        int index_left = binarySearchForLeftRange(left_range);
        int index_right = binarySearchForRightRange(right_range);
        int count;

        if (index_left==-1 || index_right==-1 || index_left>index_right)
            count = 0;
        else
            count = index_right-index_left+1;

        return count;
    }
}
