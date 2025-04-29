/**
 * BurgerController.java
 *
 * Summary:
 * This controller manages the UI interactions for customizing a burger order within the application.
 * It handles the selection of bread type, patty configuration, optional addons, quantity,
 * and also supports creating a combo by invoking an additional view for combo items.
 * The controller updates the burger image, calculates cost adjustments (including combo additions),
 * and allows users to either add the burger (or combo) to their order or cancel the selection.
 *
 * @authors
 * Anirudh Deveram
 * Karthik Penumetch
 */

package com.example.cs213project4.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.example.cs213project4.model.*;
import javafx.event.ActionEvent;
import java.io.IOException;

public class BurgerController {

    // FXML Components bound from the view
    @FXML
    private ComboBox<Bread> breadComboBox;     // Dropdown for selecting burger bread type
    @FXML
    private RadioButton singlePattyRadio;      // Radio button for single patty selection
    @FXML
    private RadioButton doublePattyRadio;      // Radio button for double patty selection
    @FXML
    private CheckBox lettuceCheckBox;          // Checkbox for adding lettuce
    @FXML
    private CheckBox tomatoesCheckBox;         // Checkbox for adding tomatoes
    @FXML
    private CheckBox onionsCheckBox;           // Checkbox for adding onions
    @FXML
    private CheckBox avocadoCheckBox;          // Checkbox for adding avocado
    @FXML
    private CheckBox cheeseCheckBox;           // Checkbox for adding cheese
    @FXML
    private CheckBox comboCheckBox;            // Checkbox to mark the burger as a combo
    @FXML
    private Spinner<Integer> quantitySpinner;  // Spinner for selecting burger quantity
    @FXML
    private Label priceLabel;                  // Label to display the calculated price
    @FXML
    private Button addToOrderButton;           // Button to add burger/combo to current order
    @FXML
    private Button cancelButton;               // Button to cancel the current selection
    @FXML
    private ImageView burgerImageView;         // ImageView to display the burger image

    // ToggleGroup for patty selection
    private ToggleGroup patty;
    // The Burger model object to hold the current burger configuration
    private Burger burger;
    // Boolean flag to indicate if the burger is to be treated as a combo
    private boolean isCombo;

    /**
     * Initializes the controller. Configures the UI components, sets default selections,
     * and registers event listeners for interactive updates on the burger configuration.
     * This method is automatically called after the FXML file has been loaded.
     *
     * @throws IOException if an error occurs loading the burger image.
     */
    public void initialize() throws IOException {
        // Create a new Burger instance and set default combo flag to false.
        burger = new Burger();
        isCombo = false;

        // Configure the breadComboBox with available bread types from the Bread enum.
        breadComboBox.getItems().addAll(Bread.values());
        breadComboBox.setValue(Bread.BRIOCHE); // Set default to BRIOCHE.
        breadComboBox.setOnAction(e -> updateBurger());

        // Set up the patty selection with a ToggleGroup to allow one selection at a time.
        patty = new ToggleGroup();
        singlePattyRadio.setToggleGroup(patty);
        doublePattyRadio.setToggleGroup(patty);
        singlePattyRadio.setSelected(true);  // Default to single patty.
        singlePattyRadio.setOnAction(e -> updateBurger());
        doublePattyRadio.setOnAction(e -> updateBurger());

        // Set up event handlers for addon CheckBoxes that update the burger configuration.
        lettuceCheckBox.setOnAction(e -> updateBurger());
        tomatoesCheckBox.setOnAction(e -> updateBurger());
        onionsCheckBox.setOnAction(e -> updateBurger());
        cheeseCheckBox.setOnAction(e -> updateBurger());
        avocadoCheckBox.setOnAction(e -> updateBurger());

        // Combo CheckBox toggles the combo flag and updates the cost accordingly.
        comboCheckBox.setOnAction(e -> {
            isCombo = comboCheckBox.isSelected();
            updateCost();
        });

        // Configure quantitySpinner with a minimum of 1 and maximum of 10, defaulting to 1.
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        quantitySpinner.setValueFactory(valueFactory);
        quantitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            burger.setQuantity(newValue);
            updateCost();
        });

        // Load and set the burger image.
        try {
            // Note: Resource path uses the class loader to get the burger image.
            Image picture = new Image(String.valueOf(getClass().getResource("com/example/cs213project4/image/burger.png")));
            burgerImageView.setImage(picture);
        } catch (Exception e) {
            e.printStackTrace();
            Alert warning = new Alert(Alert.AlertType.ERROR);
            warning.setTitle("Oh no is broke :(");
            warning.setHeaderText("Could not load burger, please try again later... ");
        }

        // Call updateBurger() to ensure the burger configuration and cost are initialized.
        updateBurger();
    }

    /**
     * Updates the burger configuration based on the current UI selections.
     * This method sets the selected bread, patty type, and clears then adds the selected addons.
     * It also triggers a cost recalculation.
     */
    private void updateBurger() {
        // Set the burger's bread type from the selected value in the ComboBox.
        burger.setBread(breadComboBox.getValue());
        // Set the burger's patty configuration based on the selected radio button.
        burger.setDoublePatty(doublePattyRadio.isSelected());
        // Clear any previously added addons.
        burger.getAddons().clear();

        // Add optional addons based on the checked CheckBoxes.
        if (lettuceCheckBox.isSelected()) {
            burger.addAddOns(AddOns.LETTUCE);
        }
        if (onionsCheckBox.isSelected()) {
            burger.addAddOns(AddOns.ONIONS);
        }
        if (tomatoesCheckBox.isSelected()) {
            burger.addAddOns(AddOns.TOMATOES);
        }
        if (cheeseCheckBox.isSelected()) {
            burger.addAddOns(AddOns.CHEESE);
        }
        if (avocadoCheckBox.isSelected()) {
            burger.addAddOns(AddOns.AVOCADO);
        }
        // Update the cost display after configuration changes.
        updateCost();
    }

    /**
     * Calculates and updates the displayed cost label.
     * The cost is computed via the burger model and, if marked as a combo,
     * an extra amount is added before formatting and displaying the result.
     */
    private void updateCost() {
        // Calculate the base burger cost.
        double cost = burger.cost();
        // Add additional combo cost if the combo option is selected.
        if (isCombo) {
            cost += 2.00;
        }
        // Display the cost in currency format.
        priceLabel.setText(String.format("$%.2f", cost));
    }

    /**
     * Event handler for adding the burger (or burger combo) to the order.
     * If the burger is a combo, it loads an additional view for combo selection.
     * Otherwise, it adds the burger directly to the current order and closes the window.
     *
     * @param event the ActionEvent triggered by clicking the "Add to Order" button.
     */
    @FXML
    private void handleAddToOrder(ActionEvent event) {
        Order current = MainController.getCurrentOrder();
        // If combo option selected, open the combo view to choose additional combo items.
        if (isCombo) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs213project4/ComboView.fxml"));
                Parent root = loader.load();
                // Pass the current burger to the ComboController for further combo configuration.
                ComboController control = loader.getController();
                control.setBurger(burger);
                Stage stage = new Stage();
                stage.setTitle("Choose Combo Items");
                stage.setScene(new Scene(root, 800, 800));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                Alert warning = new Alert(Alert.AlertType.ERROR);
                warning.setTitle("Oh no is broke :(");
                warning.setHeaderText("Could not load combo, please try again later... ");
            }
        } else {
            // For non-combo burgers, simply add to current order.
            current.addItem(burger);
            // Close the burger window after addition.
            ((Stage) addToOrderButton.getScene().getWindow()).close();
        }
    }

    /**
     * Event handler for the cancel button.
     * Closes the current window without saving any changes.
     *
     * @param event the ActionEvent triggered by clicking the cancel button.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
