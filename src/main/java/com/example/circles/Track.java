package com.example.circles;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

public class Track {
    MyCircle[] circles;
    final int NUM_OF_CIRCLES;

    public Track(int num, BorderPane borderPane) {
        NUM_OF_CIRCLES = num;
        circles = new MyCircle[num];
        createCircles(borderPane);
    }

    private void createCircles(BorderPane borderPane) {
        for (int i = 0; i < NUM_OF_CIRCLES; i++) {
            circles[i] = new MyCircle(i, this);
            while(primaryCollision(i)) {
                circles[i] = new MyCircle(i, this);
            }
            borderPane.getChildren().add(circles[i]);
        }
    }

    public boolean isCollision(final int id, double x, double y) {
        Circle tmpCircle = new Circle(x, y, 20);
        for (int i = 0; i < NUM_OF_CIRCLES; i++) {
            if (i != id && tmpCircle.intersects(circles[i].getBoundsInLocal())) {
                return true;
            }
        }
        return false;
    }

    private boolean primaryCollision(final int num) {
        for (int i = 0; i < num; i++) {
            if (circles[num].intersects(circles[i].getBoundsInLocal())) {
                return true;
            }
        }
        return false;
    }

}