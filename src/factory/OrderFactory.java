package src.factory;

import src.model.Order;

public class OrderFactory {

    public static Order create(String productName, Integer quantity, Long processingDuration) {
        validate(productName, quantity, processingDuration);

        return new Order(productName, quantity, processingDuration);
    }

    private static void validate(String productName, Integer quantity, Long processingDuration) {

        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        if (processingDuration == null || processingDuration <= 0) {
            throw new IllegalArgumentException("Processing duration must be greater than 0");
        }

    }
}