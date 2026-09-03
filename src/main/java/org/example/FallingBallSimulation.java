package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class FallingBallSimulation extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Circle ball = new Circle(10);

        Label energy = new Label("");

        Pane root = new Pane();
        root.getChildren().addAll(ball, energy);
        Scene scene = new Scene(root, 500, 700);

        double s_x = scene.getWidth() / 2;
        double s_y = 100;

        BallPhysics physics = new BallPhysics(ball, scene, energy, s_x, s_y, 4,-4,0,0);

        primaryStage.setTitle("Ball Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();

        physics.fallingMovement();

    }
}
