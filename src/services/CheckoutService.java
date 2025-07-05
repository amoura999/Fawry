package services;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    public static void checkout(Customer customer, Cart cart) throws Exception {
        if (cart.isEmpty()) throw new Exception("Cart is empty");

        List<Shippable> shippableItems = new ArrayList<>();
        double subtotal = 0;

        for (CartItem item : cart.getItems()) {
            if (item.product.isExpired()) throw new Exception(item.product.getName() + " is expired.");
            if (item.quantity > item.product.getQuantity()) throw new Exception("Insufficient stock for " + item.product.getName());

            subtotal += item.getTotalPrice();
            if (item.product.isShippable()) {
                Shippable shippable = (Shippable) item.product;
                for (int i = 0; i < item.quantity; i++) {
                    shippableItems.add(shippable);
                }
            }
        }

        double shipping = shippableItems.isEmpty() ? 0 : 30;
        double total = subtotal + shipping;

        if (!customer.hasSufficientBalance(total)) throw new Exception("Insufficient balance.");

        for (CartItem item : cart.getItems()) {
            item.product.reduceQuantity(item.quantity);
        }
        customer.deductBalance(total);

        if (!shippableItems.isEmpty()) ShippingService.shipItems(shippableItems);

        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %-12s %.0f%n", item.quantity, item.product.getName(), item.getTotalPrice());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal         %.0f%n", subtotal);
        System.out.printf("Shipping         %.0f%n", shipping);
        System.out.printf("Amount           %.0f%n", total);
        System.out.printf("Remaining Balance %.2f%n", customer.getBalance());
    }
}