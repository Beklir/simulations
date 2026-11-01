package org.example;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Pendulum {
    private final double DAMPING = 0.999;
    private final double PIXEL_METER_SCALE = 100;
    private final double G = 9.81 * PIXEL_METER_SCALE;

    private Circle bob;
    private Line rod;
    private double angle;
    private double angular_v;
    private double angular_a;
    private double rod_length;
    private double pivot_x;
    private double pivot_y;

    private double bob_x;
    private double bob_y;

    public Pendulum(Circle bob, Line rod, double rod_length, double pivot_x,
                    double pivot_y, double angle) {
        this.bob = bob;
        this.rod = rod;
        this.angle = angle;
        this.rod_length = rod_length;
        this.pivot_x = pivot_x;
        this.pivot_y = pivot_y;

    }

    public void pendulumMovement() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                double deltaSeconds = 0;
                if (lastTime != 0) {
                    long deltaNanos = now - lastTime;

                    deltaSeconds = deltaNanos / 1_000_000_000.0;
                }
                lastTime = now;

                angular_a = - (G / rod_length) * Math.sin(angle);
                angular_v += angular_a * deltaSeconds;
                angular_v *= Math.pow(DAMPING, deltaSeconds * 60.0);
                angle += angular_v * deltaSeconds;

                bob_x = pivot_x + rod_length * Math.sin(angle);
                bob_y = pivot_y + rod_length * Math.cos(angle);

                bob.setCenterX(bob_x);
                bob.setCenterY(bob_y);

            }
        };
        timer.start();
    }
}

