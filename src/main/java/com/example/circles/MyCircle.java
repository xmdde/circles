package com.example.circles;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

public class MyCircle extends Circle implements Runnable {
    Random rand;
    final Track track;
    final int ID;
    private double theta0; //kat poczatkowy
    private double omega; //predkosc katowa czesc sprawdzam czy jestem

    MyCircle(int id, Track track) {
        rand = new Random();
        this.setRadius(20);
        this.ID = id;
        this.track = track;
        this.omega = rand.nextDouble() * 2;
        System.out.println(omega);
        setPrimaryPos();
        this.setStroke(Color.BLACK);
        this.setFill(Color.BLANCHEDALMOND);
        Thread thread = new Thread(this);
        thread.start();
    }

    private void setPrimaryPos() {
        double factor = rand.nextDouble();
        theta0 = factor * 2 * Math.PI;
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
        while (true) {
            try {
                Thread.sleep(50);
                synchronized (track) {
                    double x = newX(i);
                    double y = newY(i);
                    if (track.isCollision(ID, x, y)) {
                        theta0 = omega * i + theta0;
                        omega = 0;
                        updatePos(x, y);
                    }
                    else updatePos(x, y);
                }
                i += 0.05;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
