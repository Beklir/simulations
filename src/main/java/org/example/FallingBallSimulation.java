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
        double v_x = 4;
        double v_y = -4;
        double a_x = 0;
        double a_y = 0;

        BallPhysics physics = new BallPhysics(ball, scene, energy, s_x, s_y, v_x,v_y,a_x,a_y);

        primaryStage.setTitle("Ball Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();

        physics.fallingMovement();

    }
}
