package org.example;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;

public class Collision {
    private final double DENSITY  = 1.0;
    private final double RESTITUTION = 1;
    private final double PIXEL_METER_SCALE = 100;

    private Scene scene;
    private Circle ball1;
    private Circle ball2;
    private double ball1_pos, ball2_pos;
    private double ball1_mass, ball2_mass;
    private double ball1_vel, ball2_vel;
    private double ball1_radius, ball2_radius;

    Collision(Scene scene, Circle  ball1, Circle ball2, double ball1_pos, double ball2_pos, double ball1_vel, double ball2_vel) {
        this.scene = scene;
        this.ball1 = ball1;
        this.ball2 = ball2;
        this.ball1_pos = ball1_pos;
        this.ball2_pos = ball2_pos;
        this.ball1_vel = ball1_vel;
        this.ball2_vel = ball2_vel;
        this.ball1_radius = ball1.getRadius();
        this.ball2_radius = ball2.getRadius();
        this.ball1_mass = Math.PI * Math.pow(ball1_radius, 3) * DENSITY * 4 / 3;
        this.ball2_mass = Math.PI * Math.pow(ball2_radius, 3) * DENSITY * 4 / 3;
    }

    void movement() {
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

                if (collided()) {
                    double ball1_final_vel,  ball2_final_vel;
                    ball1_final_vel = (ball1_mass-ball2_mass)/(ball1_mass+ball2_mass)*ball1_vel + (2*ball2_mass)/(ball1_mass+ball2_mass)*ball2_vel;
                    ball2_final_vel = (2*ball1_mass)/(ball1_mass+ball2_mass)*ball1_vel + (ball2_mass-ball1_mass)/(ball1_mass+ball2_mass)*ball2_vel;
                    ball1_vel = ball1_final_vel;
                    ball2_vel = ball2_final_vel;
                }

                if (ball1_pos-ball1_radius <=0) {
                    ball1_pos = ball1_radius;
                    ball1_vel = -ball1_vel;
                }
                if (ball2_pos+ball2_radius >= scene.getWidth()) {
                    ball2_pos = scene.getWidth() - ball2_radius;
                    ball2_vel = -ball2_vel;
                }

                ball1_pos += ball1_vel * deltaSeconds;
                ball2_pos += ball2_vel * deltaSeconds;

                ball1.setTranslateX(ball1_pos);
                ball2.setTranslateX(ball2_pos);

            }
        };
        timer.start();
    }

    private boolean collided() {
        double distance = Math.abs(ball2_pos - ball1_pos);
        double minimumDistance = ball1_radius + ball2_radius;

        if (distance < minimumDistance) {
            double overlap = minimumDistance - distance;

            ball1_pos -= overlap / 2.0;
            ball2_pos += overlap / 2.0;
        }
        return distance <= minimumDistance;
    }

}
