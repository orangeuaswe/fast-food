/**
 * BeverageController.java
 *
 * Summary:
 * This controller manages the UI interactions for configuring a beverage order within the application.
 * It handles the selection of flavor, size, quantity, updates the beverage image, and calculates the cost.
 * It also contains actions for adding the beverage to the current order or canceling the operation.
 *
 * @authors
 * Anirudh Deveram
 * Karthik Penumetch
 */

package com.example.cs213project4.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.example.cs213project4.model.*;
import java.io.InputStream;

public class BeverageController {

    // FXML-annotated UI components injected from the view.
    @FXML
    private ComboBox<Flavor> drinkFlavorBox;
    @FXML
    private RadioButton small;
    @FXML
    private RadioButton medium;
    @FXML
    private RadioButton large;
    @FXML
    private Spinner<Integer> quantity;
    @FXML
    private Label cost;
    @FXML
    private Button addOrder;
    @FXML
    private Button cancel;
    @FXML
    private ImageView beverageImage;

    // Group for the size RadioButtons and the Beverage model instance.
    private ToggleGroup size;
    private Beverage bev;

    /**
     * Initializes the controller by setting up UI components and event listeners.
     * This includes populating the flavor combo box, configuring the size toggle group,
     * and setting up the quantity spinner and cost update mechanisms.
     */
    public void initialize() {
        // Create a new Beverage instance to hold the order data.
        bev = new Beverage();

        // Set up the flavor ComboBox:
        // Populate with all available flavors defined in the Flavor enum.
        // Set default selection to COLA and update image and cost on flavor change.
        drinkFlavorBox.getItems().addAll(Flavor.values());
        drinkFlavorBox.setValue(Flavor.COLA);
        drinkFlavorBox.setOnAction(e -> {
            bev.setFlavor(drinkFlavorBox.getValue());
            updateBevImage();
            updateCost();
        });

        // Set up the size toggle group:
        // Assign the three size options to a single ToggleGroup so that only one can be selected.
        // Default selected size is SMALL and listeners update the Beverage size and recalculates the cost.
        size = new ToggleGroup();
        small.setToggleGroup(size);
        medium.setToggleGroup(size);
        large.setToggleGroup(size);
        small.setSelected(true);
        small.setOnAction(e -> {
            bev.setSize(Size.SMALL);
            updateCost();
        });
        medium.setOnAction(e -> {
            bev.setSize(Size.MEDIUM);
            updateCost();
        });
        large.setOnAction(e -> {
            bev.setSize(Size.LARGE);
            updateCost();
        });

        // Set up the quantity spinner:
        // Defines a value factory that allows values from 1 to 10, with an initial value of 1.
        // Listens to changes in quantity, updating the Beverage object and cost accordingly.
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        quantity.setValueFactory(valueFactory);
        quantity.valueProperty().addListener((obs, oldVal, newVal) -> {
            bev.setQuantity(newVal);
            updateCost();
        });

        // Initialize the beverage image and cost display.
        updateBevImage();
        updateCost();
    }

    /**
     * Updates the beverage image based on the currently selected flavor.
     * Attempts to load a flavor-specific image. If not found, a default image is used.
     * If neither image is available, an error message is printed.
     */
    private void updateBevImage() {
        // Format the flavor name to lower case and replace spaces with underscores.
        String flavorName = drinkFlavorBox.getValue().toString().toLowerCase().replace(" ", "_");
        String imagePath = "/com/example/cs213project4/image/beverage_" + flavorName + ".png";

        // Try loading the image specific to the flavor.
        InputStream bevStream = getClass().getResourceAsStream(imagePath);
        Image pic;
        if (bevStream != null) {
            // Create Image from the input stream.
            pic = new Image(bevStream);
        } else {
            // Attempt to load the default beverage image if flavor-specific image is missing.
            InputStream fallbackStream = getClass().getResourceAsStream("/com/example/cs213project4/image/beverage.png");
            if (fallbackStream != null) {
                pic = new Image(fallbackStream);
            } else {
                // Log error if neither image resource is available.
                System.err.println("Default beverage image not found.");
                return;
            }
        }
        // Set the image on the ImageView.
        beverageImage.setImage(pic);
    }

    /**
     * Updates the cost label to display the current beverage cost, formatted as currency.
     * The cost is computed by the Beverage model.
     */
    private void updateCost() {
        cost.setText(String.format("$%.2f", bev.cost()));
    }

    /**
     * Event handler for the "Order" button.
     * Ensures the proper size is set, adds the beverage to the current order,
     * and then closes the current window.
     *
     * @param event the ActionEvent triggered by clicking the order button.
     */
    @FXML
    private void handleOrderButton(ActionEvent event) {
        // Ensure the Beverage model holds the correct size from selected RadioButton.
        if (small.isSelected()) {
            bev.setSize(Size.SMALL);
        }
        if (medium.isSelected()) {
            bev.setSize(Size.MEDIUM);
        }
        if (large.isSelected()) {
            bev.setSize(Size.LARGE);
        }
        // Retrieve the current order from the main controller and add this beverage to it.
        Order current = MainController.getCurrentOrder();
        current.addItem(bev);
        // Close the window after successfully adding the beverage to the order.
        ((Stage) addOrder.getScene().getWindow()).close();
    }

    /**
     * Event handler for the "Cancel" button.
     * Closes the current window without saving any changes.
     *
     * @param event the ActionEvent triggered by clicking the cancel button.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        ((Stage) cancel.getScene().getWindow()).close();
    }
}
