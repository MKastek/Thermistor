package com.thermistor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class NewSeries extends Application
{

    String filePath,fileName;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage window =  primaryStage;
        GridPane gridPane = new GridPane();

        window.setWidth(400);
        window.setHeight(400);

        Label startLabel, sampleLabel, fileLabel;
        TextField startTextField, sampleTextField;
        Button fileButton = new Button("Choose file");
        Button graphButton = new Button("Graph");

        startLabel = new Label("Start time");
        sampleLabel = new Label("Sample");
        fileLabel = new Label("File");

        startTextField = new TextField();
        startTextField.setPromptText("Start Time");
        sampleTextField= new TextField();
        sampleTextField.setPromptText("Sample");

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(8);
        gridPane.setHgap(8);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(startLabel,1,0);
        gridPane.add(startTextField,2,0);
        gridPane.add(sampleLabel,1,1);
        gridPane.add(sampleTextField,2,1);

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "TEMP1",
                        "TEMP1 TEMP2",
                        "TEMP1 TEMP2 SOURCE"
                );
        final ComboBox comboBox = new ComboBox(options);
        comboBox.getSelectionModel().selectFirst();

        gridPane.add(new Label("File Type"),1,2);
        gridPane.add(comboBox,2,2);
        gridPane.add(fileLabel,1,3);
        gridPane.add(fileButton,2,3);
        gridPane.add(graphButton,2,4);




        fileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load");
            File selectedFile = fileChooser.showOpenDialog(window);
            filePath =selectedFile.getAbsolutePath();
            fileName = selectedFile.getName();
        });

        graphButton.setOnAction(event -> {
            switch (comboBox.getSelectionModel().getSelectedItem().toString())
            {
                case "TEMP1":
                    Data data = new Data();
                    data.setStartTime(startTextField.getText());
                    data.setSample(Integer.parseInt(sampleTextField.getText()));
                    data.setFilePath(filePath);
                    data.loadData(filePath);
                    Thermistor.addedFiles.setText("added files: \n"+fileName);

                break;

                case "TEMP1 TEMP2":


                break;

                case "TEMP1 TEMP2 SOURCE":


                break;
            }
        });




        Scene scene = new Scene(gridPane);
        // Add the Scene to the Stage
        primaryStage.setScene(scene);
        // Set the Title of the Stage
        primaryStage.setTitle("New Series");
        // Display the Stage
        primaryStage.show();

    }
}
