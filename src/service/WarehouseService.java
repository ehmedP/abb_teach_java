package src.service;

import src.connection.WarehouseConnection;
import src.exception.CriticalSystemFailureException;
import src.exception.InvalidOrderException;
import src.exception.ProductOutOfStockException;
import src.exception.WarehouseConnectionException;
import src.model.Order;
import src.model.OrderResult;
import src.model.Product;

import java.util.*;

public class WarehouseService {

    private final Map<Integer, Product> products;
    private final Queue<Order> orders;
    private final TreeSet<Order> orderHistory;
    private final List<String> logs;
    private final PriorityQueue<Product> productQueue;
    private final Map<Integer, Integer> failedProductsByCustomer;

    public WarehouseService(
            Map<Integer, Product> products,
            Queue<Order> orders,
            TreeSet<Order> orderHistory,
            List<String> logs,
            PriorityQueue<Product> productQueue,
            Map<Integer, Integer> failedProductsByCustomer
    ) {
        this.products = products;
        this.orders = orders;
        this.orderHistory = orderHistory;
        this.logs = logs;
        this.productQueue = productQueue;
        this.failedProductsByCustomer = failedProductsByCustomer;
    }

    public OrderResult processOrder(Order order) throws InvalidOrderException, ProductOutOfStockException, WarehouseConnectionException {

        try (WarehouseConnection conn = new WarehouseConnection()) {

            for (Map.Entry<Integer, Integer> entry : order.getItems().entrySet()) {

                Product product = findProductById(entry.getKey())
                        .orElseThrow(() -> new InvalidOrderException("Məhsul tapılmadı: " + entry.getKey()));

                if (product.getStock() < entry.getValue()) {
                    throw new ProductOutOfStockException("Stok kifayət etmir: " + product.getName());
                }
            }

            order.markCompleted();

            return new OrderResult(order.getId(), true, null);

        } catch (ProductOutOfStockException | InvalidOrderException e) {

            order.markFailed();

            incrementFailureCount(order.getCustomerId());

            if (getFailureCount(order.getCustomerId()) >= 3) {
                throw new CriticalSystemFailureException("Müştəri üçün kritik xəta həddi aşıldı: " + order.getCustomerId(), e);
            }

            throw e;
        } catch (WarehouseConnectionException e) {

            order.markFailed();

            logRecord("Connection failed for order: " + order.getId() + " for customer: " + order.getCustomerId() + ". Error: " + e.getMessage());

            throw e;

        } finally {

            logRecord("Order processed: " + order.getId() + " for customer: " + order.getCustomerId());
        }
    }

    public void incrementFailureCount(Integer customerId) {
        failedProductsByCustomer.put(customerId, failedProductsByCustomer.getOrDefault(customerId, 0) + 1);
    }

    public Integer getFailureCount(Integer customerId) {
        return failedProductsByCustomer.get(customerId);
    }

    public Optional<Product> findProductById(Integer id) {
        return Optional.ofNullable(products.get(id));
    }

    public void logRecord(String message) {
        logs.add(message);
    }

    public Map<Integer, Product> getProducts() {
        return products;
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

    public PriorityQueue<Product> getProductQueue() {
        return productQueue;
    }

    public Map<Integer, Integer> getFailedProductsByCustomer() {
        return failedProductsByCustomer;
    }
}
