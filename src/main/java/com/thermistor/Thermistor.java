package com.thermistor;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Thermistor extends Application {

    ArrayList<Double> list =new ArrayList<Double>();
    final XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
    static LineChart<Number, Number> chart = new LineChart<>( new NumberAxis(), new NumberAxis());

    //Gui elements

    Button newSeries,setButon;
    static Label addedFiles, xlim, ylim;
    TextField xMin;
    TextField xMax;
    TextField yMin;
    TextField yMax;
    static TextField time;
    static TextField temp;

    @Override
    public void start(Stage stage) throws ParseException, FileNotFoundException {
        Stage window = stage;
        window.setTitle("Thermistor graph");
        BorderPane borderPane = new BorderPane();

        time = new TextField("Time: ");
        temp = new TextField("Temperatura: ");

        VBox leftMenu = new VBox(5);
        leftMenu.setAlignment(Pos.CENTER);
        leftMenu.setSpacing(50);
        leftMenu.setPadding(new Insets(2, 2, 2, 2));

        VBox rightMenu = new VBox(5);
        rightMenu.setAlignment(Pos.CENTER);
        rightMenu.setSpacing(10);
        rightMenu.setPadding(new Insets(2, 2, 2, 2));

        addedFiles = new Label("Added files:" +"\n");

        Long startTime=  System.currentTimeMillis();

        newSeries = new Button("New Series");
        NumberAxis yaxis = (NumberAxis)chart.getYAxis();
        yaxis.setLowerBound(15);
        yaxis.setUpperBound(25);
        NumberAxis xaxis = (NumberAxis)chart.getXAxis();

        newSeries.setAlignment(Pos.CENTER);
        newSeries.setOnAction(event -> {
            Stage Stage = new Stage();
            NewSeries newSeries = new  NewSeries();
            try {
                newSeries.start(Stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Stage.show();

            /*
            NumberAxis numberAxis  =(NumberAxis) chart.getXAxis();
            numberAxis.setTickLabelFormatter(new StringConverter<Number>() {
                private final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                @Override
                public String toString(Number object) {
                    return format.format(new Date(startTime+object.longValue()*1000L));
                }
                @Override
                public Number fromString(String string) {
                    return null;
                }
            });

            for (int i = 0; i < list.size(); i += 60) {
                XYChart.Data data = new XYChart.Data(i, list.get(i));
                series1.getData().add(data);
                data.getNode().setOnMouseClicked(e ->
                        {
                            Date date = new Date( startTime+Long.parseLong(data.getXValue().toString())*1000L);
                            time.setText("Time: " + new SimpleDateFormat("HH:mm").format(date) );

                            temp.setText("Temperature: " + data.getYValue());
                        }
                );
            }
             */
        });

        xlim = new Label("x limits: \n");
        xMin = new TextField();
        xMin.setPromptText("x min:");
        xMax = new TextField();
        xMax.setPromptText("x max:");

        ylim = new Label("y limits: \n");
        yMin = new TextField();
        yMin.setPromptText("y min:");
        yMax = new TextField();
        yMax.setPromptText("y max:");

        chart.getXAxis().setAnimated(false);
        chart.getYAxis().setAnimated(false);
        chart.getYAxis().setAutoRanging(false);
        chart.getXAxis().setAutoRanging(false);
        chart.getXAxis().setTickLength(1);
        chart.setVerticalGridLinesVisible(false);
        chart.setHorizontalGridLinesVisible(true);

        setButon = new Button("Set");
        setButon.setOnAction(event -> {
            NumberAxis yAxis = (NumberAxis)chart.getYAxis();
            if(yMin!=null && yMax!=null) {
                yAxis.setLowerBound(Double.parseDouble(yMin.getText()));
                yAxis.setUpperBound(Double.parseDouble(yMax.getText()));
            }
            NumberAxis xAxis = (NumberAxis)chart.getXAxis();
            if(xMin!=null && xMax!=null) {
                xAxis.setLowerBound(Double.parseDouble(xMin.getText()));
                xAxis.setUpperBound(Double.parseDouble(xMax.getText()));
            }
        });
        leftMenu.getChildren().addAll( addedFiles,newSeries);
        rightMenu.getChildren().addAll(time, temp, xlim,xMin,xMax, ylim,yMin,yMax,setButon);

        borderPane.setRight(rightMenu);
        borderPane.setLeft(leftMenu);
        MenuBar menubar = new MenuBar();
        Menu FileMenu = new Menu("File");
        MenuItem filemenu1 = new MenuItem("Load");
        filemenu1.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load");
            File selectedFile = fileChooser.showOpenDialog(window);
            Scanner s = null;
            list.clear();
            try {
                s = new Scanner(new File(selectedFile.getAbsolutePath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            s.nextLine();
            while (s.hasNext()) {
                list.add(Double.valueOf(s.next()));
            }
            s.close();
            xaxis.setLowerBound(0);
            xaxis.setTickUnit(180);
            xaxis.setUpperBound(list.size());

        });
        MenuItem filemenu2 = new MenuItem("Save");
        borderPane.setTop(menubar);
        FileMenu.getItems().addAll(filemenu1, filemenu2);
        menubar.getMenus().addAll(FileMenu);

        borderPane.setCenter(chart);
        chart.setLegendVisible(true);
        chart.setTitle("Thermistor");




        //chart.getData().addAll(series1);
        window.setScene(new Scene(borderPane, 1250, 680));
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}