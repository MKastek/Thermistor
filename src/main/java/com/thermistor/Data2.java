package com.thermistor;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class Data2 extends Data {

    ArrayList<Double> dataList2;
    XYChart.Series<Number, Number> series2;

    public Data2(String filePath, ArrayList<Double> dataList, XYChart.Series<Number, Number> series, String startTime, int sample,ArrayList<Double> dataList2,  XYChart.Series<Number, Number> series2) {
        super(filePath, dataList, series, startTime, sample);
        this.dataList2=dataList2;
        this.series2=series2;
    }


}
