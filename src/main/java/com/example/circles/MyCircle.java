package com.example.circles;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;

/**
 * Klasa kół dziedzicząca po klasie Circle.
 */
public class MyCircle extends Circle implements Runnable {
    final BorderPane borderPane;
    final Random rand;
    final Track track;
    private double theta0; //kat poczatkowy
    private double omega; //predkosc katowa
    private MyCircle predecessor;

    /**
     * Konstruktor obiektów klasy MyCircle.
     * Dodaje parametry, losuje prędkość kątową, ustala pozycję początkową i kolor oraz rozpoczyna pracę wątku.
     * @param track obiekt klasy Track zawierającym dane koło
     * @param theta0 kąt początkowy z przedziału [0, 2PI]
     * @param rand obiekt Random generator liczb losowych
     * @param borderPane panel, na którym jest umieszczone koło
     */
    MyCircle(Track track, double theta0, Random rand, BorderPane borderPane) {
        this.borderPane = borderPane;
        this.rand = rand;
        this.setRadius(20);
        this.track = track;
        this.omega = rand.nextDouble();
        this.theta0 = theta0;
        this.setStroke(Color.BLACK);
        setRandomColor();
        setPrimaryPos();
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Ustala kolor koła na wylosowany.
     */
    private void setRandomColor() {
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color color = new Color(r, g, b,1.0);
        this.setFill(color);
    }

    /**
     * Ustawia poprzednika.
     * @param predecessor poprzednik - obiekt klasy MyCircle poruszający się bezpośrednio przed danym kołem.
     */
    public void setPredecessor(MyCircle predecessor) {
        this.predecessor = predecessor;
    }

    /**
     * Ustawia koło na odpowiedniej pozycji początkowej zależnej od kąta początkowego.
     */
    private void setPrimaryPos() {
        this.setCenterX(400 + 350 * Math.cos(theta0));
        this.setCenterY(400 + 350 * Math.sin(theta0));
    }

    /**
     * Zmienia współrzędne środka koła.
     * @param x współrzędna X środka koła
     * @param y współrzędna Y środka koła
     */
    private void updatePos(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
    }

    /**
     * Zwraca współrzędną X środka koła dla podanego czasu.
     * @param time czas w sekundach
     * @return współrzędna X środka koła
     */
    private double newX(double time) {
        return 400 + 350 * Math.cos(omega * time + theta0);
    }

    /**
     * Zwraca współrzędną Y środka koła dla podanego czasu.
     * @param time czas w sekundach
     * @return współrzędna Y środka koła
     */
    private double newY(double time) {
        return 400 + 350 * Math.sin(omega * time + theta0);
    }

    /**
     * Funkcja zwracająca kąt początkowy.
     * @return kąt początkowy z przedziału [0, 2PI]
     */
    public double getTheta0() {
        return theta0;
    }

    /**
     * Funkcja zwracająca prędkość kątową.
     * @return prędkość kątowa koła
     */
    public double getOmega() {
        return omega;
    }

    /**
     * Funkcja run wykonująca się przez cały czas działania programu.
     * Co każde 50 milisekund odświeżana jest pozycja koła.
     * Jeśli ma ono zetknąc się z poprzednikiem jest przez niego blokowane i zaczyna się poruszać z jego prędkością kątową.
     */
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
