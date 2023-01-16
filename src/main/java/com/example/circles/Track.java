package com.example.circles;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;
import java.util.Random;

/**
 * Klasa toru, okrąg, po którym poruszają się koła - obiekty klasy MyCircle.
 */
public class Track {
    Random rand;
    MyCircle[] circles;
    final int NUM_OF_CIRCLES;
    private double[] thetas;

    /**
     * Konstruktor obiektu klasy Track.
     * @param num liczba kół w symulacji
     * @param borderPane panel, na którym zostanie umieszczony obiekt
     */
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

    /**
     * Tworzy tablicę losowych kątów z przedziału [0, 2PI], tak aby koła z podanymi kątami początkowymi nie kolidowały ze sobą.
     */
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

    /**
     * Tworzy obiekty klasy MyCircle o kątach początkowych z tablicy thetas[] i dodaje je na panel.
     * @param borderPane panel, na który dodawane są koła
     */
    private void createCircles(BorderPane borderPane) {
        for (int i = 0; i < NUM_OF_CIRCLES; i++) {
            circles[i] = new MyCircle(this, thetas[i], rand, borderPane);
        }
        circles[NUM_OF_CIRCLES - 1].setPredecessor(circles[0]);
        for (int i = 0; i < NUM_OF_CIRCLES - 1; i++) {
            circles[i].setPredecessor(circles[i + 1]);
        }
        borderPane.getChildren().addAll(circles);
    }

    /**
     * Funkcja sprawdzająca czy potencjalne koło stworzone z danym kątem początkowym będzie kolidować z powstałymi już kołami.
     * @param theta0
     * @return true jeśli stworzone koło będzie kolidować z innymi, false jeśli nie będzie
     */
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