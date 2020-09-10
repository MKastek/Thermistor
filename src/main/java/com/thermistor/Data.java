package com.thermistor;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Data {
    String filePath;
    ArrayList<Double> dataList=new ArrayList<Double>();
    XYChart.Series<Number, Number> series= new XYChart.Series<>();;
    String startTime;
    int sample;
    String fileName;

    public Data()
    {

    }

    public Data(String filePath, ArrayList<Double> dataList, XYChart.Series<Number, Number> series, String startTime, int sample) {
        this.filePath = filePath;
        this.dataList = dataList;
        this.series = series;
        this.startTime = startTime;
        this.sample = sample;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Double> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<Double> dataList) {
        this.dataList = dataList;
    }

    public XYChart.Series<Number, Number> getSeries() {
        return series;
    }

    public void setSeries(XYChart.Series<Number, Number> series) {
        this.series = series;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getSample() {
        return sample;
    }

    public void setSample(int sample) {
        this.sample = sample;
    }

    public void loadData(String file){
        Scanner scanner = null;
        try {
             scanner = new Scanner(new File(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner.nextLine();
        while (scanner.hasNext()) {
            dataList.add(Double.valueOf(scanner.next()));
        }
        scanner.close();

        for (int i = 0; i < dataList.size(); i += sample) {
            XYChart.Data data = new XYChart.Data(i, dataList.get(i));
            series.getData().add(data);
        }

        NumberAxis xaxis = (NumberAxis) Thermistor.chart.getXAxis();
        xaxis.setLowerBound(0);
        xaxis.setTickUnit(300);
        xaxis.setUpperBound(dataList.size());

        NumberAxis yaxis = (NumberAxis) Thermistor.chart.getYAxis();
        yaxis.setLowerBound(minTemperature(dataList)-1);
        yaxis.setTickUnit(1);
        yaxis.setUpperBound(maxTemperature(dataList)+1);

        Thermistor.chart.getData().addAll(series);

        for (int i = 0; i < series.getData().size(); i ++) {
            Number x = series.getData().get(i).getXValue();
            Number y = series.getData().get(i).getYValue();
            series.getData().get(i).getNode().setOnMouseClicked(event -> {
                Thermistor.time.setText("Time: " + x);
                Thermistor.temp.setText("Temperature: " + y);
            });
        }

    }
    double minTemperature(ArrayList<Double> data)
    {
        double min=data.get(0);

        for(int i=0; i<data.size(); i++)
        {
            if(data.get(i)<min) min=data.get(i);
        }
        return min;
    }

    double maxTemperature(ArrayList<Double> data)
    {
        double max=data.get(0);
        for(int i=0; i<data.size(); i++)
        {
            if(data.get(i)>max) max=data.get(i);
        }
        return max;
    }
}
