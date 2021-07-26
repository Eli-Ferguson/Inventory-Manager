/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Eli Ferguson
 */

package ucf.assignment5;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InventoryMain extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        SceneManager manager = new SceneManager(primaryStage);
        manager.loader();

        Scene scene = manager.getScene("HomeScreen");

        primaryStage.setTitle("Home");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
