package services;

import models.Shippable;
import models.CartItem;

import java.util.List;

public class ShippingService {
    public static void shipItems(List<Shippable> items) {
        double totalWeight = 0;
        System.out.println("** Shipment notice **");

        // Loop through each item and calculate the total weight
        for (Shippable item : items) {
            double itemWeight = item.getWeight();  // Get the weight of one item
            totalWeight += itemWeight;  // Add the weight of the item

            System.out.println(item.getName() + " - " + itemWeight * 1000 + "g");  // Print weight in grams
        }

        System.out.printf("Total package weight %.1fkg%n%n", totalWeight);  // Print total weight in kilograms
    }
}
