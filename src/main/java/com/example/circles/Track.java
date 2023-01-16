package com.example.circles;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;
import java.util.Random;

public class Track {
    Random rand;
    MyCircle[] circles;
    final int NUM_OF_CIRCLES;
    private double[] thetas;

    public Track(int num, BorderPane borderPane) {
        Circle track = new Circle(400, 400, 350);
        track.setFill(null);
        track.setStroke(Color.BLACK);
        borderPane.getChildren().add(track);

        this.rand = new Random();
        this.NUM_OF_CIRCLES = num;
        this.circles = new MyCircle[num];
        generateThetas();
        createCircles(borderPane);
    }

    private void generateThetas() {
        thetas = new double[NUM_OF_CIRCLES];
        for (int i = 0; i < NUM_OF_CIRCLES; i++) {
            thetas[i] = rand.nextDouble() * 2 * Math.PI;
            while (primaryCollision(thetas[i])) {
                thetas[i] = rand.nextDouble() * 2 * Math.PI;
            }
        }
        Arrays.sort(thetas);
    }

    private void createCircles(BorderPane borderPane) {
        for (int i = 0; i < NUM_OF_CIRCLES; i++) {
            circles[i] = new MyCircle(i, this, thetas[i], rand, borderPane);
        }
        circles[NUM_OF_CIRCLES - 1].setPredecessor(circles[0]);
        for (int i = 0; i < NUM_OF_CIRCLES - 1; i++) {
            circles[i].setPredecessor(circles[i + 1]);
        }
        borderPane.getChildren().addAll(circles);
    }

    private boolean primaryCollision(double theta0) {
        Circle tmp = new Circle(400 + 350 * Math.cos(theta0), 400 + 350 * Math.sin(theta0), 20);
        for (MyCircle i : circles) {
            if (i != null) {
                if (tmp.intersects(i.getBoundsInLocal()))
                    return true;
            }
        }
        return false;
    }
}