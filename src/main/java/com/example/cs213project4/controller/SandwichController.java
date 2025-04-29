/**
 * SandwichController.java
 *
 * Summary:
 * This controller manages the UI interactions for customizing a sandwich order within the application.
 * It allows the user to choose the bread type, protein, and a selection of add-ons such as lettuce,
 * tomatoes, onions, avocado, and cheese. The controller also handles the sandwich quantity, calculates the cost
 * (including an extra fee if the sandwich is part of a combo), and updates the displayed sandwich name.
 * When a combo is selected, it invokes the ComboView to allow further customization.
 *
 * @authors
 * Anirudh Deveram
 * Karthik Penumetch
 */

package com.example.cs213project4.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.cs213project4.model.*;
import com.example.cs213project4.model.MenuItem;
import javafx.scene.control.SpinnerValueFactory;
import javafx.event.ActionEvent;
import java.io.IOException;

public class SandwichController {

    // FXML Components for sandwich customization.
    @FXML
    private ComboBox<Bread> breadCombo;   // Dropdown for selecting the type of bread.
    @FXML
    private ComboBox<Protein> meatCombo;    // Dropdown for selecting the protein.
    @FXML
    private CheckBox lettuce;             // Checkbox to add lettuce.
    @FXML
    private CheckBox tomatoes;            // Checkbox to add tomatoes.
    @FXML
    private CheckBox onions;              // Checkbox to add onions.
    @FXML
    private CheckBox avocado;             // Checkbox to add avocado.
    @FXML
    private CheckBox cheese;              // Checkbox to add cheese.
    @FXML
    private CheckBox combo;               // Checkbox to mark the sandwich as part of a combo.
    @FXML
    private Spinner<Integer> quantity;    // Spinner to set the quantity of sandwiches.
    @FXML
    private TextField cost;               // Text field for displaying the sandwich cost.
    @FXML
    private Button addOrder;              // Button to add the sandwich (or combo) to the order.
    @FXML
    private Button cancel;                // Button to cancel the operation and close the window.
    @FXML
    private ImageView sandwichImage;      // ImageView to display the sandwich image (if used).

    // Model representing the sandwich being built.
    private Sandwich sandwich;
    // Flag to indicate whether the sandwich is being ordered as a combo.
    private boolean isCombo;

    /**
     * Initializes the SandwichController.
     * Sets up the initial sandwich model, default selections, and event listeners on UI components.
     */
    @FXML
    public void initialize() {
        // Create a new sandwich instance.
        sandwich = new Sandwich();
        // Initialize combo flag to false (i.e., not a combo by default).
        isCombo = false;

        // Populate the bread selection ComboBox with all available bread options.
        breadCombo.getItems().addAll(Bread.values());
        // Set the default bread selection.
        breadCombo.setValue(Bread.BRIOCHE);
        // Update the sandwich model when the bread selection changes.
        breadCombo.setOnAction(e -> updateSandwich());

        // Populate the protein selection ComboBox with available protein types.
        meatCombo.getItems().addAll(Protein.ROAST_BEEF, Protein.SALMON, Protein.CHICKEN);
        // When the protein selection is updated, adjust the sandwich accordingly.
        meatCombo.setOnAction(e -> updateSandwich());

        // Set up add-on check boxes to update the sandwich configuration when toggled.
        lettuce.setOnAction(e -> updateSandwich());
        tomatoes.setOnAction(e -> updateSandwich());
        onions.setOnAction(e -> updateSandwich());
        cheese.setOnAction(e -> updateSandwich());
        avocado.setOnAction(e -> updateSandwich());

        // Combo checkbox: update the combo flag and cost when toggled.
        combo.setOnAction(e -> {
            isCombo = combo.isSelected();
            updateCost();
        });

        // Configure the quantity spinner with a minimum of 1 and maximum of 10, defaulting to 1.
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        quantity.setValueFactory(valueFactory);
        // Listener to update sandwich quantity and cost when the spinner value changes.
        quantity.valueProperty().addListener((obs, oldVal, newVal) -> {
            sandwich.setQuantity(newVal);
            updateCost();
        });

        // Explicit listener for the meatCombo to also update the protein in the sandwich.
        meatCombo.setOnAction(e -> {
            sandwich.setProtein(meatCombo.getValue());
            updateSandwich();
        });
    }

    /**
     * Updates the sandwich model based on the current UI selections.
     * Sets the bread and protein types, clears previous add-ons and adds new ones,
     * and generates a descriptive name for the sandwich.
     */
    private void updateSandwich() {
        // Set bread and protein from the selections.
        sandwich.setBread(breadCombo.getValue());
        if (meatCombo.getValue() != null) {
            sandwich.setProtein(meatCombo.getValue());
        }

        // Clear any previously selected add-ons.
        sandwich.getAddons().clear();
        // Add selected add-ons.
        if (lettuce.isSelected()) {
            sandwich.addAddOns(AddOns.LETTUCE);
        }
        if (onions.isSelected()) {
            sandwich.addAddOns(AddOns.ONIONS);
        }
        if (tomatoes.isSelected()) {
            sandwich.addAddOns(AddOns.TOMATOES);
        }
        if (cheese.isSelected()) {
            sandwich.addAddOns(AddOns.CHEESE);
        }
        if (avocado.isSelected()) {
            sandwich.addAddOns(AddOns.AVOCADO);
        }

        // Build a descriptive name for the sandwich.
        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append(breadCombo.getValue())
                .append(" Sandwich with ")
                .append(meatCombo.getValue());
        // If add-ons are present, append them to the name.
        if (!sandwich.getAddons().isEmpty()) {
            nameBuilder.append(" (Add-ons: ");
            String addonsStr = sandwich.getAddons().stream()
                    .map(AddOns::toString)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            nameBuilder.append(addonsStr)
                    .append(")");
        }
        sandwich.setName(nameBuilder.toString());

        // Update the displayed cost.
        updateCost();
    }

    /**
     * Recalculates and updates the displayed cost of the sandwich.
     * Adds an extra fee if the sandwich is part of a combo order.
     */
    private void updateCost() {
        double totCost = sandwich.cost();
        // Add an additional $2.00 if the combo option is selected.
        if (isCombo) {
            totCost += 2.00;
        }
        // Format the cost as currency and update the text field.
        cost.setText(String.format("$%.2f", totCost));
    }

    /**
     * Event handler for the "Add to Order" button.
     * If the sandwich is marked as a combo, it opens the ComboView to allow additional customizations.
     * Otherwise, it adds the sandwich directly to the current order and closes the window.
     *
     * @param event the triggering ActionEvent.
     * @throws IOException if there is an error loading the ComboView.
     */
    @FXML
    public void handleAddToOrder(ActionEvent event) throws IOException {
        // Retrieve the current order from the MainController.
        Order current = MainController.getCurrentOrder();
        // If the sandwich is a combo, load the ComboView.
        if (isCombo) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs213project4/ComboView.fxml"));
                Parent root = loader.load();
                // Pass the sandwich model to the ComboController for further customization.
                ComboController control = loader.getController();
                control.setSandwich(sandwich);
                // Create and show a new stage (window) for the combo customization.
                Stage stage = new Stage();
                stage.setTitle("Choose Combo Items");
                stage.setScene(new Scene(root, 800, 800));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                Alert warning = new Alert(Alert.AlertType.ERROR);
                warning.setTitle("Oh no is broke :(");
                warning.setHeaderText("Could not load combo, please try again later...");
            }
        } else {
            // If not a combo, add the sandwich directly to the current order.
            current.addItem(sandwich);
            // Close the sandwich ordering window.
            ((Stage) addOrder.getScene().getWindow()).close();
        }
    }

    /**
     * Event handler for the "Cancel" button.
     * Closes the current window without saving any changes.
     *
     * @param event the triggering ActionEvent.
     */
    public void handleCancel(ActionEvent event) {
        ((Stage) cancel.getScene().getWindow()).close();
    }
}
