/**
 * Main.java
 *
 * Summary:
 * This class is the entry point for the RU Fast Food application.
 * It extends the JavaFX Application class and is responsible for initializing
 * and displaying the main user interface window. The application loads the main
 * FXML view, sets up the stage (window) with a title and scene dimensions,
 * and then displays it.
 *
 * @authors
 * Anirudh Deveram
 * Karthik Penumetch
 */

package com.example.cs213project4.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {

    /**
     * The start method is the main entry point for all JavaFX applications.
     * This method is called after the system is ready for the application to begin running.
     *
     * @param primary the primary stage for this application, onto which the application scene can be set.
     * @throws Exception if the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage primary) throws Exception {
        // Load the main view FXML file using a classloader resource.
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/com/example/cs213project4/MainView.fxml"))
        );

        // Set the title of the main window.
        primary.setTitle("RU Fast Food");

        // Create a new scene with the loaded root node with dimensions 800x800 and set it to the stage.
        primary.setScene(new Scene(root, 800, 800));

        // Display the stage (window) on the screen.
        primary.show();
    }

    /**
     * The main method serves as a fallback entry point. It calls the launch method
     * which internally calls the start method and initializes the JavaFX application.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Launch the JavaFX application.
        launch(args);
    }
}
