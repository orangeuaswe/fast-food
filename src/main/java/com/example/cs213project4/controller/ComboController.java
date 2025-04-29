/**
 * ComboController.java
 *
 * Summary:
 * This controller manages the UI interactions for assembling a combo meal within the application.
 * A combo consists of a sandwich (or burger), a beverage, and a side. The controller handles the
 * selection of beverage flavor and side option, updates images accordingly, calculates the total cost
 * of the combo, and handles the order addition or cancellation.
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
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ComboController {

    // FXML Components
    @FXML
    private ComboBox<SideOption> sideCombo;   // ComboBox for selecting side options (e.g., chips, apple slices)
    @FXML
    private ComboBox<Flavor> drinkCombo;        // ComboBox for selecting beverage flavor
    @FXML
    private Label cost;                         // Label for displaying the cost of the combo
    @FXML
    private Button addOrder;                    // Button to add the combo to the order
    @FXML
    private Button cancel;                      // Button to cancel the combo creation
    @FXML
    private ImageView sideImage;                // ImageView for displaying the side option image
    @FXML
    private ImageView drinkImage;               // ImageView for displaying the beverage image

    // Model objects for constructing the combo
    private Sandwich sandwich;                   // Sandwich or Burger instance (set via setters)
    private Side side;                           // Side selection object, based on user choices
    private Beverage bev;                        // Beverage selection object
    private Combo combo;                         // Combo object that encapsulates the sandwich, beverage, and side

    /**
     * Initializes the ComboController.
     * Sets default values for beverage and side, configures combo boxes,
     * assigns event listeners to update images and cost when selections change.
     */
    public void initialize() {
        // Initialize the side with a default size and option.
        side = new Side(Size.SMALL, SideOption.CHIPS);
        // Initialize the beverage with a default size and flavor.
        bev = new Beverage(Size.MEDIUM, Flavor.COLA);

        // Populate sideCombo with available side options.
        sideCombo.getItems().addAll(SideOption.CHIPS, SideOption.APPLE_SLICES);
        // Set event listener to update the Side model and UI when selection changes.
        sideCombo.setOnAction(e -> {
            side.setSide(sideCombo.getValue());
            updateSidePic();
            updateCombo();
        });

        // Populate drinkCombo with a variety of beverage flavors.
        drinkCombo.getItems().addAll(
                Flavor.COLA,
                Flavor.DIET_COLA,
                Flavor.ICED_TEA,
                Flavor.PEACH_TEA,
                Flavor.GREEN_TEA,
                Flavor.ORANGE_JUICE,
                Flavor.GRAPE,
                Flavor.LEMONADE
        );
        // Set default beverage flavor and assign a listener to update the beverage and UI.
        drinkCombo.setValue(Flavor.COLA);
        drinkCombo.setOnAction(e -> {
            bev.setFlavor(drinkCombo.getValue());
            updateBevImage();
            updateCombo();
        });

        // Ensure the sideCombo has a default value and setup its listener.
        sideCombo.getItems().addAll(SideOption.CHIPS, SideOption.APPLE_SLICES);
        sideCombo.setValue(SideOption.CHIPS);
        sideCombo.setOnAction(e -> {
            side.setSide(sideCombo.getValue());
            updateBevImage();
            updateCombo();
        });
    }

    /**
     * Sets the sandwich portion of the combo.
     *
     * @param sand a Sandwich object representing a sandwich combo component.
     */
    public void setSandwich(Sandwich sand) {
        this.sandwich = sand;
        updateCombo();
    }

    /**
     * Sets the burger portion of the combo.
     * Since Burger extends Sandwich, it is set to the same sandwich variable.
     *
     * @param burger a Burger object representing a burger combo component.
     */
    public void setBurger(Burger burger) {
        this.sandwich = burger;
        updateCombo();
    }

    /**
     * Updates the combo object by creating a new Combo with the current sandwich, beverage, and side.
     * Also updates the total cost display.
     */
    private void updateCombo() {
        if (sandwich != null) {
            // Create a new Combo from the sandwich, beverage, and side selections.
            this.combo = new Combo(sandwich, bev, side);
            updateCost();
        }
    }

    /**
     * Updates the beverage image based on the selected flavor in drinkCombo.
     * Attempts to load a specific image for the flavor; falls back to a default image if not found.
     */
    private void updateBevImage() {
        // Build the filename using the selected flavor (e.g., "cola" becomes "beverage_cola.png").
        String flavorName = drinkCombo.getValue().toString().toLowerCase().replace(" ", "_");
        String imagePath = "/com/example/cs213project4/image/beverage_" + flavorName + ".png";

        // Load the beverage image from the resources.
        InputStream bevStream = getClass().getResourceAsStream(imagePath);
        Image pic;
        if (bevStream != null) {
            pic = new Image(bevStream);
        } else {
            // If the specific image is not found, try to load the default beverage image.
            InputStream fallbackStream = getClass().getResourceAsStream("/com/example/cs213project4/image/beverage.png");
            if (fallbackStream != null) {
                pic = new Image(fallbackStream);
            } else {
                System.err.println("Default beverage image not found.");
                return;
            }
        }
        // Set the image in the ImageView.
        drinkImage.setImage(pic);
    }

    /**
     * Updates the side image based on the selected side option in sideCombo.
     * Constructs the image file name and loads the image; falls back to a default if necessary.
     */
    private void updateSidePic() {
        // Build the filename from the side option (e.g., "chips" becomes "side_chips.png").
        String sideName = sideCombo.getValue().toString().toLowerCase().replace(" ", "_");
        String imagePath = "/com/example/cs213project4/image/side_" + sideName + ".png";

        // Attempt to load the specific side image.
        InputStream sideStream = getClass().getResourceAsStream(imagePath);
        Image pic;
        if (sideStream != null) {
            pic = new Image(sideStream);
        } else {
            // Fallback to a default side image if specific one is not found.
            InputStream fallbackStream = getClass().getResourceAsStream("/com/example/cs213project4/image/side.png");
            if (fallbackStream != null) {
                pic = new Image(fallbackStream);
            } else {
                System.err.println("Default side image not found.");
                return;
            }
        }
        // Set the side image on the ImageView.
        sideImage.setImage(pic);
    }

    /**
     * Updates the cost display label with the cost computed from the current combo.
     */
    private void updateCost() {
        if (combo != null) {
            cost.setText(String.format("$%.2f", combo.cost()));
        }
    }

    /**
     * Event handler for the "Order" button.
     * If a valid combo exists, adds it to the current order and closes the window.
     *
     * @param event the action event triggered when the order button is clicked.
     */
    @FXML
    private void handleOrder(ActionEvent event) {
        if (combo != null) {
            // Retrieve the current order and add the constructed combo.
            Order current = MainController.getCurrentOrder();
            current.addItem(combo);
        }
        // Close the current window.
        ((Stage) addOrder.getScene().getWindow()).close();
    }

    /**
     * Event handler for the "Cancel" button.
     * Closes the window without saving the current combo selection.
     *
     * @param event the action event triggered when the cancel button is clicked.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        ((Stage) cancel.getScene().getWindow()).close();
    }
}
