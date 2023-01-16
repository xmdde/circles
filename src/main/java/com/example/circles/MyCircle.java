package com.example.circles;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

public class MyCircle extends Circle implements Runnable {
    final BorderPane borderPane;
    final Random rand;
    final Track track;
    final int ID;
    private double theta0; //kat poczatkowy
    private double omega; //predkosc katowa
    private MyCircle predecessor;

    MyCircle(int id, Track track, double theta0, Random rand, BorderPane borderPane) {
        this.borderPane = borderPane;
        this.rand = rand;
        this.setRadius(20);
        this.ID = id;
        this.track = track;
        this.omega = rand.nextDouble();
        this.theta0 = theta0;
        this.setStroke(Color.BLACK);
        setRandomColor();
        setPrimaryPos();
        Thread thread = new Thread(this);
        thread.start();
    }

    private void setRandomColor() {
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color color = new Color(r, g, b,1.0);
        this.setFill(color);
    }

    public void setPredecessor(MyCircle predecessor) {
        this.predecessor = predecessor;
    }

    private void setPrimaryPos() {
        this.setCenterX(400 + 350 * Math.cos(theta0));
        this.setCenterY(400 + 350 * Math.sin(theta0));
    }

    private void updatePos(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
    }

    private double newX(double time) {
        return 400 + 350 * Math.cos(omega * time + theta0);
    }

    private double newY(double time) {
        return 400 + 350 * Math.sin(omega * time + theta0);
    }

    public double getTheta0() {
        return theta0;
    }

    public double getOmega() {
        return omega;
    }

    @Override
    public void run() {
        double i = 0.0;
        long time = 50;
        while (true) {
            try {
                Thread.sleep(time);
                double x = newX(i);
                double y = newY(i);
                Circle tmp = new Circle(x, y, 20);

                synchronized (track) {
                    if (!tmp.intersects(predecessor.getBoundsInLocal())) { //this...
                        updatePos(x, y);
                    }
                    else {
                        omega = predecessor.getOmega();
                        theta0 = predecessor.getTheta0() - 0.12;
                        updatePos(newX(i), newY(i));
                    }
                }
                i += time / 1000.0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
