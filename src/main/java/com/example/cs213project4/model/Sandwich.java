/**
 * Sandwich.java
 *
 * Summary:
 * This class represents a sandwich menu item in the RU Fast Food application.
 * It extends the MenuItem class and includes properties specific to a sandwich,
 * such as the type of bread, protein, add-ons, and a name description.
 * The cost of a sandwich is calculated based on the protein cost plus any add-ons, multiplied by the quantity.
 *
 * Authors:
 * Anirudh Deveram
 * Karthik Penumetch
 */

package com.example.cs213project4.model;

import java.util.ArrayList;

public class Sandwich extends MenuItem {
	// The type of bread for the sandwich.
	protected Bread toast;
	// The protein used in the sandwich.
	protected Protein meat;
	// List of add-ons (extras) added to the sandwich.
	protected ArrayList<AddOns> extras;
	// The name of the sandwich; can be set to a custom string description.
	protected String name;

	/**
	 * Default constructor initializes the sandwich with default bread (BRIOCHE) and protein (ROAST_BEEF),
	 * an empty list for add-ons, a default quantity of 1, and no name.
	 */
	public Sandwich() {
		this.quantity = 1;
		this.toast = Bread.BRIOCHE;
		this.meat = Protein.ROAST_BEEF;
		this.extras = new ArrayList<>();
		this.name = null; // Name is not set by default.
	}

	/**
	 * Constructor to initialize a sandwich with a specified bread and protein.
	 *
	 * @param toast the type of bread to use.
	 * @param meat the type of protein to use.
	 */
	public Sandwich(Bread toast, Protein meat) {
		this.quantity = 1;
		this.toast = toast;
		this.meat = meat;
		this.extras = new ArrayList<>();
		this.name = null;
	}

	/**
	 * Returns the bread type of the sandwich.
	 *
	 * @return the bread.
	 */
	public Bread getBread() {
		return toast;
	}

	/**
	 * Sets the bread type of the sandwich.
	 *
	 * @param toast the new bread type.
	 */
	public void setBread(Bread toast) {
		this.toast = toast;
	}

	/**
	 * Returns the protein used in the sandwich.
	 *
	 * @return the protein.
	 */
	public Protein getProtein() {
		return meat;
	}

	/**
	 * Sets the protein used in the sandwich.
	 *
	 * @param meat the new protein.
	 */
	public void setProtein(Protein meat) {
		this.meat = meat;
	}

	/**
	 * Returns the list of add-ons for the sandwich.
	 *
	 * @return an ArrayList of AddOns.
	 */
	public ArrayList<AddOns> getAddons() {
		return extras;
	}

	/**
	 * Adds an add-on to the sandwich if it is not already present.
	 *
	 * @param extra the add-on to add.
	 */
	public void addAddOns(AddOns extra) {
		if (!extras.contains(extra)) {
			extras.add(extra);
		}
	}

	/**
	 * Removes an add-on from the sandwich.
	 *
	 * @param extra the add-on to remove.
	 */
	public void removeAddOns(AddOns extra) {
		extras.remove(extra);
	}

	/**
	 * Checks if an add-on is already added to the sandwich.
	 *
	 * @param extra the add-on to check.
	 * @return true if the add-on is present, false otherwise.
	 */
	public boolean hasAddOn(AddOns extra) {
		return extras.contains(extra);
	}

	/**
	 * Sets a custom name for the sandwich.
	 *
	 * @param name the custom name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves the custom name of the sandwich.
	 *
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Calculates the cost of the sandwich by adding the cost of the protein and any add-ons,
	 * then multiplying by the quantity.
	 *
	 * @return the total cost for the sandwich.
	 */
	@Override
	public double cost() {
		double total = meat.getCost();
		for (AddOns extra : extras) {
			total += extra.getCost();
		}
		return total * quantity;
	}

	/**
	 * Returns a string representation of the sandwich.
	 * If a custom name has been set, it returns that name; otherwise,
	 * it builds a description with the bread, protein, and any add-ons.
	 *
	 * @return the string representation of the sandwich.
	 */
	@Override
	public String toString() {
		if (name != null) {
			return name;
		}
		StringBuilder description = new StringBuilder();
		description.append(toast)
				.append(" Sandwich with ")
				.append(meat);
		if (!extras.isEmpty()) {
			description.append(" (Add-ons: ");
			for (int i = 0; i < extras.size(); i++) {
				description.append(extras.get(i));
				if (i < extras.size() - 1) {
					description.append(", ");
				}
			}
			description.append(")");
		}
		return description.toString();
	}
}
