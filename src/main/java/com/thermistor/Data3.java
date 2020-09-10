package com.thermistor;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class Data3 extends Data2 {


    ArrayList<Double> dataList3;
    XYChart.Series<String, Number> series3;

    public Data3(String filePath, ArrayList<Double> dataList, XYChart.Series<Number, Number> series, String startTime, int sample, ArrayList<Double> dataList2, XYChart.Series<Number, Number> series2,ArrayList<Double> dataList3, XYChart.Series<String, Number> series3) {
        super(filePath, dataList, series, startTime, sample, dataList2, series2);
        this.dataList3= dataList3;
        this.series3=series3;
    }

}
