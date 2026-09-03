package org.example;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class BallPhysics {
    private final double DENSITY  = 1.0;
    private final double RESTITUTION = 0.8;
    private final double PIXEL_METER_SCALE = 100;
    private final double G = 9.81 * PIXEL_METER_SCALE;
    private Circle ball;
    private Scene scene;
    private double s_x;
    private double s_y;
    private double v_x;
    private double v_y;
    private double a_x;
    private double a_y;

    private double mass;
    private double radius;
    private double height;
    private Label energyLabel;


    public BallPhysics(Circle ball, Scene scene, Label energyLabel, double s_x, double s_y, double v_x, double v_y, double a_x, double a_y) {
        this.ball = ball;
        this.scene = scene;
        this.s_x = s_x;
        this.s_y = s_y;
        this.v_x = v_x * PIXEL_METER_SCALE;
        this.v_y = v_y * PIXEL_METER_SCALE;
        this.a_x = a_x * PIXEL_METER_SCALE;
        this.a_y = a_y * PIXEL_METER_SCALE;
        radius = ball.getRadius();
        height = scene.getHeight() - (s_y + radius);
        this.mass = 4/3 * Math.PI * radius/PIXEL_METER_SCALE * radius/PIXEL_METER_SCALE * DENSITY;
        this.energyLabel = energyLabel;
    }

    public void fallingMovement(){
        a_y = a_y + G;
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

                boolean isRestingOnFloor = (v_x == 0.0 && v_y == 0.0 && s_y + radius >= scene.getHeight());

                if (!isRestingOnFloor) {
                    double v_y_old = v_y;
                    double v_x_old = v_x;
                    v_y += a_y * deltaSeconds;
                    v_x += a_x * deltaSeconds;

                    double v_y_average = (v_y_old + v_y) / 2.0;
                    double v_x_average = (v_x_old + v_x) / 2.0;
                    s_y += v_y_average * deltaSeconds;
                    s_x += v_x_average * deltaSeconds;

                    if (s_x - radius < 0) {
                        s_x = radius;
                        if (Math.abs(v_x) < 20.0) {
                            v_x = 0;
                        } else {
                            v_x = -v_x * RESTITUTION;
                        }
                    }
                    if (s_x + radius > scene.getWidth()){
                        s_x = scene.getWidth() - radius;
                        if (Math.abs(v_x) < 20.0) {
                            v_x = 0;
                        }else {
                            v_x = -v_x * RESTITUTION;
                        }
                    }

                    if (s_y + radius > scene.getHeight()) {
                        s_y = scene.getHeight() - radius;
                        if (Math.abs(v_y) < 20.0) {
                            v_y = 0;
                        } else {
                            v_y = -v_y * RESTITUTION;
                        }
                    }
                }else {
                    v_x = 0.0;
                    v_y = 0.0;
                    s_y = scene.getHeight() - radius;
                }

                ball.setTranslateX(s_x);
                ball.setTranslateY(s_y);

                height = scene.getHeight() - (s_y + radius);

                double kinetic = getKineticEnergy();
                double potential = getPotentialEnergy();

                energyLabel.setText(String.format("KINETIC ENERGY: %.2f " +
                                "\nPOTENTIAL ENERGY: %.2f " +
                                "\nTOTAL ENERGY: %.2f J",
                        kinetic, potential, (kinetic + potential)));
            }
        };
        timer.start();
    }

    public double getKineticEnergy() {
        double v_meters = Math.sqrt(v_x * v_x + v_y * v_y) / PIXEL_METER_SCALE;
        return 0.5 * mass * v_meters * v_meters;
    }

    public double getPotentialEnergy() {
        double h_meters = height / PIXEL_METER_SCALE;
        return mass * 9.81 * h_meters;
    }
}
