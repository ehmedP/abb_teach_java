package src.seed;

import src.comparator.OrderComparators;
import src.model.Customer;
import src.model.Order;
import src.model.Product;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;

public final class OrderSeeder {

    private OrderSeeder() {
    }

    public static Queue<Order> seed(Map<Integer, Product> products, Map<Integer, Customer> customers) {

        Integer laptop = ProductSeeder.idOf(products, ProductSeeder.LAPTOP);
        Integer smartphone = ProductSeeder.idOf(products, ProductSeeder.SMARTPHONE);
        Integer headphones = ProductSeeder.idOf(products, ProductSeeder.HEADPHONES);
        Integer keyboard = ProductSeeder.idOf(products, ProductSeeder.KEYBOARD);
        Integer mouse = ProductSeeder.idOf(products, ProductSeeder.MOUSE);
        Integer monitor = ProductSeeder.idOf(products, ProductSeeder.MONITOR);
        Integer printer = ProductSeeder.idOf(products, ProductSeeder.PRINTER);
        Integer webcam = ProductSeeder.idOf(products, ProductSeeder.WEBCAM);
        Integer externalDisk = ProductSeeder.idOf(products, ProductSeeder.EXTERNAL_DISK);
        Integer powerbank = ProductSeeder.idOf(products, ProductSeeder.POWERBANK);

        Integer elvin = CustomerSeeder.idOf(customers, CustomerSeeder.ELVIN);
        Integer nigar = CustomerSeeder.idOf(customers, CustomerSeeder.NIGAR);
        Integer rashad = CustomerSeeder.idOf(customers, CustomerSeeder.RASHAD);
        Integer aysel = CustomerSeeder.idOf(customers, CustomerSeeder.AYSEL);
        Integer kamran = CustomerSeeder.idOf(customers, CustomerSeeder.KAMRAN);

        Queue<Order> orders = new ArrayDeque<>();

        orders.add(order(elvin, items(entry(laptop, 1), entry(mouse, 2))));
        orders.add(order(elvin, items(entry(smartphone, 1))));
        orders.add(order(elvin, items(entry(printer, 7))));

        orders.add(order(nigar, items(entry(ProductSeeder.UNKNOWN_PRODUCT_ID, 1))));
        orders.add(order(nigar, items(
                entry(laptop, 1),
                entry(ProductSeeder.ANOTHER_UNKNOWN_PRODUCT_ID, 2)
        )));

        orders.add(order(rashad, items(entry(headphones, 10))));
        orders.add(order(rashad, items(entry(keyboard, 1))));

        orders.add(order(aysel, items(entry(monitor, 50))));
        orders.add(order(aysel, items(entry(ProductSeeder.UNKNOWN_PRODUCT_ID, 1))));
        orders.add(order(aysel, items(entry(keyboard, 3))));
        orders.add(order(aysel, items(entry(webcam, 9))));
        orders.add(order(aysel, items(entry(ProductSeeder.ANOTHER_UNKNOWN_PRODUCT_ID, 1))));
        orders.add(order(aysel, items(entry(headphones, 99))));
        orders.add(order(aysel, items(entry(laptop, 1))));

        orders.add(order(kamran, items(entry(powerbank, 4))));
        orders.add(order(kamran, items(entry(powerbank, 5))));
        orders.add(order(kamran, items(
                entry(laptop, 2),
                entry(monitor, 1),
                entry(printer, 3),
                entry(externalDisk, 5)
        )));
        orders.add(order(kamran, items()));
        orders.add(order(kamran, items(entry(mouse, 0))));
        orders.add(order(kamran, items(entry(mouse, -1))));
        orders.add(order(kamran, items(
                entry(mouse, 3),
                entry(keyboard, 1),
                entry(laptop, 1)
        )));

        return orders;
    }

    public static TreeSet<Order> seedHistory(Map<Integer, Product> products, Map<Integer, Customer> customers) {

        Integer laptop = ProductSeeder.idOf(products, ProductSeeder.LAPTOP);
        Integer mouse = ProductSeeder.idOf(products, ProductSeeder.MOUSE);
        Integer smartphone = ProductSeeder.idOf(products, ProductSeeder.SMARTPHONE);
        Integer keyboard = ProductSeeder.idOf(products, ProductSeeder.KEYBOARD);

        Integer elvin = CustomerSeeder.idOf(customers, CustomerSeeder.ELVIN);
        Integer nigar = CustomerSeeder.idOf(customers, CustomerSeeder.NIGAR);
        Integer rashad = CustomerSeeder.idOf(customers, CustomerSeeder.RASHAD);

        LocalDateTime now = LocalDateTime.now();

        TreeSet<Order> history = new TreeSet<>(OrderComparators.BY_STATUS_THEN_CREATED_AT);

        history.add(completed(order(elvin, items(entry(laptop, 1))), now.minusDays(9)));
        history.add(completed(order(nigar, items(entry(mouse, 4))), now.minusDays(6)));
        history.add(completed(order(elvin, items(entry(smartphone, 2))), now.minusDays(3)));
        history.add(failed(order(rashad, items(entry(keyboard, 2))), now.minusDays(2)));
        history.add(failed(order(nigar, items(entry(laptop, 100))), now.minusHours(20)));

        return history;
    }

    private static Order order(Integer customerId, Map<Integer, Integer> items) {
        Order order = new Order(customerId);
        order.setItems(items);
        return order;
    }

    private static Order completed(Order order, LocalDateTime createdAt) {
        order.setCreatedAt(createdAt);
        order.markCompleted();
        return order;
    }

    private static Order failed(Order order, LocalDateTime createdAt) {
        order.setCreatedAt(createdAt);
        order.markFailed();
        return order;
    }

    @SafeVarargs
    private static Map<Integer, Integer> items(Map.Entry<Integer, Integer>... entries) {
        Map<Integer, Integer> items = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : entries) {
            items.put(entry.getKey(), entry.getValue());
        }
        return items;
    }

    private static Map.Entry<Integer, Integer> entry(Integer productId, Integer quantity) {
        return Map.entry(productId, quantity);
    }
}
