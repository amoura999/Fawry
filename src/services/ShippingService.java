package services;

import models.Shippable;

import java.util.List;

public class ShippingService {
    public static void shipItems(List<Shippable> items) {
        double totalWeight = 0;
        System.out.println("** Shipment notice **");
        for (Shippable item : items) {
            System.out.println(item.getName() + " - " + item.getWeight() * 1000 + "g");
            totalWeight += item.getWeight();
        }
        System.out.printf("Total package weight %.1fkg%n%n", totalWeight);
    }
}