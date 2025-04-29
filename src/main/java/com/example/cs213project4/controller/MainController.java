/**
 * MainController.java
 *
 * Summary:
 * This controller serves as the primary interface for navigating the RU Fast Food application.
 * It facilitates opening different ordering windows for burgers, sandwiches, beverages, and sides,
 * as well as viewing the current and stored orders. The controller maintains a reference to the current
 * order and the stored orders to ensure that any new order created is non-null.
 *
 * @authors
 * Anirudh Deveram
 * Karthik Penumetch
 */

package com.example.cs213project4.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import com.example.cs213project4.model.Order;
import com.example.cs213project4.model.StoredOrder;
import java.io.IOException;
import javafx.scene.control.Alert;

public class MainController {

    // FXML Components: buttons for navigating to various ordering screens.
    @FXML
    private Button burgerButton;        // Opens the Burger ordering view.
    @FXML
    private Button sandwichButton;      // Opens the Sandwich ordering view.
    @FXML
    private Button bevButton;           // Opens the Beverage ordering view.
    @FXML
    private Button sideButton;          // Opens the Sides ordering view.
    @FXML
    private Button viewOrderButton;     // Opens the current order view.
    @FXML
    private Button viewStoredOrderButton; // Opens the stored orders view.

    // Static model objects to maintain the state of orders.
    private static StoredOrder storedOrder = new StoredOrder();
    private static Order current;

    /**
     * Initialization method called after FXML loading.
     * Ensures that a current order exists for the session.
     */
    public void initialize() {
        // If no current order exists, create a new one from the stored orders.
        if (current == null) {
            current = storedOrder.createNewOrder();
        }
    }

    /**
     * Returns the current order, ensuring it is not null.
     *
     * @return the current Order instance.
     */
    public static Order getCurrentOrder() {
        // Create a new order if the current one is null.
        if (current == null) {
            current = storedOrder.createNewOrder();
        }
        return current;
    }

    /**
     * Returns the stored order collection.
     *
     * @return the StoredOrder instance.
     */
    public static StoredOrder getStoredOrder() {
        return storedOrder;
    }

    /**
     * Resets the current order by creating a new order from the stored order collection.
     */
    public static void setNewCurrentOrder() {
        current = storedOrder.createNewOrder();
    }

    /**
     * Event handler for the Burger button.
     * Opens the Burger ordering window.
     *
     * @param event the triggering ActionEvent.
     */
    @FXML
    private void handleBurgerButton(ActionEvent event) {
        try {
            openWindow("/com/example/cs213project4/BurgerView.fxml", "Order Burger");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Event handler for the Sandwich button.
     * Opens the Sandwich ordering window.
     *
     * @param event the triggering ActionEvent.
     */
    @FXML
    private void handleSandwichButton(ActionEvent event) {
        try {
            openWindow("/com/example/cs213project4/SandwichView.fxml", "Order Sandwich");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Event handler for the Side button.
     * Opens the Sides ordering window.
     *
     * @param event the triggering ActionEvent.
     */
    @FXML
    private void handleSideButton(ActionEvent event) {
        try {
            openWindow("/com/example/cs213project4/SideView.fxml", "Order Sides");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Event handler for the View Order button.
     * Opens the window displaying the current order details.
     *
     * @param event the triggering ActionEvent.
     */
    @FXML
    private void handleViewOrderButton(ActionEvent event) {
        try {
            openWindow("/com/example/cs213project4/OrderView.fxml", "Current Order");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Event handler for the View Stored Orders button.
     * Opens the window displaying stored orders.
     *
     * @param event the triggering ActionEvent.
     */
    @FXML
    private void handleViewStoredOrdersButton(ActionEvent event) {
        try {
            openWindow("/com/example/cs213project4/StoreOrdersView.fxml", "Store Orders");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Event handler for the Beverage button.
     * Opens the Beverage ordering window.
     *
     * @param event the triggering ActionEvent.
     */
    @FXML
    private void handleBevButton(ActionEvent event) {
        try {
            openWindow("/com/example/cs213project4/BeverageView.fxml", "Order Beverage");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Opens a new window given the FXML file path and window title.
     * If an error occurs during loading, it prints the error and throws an IOException.
     *
     * @param fxmlPath the path of the FXML file to load.
     * @param title the title of the new window.
     * @throws IOException if the FXML file cannot be loaded.
     */
    private void openWindow(String fxmlPath, String title) throws IOException {
        try {
            // Load the FXML view using the provided path.
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Create a new stage (window) for the view.
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 600, 500));  // Set the scene dimensions.
            stage.show();
        } catch (Exception e) {
            // Log the error details if opening the window fails.
            System.out.println("Error opening " + title + ": " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Could not load " + fxmlPath);
        }
    }
}
