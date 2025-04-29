/**
 * SideController.java
 *
 * Summary:
 * This controller handles the user interactions for customizing and ordering side dishes.
 * It allows users to select a side option, choose a size (small, medium, or large), and set a quantity.
 * The controller updates the side image based on the selection, calculates the cost accordingly,
 * and provides event handlers to add the side to the current order or cancel the operation.
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
import java.util.Objects;

public class SideController {

    // FXML components injected from the view.
    @FXML
    private ComboBox<SideOption> optionCombo;  // Dropdown for selecting side options (e.g., Chips, Apple Slices)
    @FXML
    private RadioButton small;                 // Radio button for small size selection
    @FXML
    private RadioButton medium;                // Radio button for medium size selection
    @FXML
    private RadioButton large;                 // Radio button for large size selection
    @FXML
    private Spinner<Integer> quantity;         // Spinner control to choose the quantity of sides
    @FXML
    private Label cost;                        // Label to display the calculated cost of the side order
    @FXML
    private Button addOrder;                   // Button to add the side to the current order
    @FXML
    private Button cancel;                     // Button to cancel the side order
    @FXML
    private ImageView sideImage;               // Image view that displays the image of the selected side option

    // ToggleGroup to group the size radio buttons so that only one can be selected at a time.
    private ToggleGroup size;
    // The model object for the Side that is being customized.
    private Side side;

    /**
     * Initializes the SideController.
     * Sets up default values, populates the side option ComboBox, configures the size radio buttons,
     * initializes the quantity spinner, and sets up event handlers to update the image and cost
     * whenever the user makes changes.
     */
    public void initialize() {
        // Create a new Side object instance.
        side = new Side();

        // Populate the ComboBox with all available side options.
        optionCombo.getItems().addAll(SideOption.values());
        // Set the default selection to CHIPS.
        optionCombo.setValue(SideOption.CHIPS);
        // Register an event handler that updates the side model, image, and cost when the selection changes.
        optionCombo.setOnAction(e -> {
            side.setSide(optionCombo.getValue());
            updateSideImage();
            updateCost();
        });

        // Initialize the ToggleGroup for size selection.
        size = new ToggleGroup();
        small.setToggleGroup(size);
        medium.setToggleGroup(size);
        large.setToggleGroup(size);
        // Set the default size selection to small.
        small.setSelected(true);
        // Update the side model and cost when the size is selected.
        small.setOnAction(e -> { side.setSize(Size.SMALL); updateCost(); });
        medium.setOnAction(e -> { side.setSize(Size.MEDIUM); updateCost(); });
        large.setOnAction(e -> { side.setSize(Size.LARGE); updateCost(); });

        // Configure the quantity spinner with a range from 1 to 10, default value is 1.
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        quantity.setValueFactory(valueFactory);
        // Add a listener to update the side quantity and recalculate the cost when the spinner value changes.
        quantity.valueProperty().addListener((obs, oldValue, newValue) -> {
            side.setQuantity(newValue);
            updateCost();
        });

        // Initialize the side image and cost display.
        updateSideImage();
        updateCost();
    }

    /**
     * Updates the side image displayed in the UI based on the current side option.
     * Constructs the image file path dynamically and attempts to load the image;
     * if the specific image is not found, it falls back to a default side image.
     */
    private void updateSideImage() {
        // Convert the selected side option to lowercase and format it to match the resource naming convention.
        String sideName = optionCombo.getValue().toString().toLowerCase().replace(" ", "_");
        String imagePath = "/com/example/cs213project4/image/side_" + sideName + ".png";

        // Attempt to load the specific side image resource.
        InputStream sideStream = getClass().getResourceAsStream(imagePath);
        Image pic;
        if (sideStream != null) {
            pic = new Image(sideStream);
        } else {
            // If the specific image isn't found, attempt to load a default side image.
            InputStream fallbackStream = getClass().getResourceAsStream("/com/example/cs213project4/image/side.png");
            if (fallbackStream != null) {
                pic = new Image(fallbackStream);
            } else {
                // Log an error if no image is available.
                System.err.println("Default side image not found.");
                return;
            }
        }
        // Set the loaded image to the ImageView.
        sideImage.setImage(pic);
    }

    /**
     * Calculates and updates the cost label based on the current configuration of the side.
     * The cost is computed by the side model's cost() method.
     */
    private void updateCost() {
        cost.setText(String.format("$%.2f", side.cost()));
    }

    /**
     * Handles the event when the "Add to Order" button is pressed.
     * It adds the configured side to the current order and then closes the window.
     *
     * @param event the ActionEvent triggered by pressing the button.
     */
    @FXML
    public void handleAddToOrder(ActionEvent event) {
        // Retrieve the current order from the MainController.
        Order current = MainController.getCurrentOrder();

        // If no current order exists, display an error alert and exit.
        if (current == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot add to order");
            alert.setContentText("The current order is not available");
            alert.showAndWait();
            return;
        }

        // Explicitly set the size on the side model based on the selected radio button.
        if (small.isSelected()) {
            side.setSize(Size.SMALL);
        } else if (medium.isSelected()) {
            side.setSize(Size.MEDIUM);
        } else {  // Assume large is selected if neither small nor medium is selected.
            side.setSize(Size.LARGE);
        }

        // Add the configured side to the current order.
        current.addItem(side);

        // Close the current window.
        ((Stage) addOrder.getScene().getWindow()).close();
    }

    /**
     * Handles the event when the "Cancel" button is pressed.
     * Simply closes the window without adding the side to the order.
     *
     * @param event the ActionEvent triggered by pressing the cancel button.
     */
    public void handleCancel(ActionEvent event) {
        ((Stage) cancel.getScene().getWindow()).close();
    }
}
