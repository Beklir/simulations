package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class LinearCollisionSimulation extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Circle ball1 = new Circle(10);
        Circle ball2 = new Circle(10);

        Pane root = new Pane();
        root.getChildren().addAll(ball1, ball2);
        Scene scene = new Scene(root, 800, 400);

        double ball1_pos = scene.getWidth() / 4;
        double ball2_pos = scene.getWidth() * 3 / 4;

        ball1.setCenterY(scene.getHeight() / 2);
        ball2.setCenterY(scene.getHeight() / 2);

        double ball1_vel = 400;
        double ball2_vel = -400;

        Collision collisionPhysics = new Collision(scene, ball1, ball2, ball1_pos, ball2_pos, ball1_vel, ball2_vel);

        primaryStage.setTitle("Collision Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();

        collisionPhysics.movement();
    }




}
