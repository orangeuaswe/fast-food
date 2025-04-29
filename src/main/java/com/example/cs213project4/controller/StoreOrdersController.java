/**
 * StoreOrdersController.java
 *
 * Summary:
 * This controller manages the UI for viewing, canceling, and exporting stored orders.
 * It displays a list of stored orders, shows the details of the selected order, and
 * allows the user to cancel or export orders via file chooser dialogs.
 *
 * @authors
 * Anirudh Deveram
 * Karthik Penumetch
 */

package com.example.cs213project4.controller;

import com.example.cs213project4.model.Order;
import com.example.cs213project4.model.StoredOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class StoreOrdersController {

    // FXML components injected from the view.
    @FXML
    private ListView<Order> orderList;      // List view displaying all stored orders.
    @FXML
    private TextArea orderDetails;          // Text area for showing detailed info of the selected order.
    @FXML
    private Button cancelOrder;             // Button to cancel the currently selected order.
    @FXML
    private Button exportOrder;             // Button to export all stored orders to a file.
    @FXML
    private Button close;                   // Button to close the order management window.

    // The stored orders model.
    private StoredOrder sOrder;

    /**
     * Initializes the StoreOrdersController.
     * Sets up the stored order model, updates the order list, and configures listeners
     * for order selection changes and button states.
     */
    public void initialize() {
        // Retrieve the stored orders from the MainController.
        sOrder = MainController.getStoredOrder();
        // Populate the order list with current stored orders.
        updateOrderList();

        // Add a listener to update the order details display when a new order is selected.
        orderList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                orderDetails.setText(newValue.toString());
                cancelOrder.setDisable(false);
            } else {
                orderDetails.clear();
                cancelOrder.setDisable(true);
            }
        });

        // Initially, cancel button should be disabled until an order is selected.
        cancelOrder.setDisable(true);
        // Disable export button if there are no orders in storage.
        exportOrder.setDisable(sOrder.getOrders().isEmpty());
    }

    /**
     * Updates the order list view with the latest stored orders.
     * Also enables or disables the export button based on whether orders exist.
     */
    private void updateOrderList() {
        orderList.getItems().clear();
        orderList.getItems().addAll(sOrder.getOrders());
        exportOrder.setDisable(sOrder.getOrders().isEmpty());
    }

    /**
     * Handles the event for canceling a selected order.
     * Prompts the user for confirmation, cancels the order if confirmed,
     * and updates the UI accordingly.
     */
    public void handleCancelOrder() {
        // Get the currently selected order from the list.
        Order selected = orderList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Create a confirmation alert before canceling the order.
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            warning.setTitle("Cancel Order");
            warning.setHeaderText("Cancel Order #" + selected.getNumber());
            warning.setContentText("Are you sure you want to cancel your order?");
            // Show the alert and wait for user response.
            warning.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // If confirmed, cancel the order in the stored orders model.
                    sOrder.cancelOrder(selected.getNumber());
                    // Refresh the order list and clear the order details.
                    updateOrderList();
                    orderDetails.clear();
                    // Disable the cancel order button as no order is now selected.
                    cancelOrder.setDisable(true);
                }
            });
        }
    }

    /**
     * Handles the event for closing the stored orders window.
     *
     * @param event the ActionEvent triggered by clicking the close button.
     */
    public void handleClose(ActionEvent event) {
        ((Stage) close.getScene().getWindow()).close();
    }

    /**
     * Handles the event for exporting stored orders.
     * Opens a file chooser to select the destination file and attempts to export orders.
     * Displays a success or failure message accordingly.
     *
     * @param event the ActionEvent triggered when the export button is clicked.
     */
    @FXML
    private void handleExportOrders(ActionEvent event) {
        // Setup and configure the file chooser.
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export Orders");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        chooser.setInitialFileName("RUBurger_Orders.txt");

        // Open the file save dialog.
        File file = chooser.showSaveDialog(exportOrder.getScene().getWindow());
        if (file != null) {
            // Attempt to export orders to the selected file path.
            boolean success = sOrder.exportOrders(file.getAbsolutePath());
            // Notify the user of the result.
            if (success) {
                Alert pass = new Alert(Alert.AlertType.INFORMATION);
                pass.setTitle("Export Successful");
                pass.setHeaderText("Orders Exported");
                pass.setContentText("Orders have been exported successfully to " + file.getAbsolutePath());
                pass.showAndWait();
            } else {
                Alert failure = new Alert(Alert.AlertType.ERROR);
                failure.setTitle("Export Failed");
                failure.setHeaderText("Orders Export Failed");
                failure.setContentText("There was an error exporting the orders.");
                failure.showAndWait();
            }
        }
    }
}
