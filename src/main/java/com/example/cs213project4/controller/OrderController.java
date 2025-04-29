/**
 * OrderController.java
 *
 * Summary:
 * This controller manages the order view where users can review the items added to
 * their order. It displays the list of ordered items along with the subtotal, tax, and
 * total cost. Users can remove individual items, clear the entire order, place the order,
 * or simply close the order view.
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.example.cs213project4.model.*;
import com.example.cs213project4.model.MenuItem;
import java.io.IOException;

public class OrderController {

    // FXML Components for the order view UI.
    @FXML
    private ListView<MenuItem> items;      // List view for displaying all menu items in the order.
    @FXML
    private Label subtotal;                // Label for showing the order's subtotal.
    @FXML
    private Label tax;                     // Label for showing the calculated tax.
    @FXML
    private Label total;                   // Label for showing the overall total cost.
    @FXML
    private Label orderNumber;             // Label to display the unique order number.
    @FXML
    private Button removeItem;             // Button for removing a selected item.
    @FXML
    private Button clearOrder;             // Button for clearing all items from the order.
    @FXML
    private Button placeOrder;             // Button for placing the order.
    @FXML
    private Button close;                  // Button for closing the order view window.

    // Represents the current order being edited.
    private Order current;

    /**
     * Initialization method called after FXML components are loaded.
     * Retrieves the current order, updates order number and item list,
     * cost display, and sets up a listener to adjust buttons based on selection changes.
     */
    public void initialize() {
        // Retrieve the current order; if not available, create a new one.
        current = MainController.getCurrentOrder();
        if (current == null) {
            MainController.setNewCurrentOrder();
            current = MainController.getCurrentOrder();
        }

        // Set the order number label using the current order's unique number.
        orderNumber.setText("Order #" + current.getNumber());

        // Update UI elements based on current order data.
        updateItemList();
        updateCostDisplay();
        updateButton();

        // Add a listener to the selection model to enable/disable buttons as needed.
        items.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateButton());
    }

    /**
     * Updates the list view to display the current order's items.
     */
    private void updateItemList() {
        items.getItems().clear();
        items.getItems().addAll(current.getItems());
    }

    /**
     * Updates the subtotal, tax, and total labels based on the current order costs.
     */
    private void updateCostDisplay() {
        subtotal.setText(String.format("$%.2f", current.getTotal()));
        tax.setText(String.format("$%.2f", current.getTax()));
        total.setText(String.format("$%.2f", current.getTotalCost()));
    }

    /**
     * Enables or disables buttons based on whether items exist in the order and if an item is selected.
     */
    public void updateButton() {
        boolean hasItems = !current.getItems().isEmpty();
        boolean hasSelection = items.getSelectionModel().getSelectedItem() != null;

        removeItem.setDisable(!hasSelection);
        clearOrder.setDisable(!hasItems);
        placeOrder.setDisable(!hasItems);
    }

    /**
     * Event handler for removing a selected item from the order.
     * Updates the item list, cost display, and button states accordingly.
     *
     * @param event the ActionEvent triggered when the remove item button is clicked.
     */
    @FXML
    public void handleRemoveItem(ActionEvent event) {
        MenuItem selected = items.getSelectionModel().getSelectedItem();
        if (selected != null) {
            current.removeItem(selected);
            updateItemList();
            updateCostDisplay();
            updateButton();
        }
    }

    /**
     * Event handler for clearing the entire order.
     * Confirms the user action before removing all items from the current order.
     *
     * @param event the ActionEvent triggered when the clear order button is clicked.
     */
    @FXML
    public void handleClearOrder(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear Order");
        alert.setHeaderText("Clear Entire Order");
        alert.setContentText("Are you sure you want to clear this order?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                current.eradicateAllItems();
                updateItemList();
                updateCostDisplay();
                updateButton();
            }
        });
    }

    /**
     * Event handler for placing the order.
     * Adds the current order to stored orders, resets the current order,
     * displays a confirmation alert, and closes the order view window.
     *
     * @param event the ActionEvent triggered when the place order button is clicked.
     */
    @FXML
    public void handlePlaceOrder(ActionEvent event) {
        // Add the current order to the stored orders and reset for a new order.
        MainController.getStoredOrder().addOrder(current);
        MainController.setNewCurrentOrder();

        // Inform the user that the order was placed successfully.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Placed");
        alert.setHeaderText("Order #" + current.getNumber() + " Placed");
        alert.setContentText("The order has been placed successfully!");
        alert.showAndWait();

        // Close the order view window.
        ((Stage) placeOrder.getScene().getWindow()).close();
    }

    /**
     * Event handler for closing the order view window without making changes.
     *
     * @param event the ActionEvent triggered when the close button is clicked.
     */
    @FXML
    public void handleClose(ActionEvent event) {
        ((Stage) close.getScene().getWindow()).close();
    }
}
