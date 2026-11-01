package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import static java.lang.Math.PI;

public class FalllingPendulumSimulation extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Circle bob = new Circle(10);
        Line rod = new Line();

        Pane root = new Pane();
        root.getChildren().addAll(bob, rod);
        Scene scene = new Scene(root, 800, 600);

        double pivot_x = scene.getWidth() / 2;
        double pivot_y = scene.getHeight() / 2;
        double rod_length = 200;
        double angle = -PI/2;

        rod.setStartX(pivot_x);
        rod.setStartY(pivot_y);
        rod.endXProperty().bind(bob.centerXProperty());
        rod.endYProperty().bind(bob.centerYProperty());

        Pendulum physics = new Pendulum(bob, rod, rod_length, pivot_x, pivot_y,angle);

        primaryStage.setTitle("Pendulum Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();

        physics.pendulumMovement();
    }
}
