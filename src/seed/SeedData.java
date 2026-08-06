package src.seed;

import src.model.Customer;
import src.model.Order;
import src.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

public final class SeedData {

    private final Map<Integer, Product> products;
    private final Map<Integer, Customer> customers;
    private final Queue<Order> orders;
    private final TreeSet<Order> orderHistory;
    private final List<String> logs;
    private final PriorityQueue<Product> lowStockProducts;
    private final Map<Integer, Integer> failedOrdersByCustomer;

    private SeedData(
            Map<Integer, Product> products,
            Map<Integer, Customer> customers,
            Queue<Order> orders,
            TreeSet<Order> orderHistory,
            List<String> logs,
            PriorityQueue<Product> lowStockProducts,
            Map<Integer, Integer> failedOrdersByCustomer
    ) {
        this.products = products;
        this.customers = customers;
        this.orders = orders;
        this.orderHistory = orderHistory;
        this.logs = logs;
        this.lowStockProducts = lowStockProducts;
        this.failedOrdersByCustomer = failedOrdersByCustomer;
    }

    public static SeedData create() {

        Map<Integer, Product> products = ProductSeeder.seed();
        Map<Integer, Customer> customers = CustomerSeeder.seed();

        Queue<Order> orders = OrderSeeder.seed(products, customers);
        TreeSet<Order> orderHistory = OrderSeeder.seedHistory(products, customers);

        PriorityQueue<Product> lowStockProducts = ProductSeeder.seedLowStockQueue(products);

        List<String> logs = new ArrayList<>();
        logs.add("Seed data yükləndi: " + products.size() + " məhsul, "
                + customers.size() + " müştəri, " + orders.size() + " gözləyən sifariş, "
                + orderHistory.size() + " tarixçə qeydi.");

        Map<Integer, Integer> failedOrdersByCustomer = new HashMap<>();

        return new SeedData(products, customers, orders, orderHistory, logs, lowStockProducts, failedOrdersByCustomer);
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public Map<Integer, Customer> getCustomers() {
        return customers;
    }

    public Queue<Order> getOrders() {
        return orders;
    }

    public TreeSet<Order> getOrderHistory() {
        return orderHistory;
    }

    public List<String> getLogs() {
        return logs;
    }

    public PriorityQueue<Product> getLowStockProducts() {
        return lowStockProducts;
    }

    public Map<Integer, Integer> getFailedOrdersByCustomer() {
        return failedOrdersByCustomer;
    }

    public String customerName(Integer customerId) {
        Customer customer = customers.get(customerId);
        return customer == null ? "Naməlum müştəri #" + customerId : customer.getName();
    }
}
