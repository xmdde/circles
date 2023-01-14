package com.example.circles;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CirclesSimulation extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 800, 800);
        Track track = new Track(10, borderPane);

        stage.setTitle("Circles Simulation");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }

}