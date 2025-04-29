package com.example.cs213project4.model;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the Sandwich class.
 */
public class SandwichTest {

    /**
     * Test case 1: Test the default sandwich.
     * The default sandwich uses Bread.BRIOCHE and Protein.ROAST_BEEF.
     * Expected cost = cost of ROAST_BEEF. (For example, if ROAST_BEEF costs 10.99.)
     */
    @Test
    public void testDefaultSandwichCost() {
        Sandwich sandwich = new Sandwich();
        sandwich.setQuantity(1);
        double expectedCost = Protein.ROAST_BEEF.getCost(); // Expected: 10.99 (as defined)
        assertEquals(expectedCost, sandwich.cost(), 0.001);
    }

    /**
     * Test case 2: Test a sandwich with add-ons.
     * Create a sandwich with Bread.BRIOCHE and Protein.CHICKEN.
     * Add CHEESE (1.00) and LETTUCE (0.30).
     * Expected cost = cost of CHICKEN + CHEESE + LETTUCE.
     */
    @Test
    public void testSandwichWithAddOns() {
        Sandwich sandwich = new Sandwich(Bread.BRIOCHE, Protein.CHICKEN);
        sandwich.addAddOns(AddOns.CHEESE);
        sandwich.addAddOns(AddOns.LETTUCE);
        sandwich.setQuantity(1);

        double expectedCost = Protein.CHICKEN.getCost() + AddOns.CHEESE.getCost() + AddOns.LETTUCE.getCost();
        // For example, if CHICKEN costs 8.99, then expected = 8.99 + 1.00 + 0.30 = 10.29
        assertEquals(expectedCost, sandwich.cost(), 0.001);
    }

    /**
     * Test case 3: Test setting a custom name.
     * If a custom name is set, the toString() method should return that name.
//     */
//    @Test
//    public void testSandwichCustomName() {
//        Sandwich sandwich = new Sandwich(Bread.BRIOCHE, Protein.CHICKEN);
//        String customName = "Custom Chicken Sandwich Deluxe";
//        sandwich.setName(customName);
//        assertEquals(customName, sandwich.toString());
//    }
}
