package chart;

import javafx.fxml.FXML;
import javafx.scene.chart.*;

public class ShowChart {
    @FXML private BarChart bar;
    @FXML private AreaChart area;
    @FXML private BubbleChart bubble;
    @FXML private LineChart line;
    @FXML private ScatterChart scatter;
    @FXML private PieChart pie;

    private ChartArea ca;

    ShowChart() {
        initChart();
        showChart();
    }

    public void initChart() {
        bar.setVisible(false);
        area.setVisible(false);
        bubble.setVisible(false);
        line.setVisible(false);
        scatter.setVisible(false);
        pie.setVisible(false);
    }

    public void showChart() {
        switch (ca.getSelectedChoice()) {
            case "Histogram":
                bar.setVisible(true);
                Histogram h = new Histogram(ca.getTitleText(), ca.getDataRange(), ca.getxAxis(), ca.getyAxis());
                break;

            case "Bar Chart":
                bar.setVisible(true);
                Bar b = new Bar(ca.getTitleText(), ca.getSeriesArray(), ca.getCatArray(), ca.getxAxis(), ca.getyAxis());
                break;

            case "Line Chart":
                line.setVisible(true);
                Line l = new Line(ca.getTitleText(), ca.getSeriesArray(), ca.getCatArray(), ca.getxAxis(), ca.getyAxis());
                break;

            case "Scatter Chart":
                scatter.setVisible(true);
                Scatter s = new Scatter(ca.getTitleText(), ca.getSeriesArray(), ca.getCatArray(), ca.getxAxis(), ca.getyAxis());
                break;

            case "Bubble Chart":
                bubble.setVisible(true);
                Bubble bu = new Bubble(ca.getTitleText(), ca.getSeriesArray(), ca.getCatArray(), ca.getxAxis(), ca.getyAxis());
                break;

            case "Pie Chart":
                pie.setVisible(true);
                //edit pie chart here
                break;
        }
    }

}
