import models.*;
import services.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            // Test Case 1: Normal Checkout
            testNormalCheckout();

            // Test Case 2: Insufficient Balance
            testInsufficientBalance();

            // Test Case 3: Expired Product
            testExpiredProduct();

            // Test Case 4: Exceeding Stock
            testExceedingStock();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Test Case 1: Normal Checkout
    public static void testNormalCheckout() throws Exception {
        Customer customer = new Customer("Alice", 1000.0);

        Product cheese = new ExpirableProduct("Cheese", 100, 5, LocalDate.now().plusDays(3));
        Product biscuits = new ExpirableProduct("Biscuits", 150, 2, LocalDate.now().plusDays(1));
        Product scratchCard = new Product("ScratchCard", 50, 10);
        Product tv = new ShippableProduct("TV", 300, 3, 15.0);
        Product test = new ShippableProduct("Test", 50, 3, 20.0);

        Cart cart = new Cart();
        cart.add(cheese, 2);  // 2x Cheese
        cart.add(biscuits, 1); // 1x Biscuits
        cart.add(tv, 1);       // 1x TV
        cart.add(scratchCard, 1); // 1x ScratchCard
        cart.add(test, 1); // 1x ScratchCard


        System.out.println("Test Case 1: Normal Checkout");
        CheckoutService.checkout(customer, cart);
    }

    // Test Case 2: Insufficient Balance
    public static void testInsufficientBalance() throws Exception {
        Customer customer = new Customer("Bob", 500.0); // Insufficient balance

        Product cheese = new ExpirableProduct("Cheese", 100, 5, LocalDate.now().plusDays(3));
        Product biscuits = new ExpirableProduct("Biscuits", 150, 2, LocalDate.now().plusDays(1));
        Product tv = new ShippableProduct("TV", 300, 3, 15.0);
        Product scratchCard = new Product("ScratchCard", 50, 10);

        Cart cart = new Cart();
        cart.add(cheese, 2);  // 2x Cheese
        cart.add(biscuits, 1); // 1x Biscuits
        cart.add(tv, 1);       // 1x TV
        cart.add(scratchCard, 1); // 1x ScratchCard

        System.out.println("Test Case 2: Insufficient Balance");
        try {
            CheckoutService.checkout(customer, cart); // This should throw an exception due to insufficient balance
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage()); // Expecting "Insufficient balance."
        }
    }

    // Test Case 3: Expired Product
    public static void testExpiredProduct() throws Exception {
        Customer customer = new Customer("Charlie", 1000.0);

        Product cheese = new ExpirableProduct("Cheese", 100, 5, LocalDate.now().minusDays(1));  // Expired
        Product biscuits = new ExpirableProduct("Biscuits", 150, 2, LocalDate.now().plusDays(1));
        Product tv = new ShippableProduct("TV", 300, 3, 15.0);
        Product scratchCard = new Product("ScratchCard", 50, 10);

        Cart cart = new Cart();
        cart.add(cheese, 1);  // 1x Expired Cheese
        cart.add(biscuits, 1); // 1x Biscuits
        cart.add(tv, 1);       // 1x TV
        cart.add(scratchCard, 1); // 1x ScratchCard

        System.out.println("Test Case 3: Expired Product");
        try {
            CheckoutService.checkout(customer, cart); // This should throw an exception due to expired cheese
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage()); // Expecting "Cheese is expired."
        }
    }

    // Test Case 4: Exceeding Stock
    public static void testExceedingStock() throws Exception {
        Customer customer = new Customer("Dave", 1000.0);

        Product cheese = new ExpirableProduct("Cheese", 100, 2, LocalDate.now().plusDays(3)); // Only 2 in stock
        Product biscuits = new ExpirableProduct("Biscuits", 150, 2, LocalDate.now().plusDays(1));
        Product tv = new ShippableProduct("TV", 300, 3, 15.0);
        Product scratchCard = new Product("ScratchCard", 50, 10);

        Cart cart = new Cart();
        cart.add(cheese, 3);  // Attempting to add 3x Cheese (only 2 available)
        cart.add(biscuits, 1); // 1x Biscuits
        cart.add(tv, 1);       // 1x TV
        cart.add(scratchCard, 1); // 1x ScratchCard

        System.out.println("Test Case 4: Exceeding Stock");
        try {
            CheckoutService.checkout(customer, cart); // This should throw an exception due to insufficient stock
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage()); // Expecting "Insufficient stock for Cheese"
        }
    }
}
