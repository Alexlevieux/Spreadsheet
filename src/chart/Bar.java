package chart;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import main.Cell;

import java.util.ArrayList;

public class Bar {
        private CategoryAxis xAxisC;
        private CategoryAxis yAxisC;
        private NumberAxis yAxisN;
        private NumberAxis xAxisN;
        private BarChart bar;
        private Series series;
        private boolean xc;
        private boolean yc;

        public Bar () {
            setBar();
        }

        public Bar (ArrayList<Cell> seriesName, ArrayList<Cell> cellCategory, ArrayList<Cell> cellData) {
            setBar();
            generateChart (seriesName, cellCategory, cellData);
        }

        public Bar (String xLabel, String yLabel) {
            setBar();
            setXLabel(xLabel);
            setYLabel(yLabel);
        }

        public void generateChart (ArrayList<Cell> seriesName, ArrayList<Cell> cellCategory, ArrayList<Cell> cellData) {
            for (int i = 0; i<seriesName.size(); i++) {
                series = new Series();
                setSeriesName(seriesName.get(i).toString());
                for (Cell aCellCategory : cellCategory) {
                    addData(aCellCategory.toString(), cellData.get(i));
                }
            }
        }

        private void setBar() {
            if (xc) xAxisC = new CategoryAxis();
            else xAxisN = new NumberAxis();

            if (yc) yAxisC = new CategoryAxis();
            else yAxisN = new NumberAxis();

            if (xc) {
                if (yc) bar = new BarChart<>(xAxisC, yAxisC);
                else bar = new BarChart<>(xAxisC, yAxisN);
            }
            else {
                if (yc) bar = new BarChart<>(xAxisN, yAxisC);
                else bar = new BarChart<>(xAxisN, yAxisN);
            }
        }

        public void setXC (boolean xc) {
            this.xc = xc;
        }

        public boolean getXC () {
            return xc;
        }

        public void setYC (boolean yc) {
            this.yc = yc;
        }

        public boolean getYC () {
            return yc;
        }

        public void setXLabel (String xLabel) {
            if (xc) xAxisC.setLabel(xLabel);
            else xAxisN.setLabel(xLabel);
        }

        public void setYLabel (String yLabel) {
            if (yc) yAxisC.setLabel(yLabel);
            else yAxisN.setLabel(yLabel);
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

//        public void seriesHeader ()
}
