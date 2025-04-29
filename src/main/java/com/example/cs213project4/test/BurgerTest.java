package com.example.cs213project4.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Test class for the Burger class.
 */
public class BurgerTest {

    /**
     * Test case 1: Test a single patty burger with add-ons.
     * Expected cost = base BEEF_PATTY (6.99) + CHEESE (1.00) + LETTUCE (0.30) + TOMATOES (0.30)
     *             = 6.99 + 1.00 + 0.30 + 0.30 = 8.59
     */
    @Test
    public void testSinglePattyBurgerWithAddOns() {
        Burger burger = new Burger(Bread.BRIOCHE, false);
        burger.addAddOns(AddOns.CHEESE);
        burger.addAddOns(AddOns.LETTUCE);
        burger.addAddOns(AddOns.TOMATOES);
        burger.setQuantity(1);

        double expectedCost = 6.99 + 1.00 + 0.30 + 0.30;
        assertEquals(expectedCost, burger.cost(), 0.001);
    }

    /**
     * Test case 2: Test a double patty burger with add-ons.
     * Expected cost = base BEEF_PATTY (6.99) + surcharge for double patty (2.50) + CHEESE (1.00)
     *               + ONIONS (0.30) + AVOCADO (0.50)
     *             = 6.99 + 2.50 + 1.00 + 0.30 + 0.50 = 11.29
     */
    @Test
    public void testDoublePattyBurgerWithAddOns() {
        Burger burger = new Burger(Bread.PRETZEL, true);
        burger.addAddOns(AddOns.CHEESE);
        burger.addAddOns(AddOns.ONIONS);
        burger.addAddOns(AddOns.AVOCADO);
        burger.setQuantity(1);

        double expectedCost = 6.99 + 2.50 + 1.00 + 0.30 + 0.50;
        assertEquals(expectedCost, burger.cost(), 0.001);
    }
}
