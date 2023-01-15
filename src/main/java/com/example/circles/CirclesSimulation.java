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
        int n = 0;
        boolean check = true;

        try {
            n = Integer.parseInt(getParameters().getUnnamed().get(0));
            if (n <= 1 || n >= 20) {
                check = false;
                System.out.println("Wprowadzono błędne dane!");
                System.exit(0);
            }
        } catch (NumberFormatException e) {
            check = false;
            System.out.println("Wprowadzono błędne dane!");
            System.exit(0);
        }

        if (check) {
            BorderPane borderPane = new BorderPane();
            Scene scene = new Scene(borderPane, 800, 800);
            Track track = new Track(n, borderPane);
            stage.setTitle("Circles Simulation");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
            stage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}