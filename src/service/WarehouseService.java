package src.service;

import src.connection.WarehouseConnection;
import src.exception.CriticalSystemFailureException;
import src.exception.InvalidOrderException;
import src.exception.ProductOutOfStockException;
import src.exception.WarehouseConnectionException;
import src.model.Order;
import src.model.Product;

import java.util.*;

public class WarehouseService {

    private Map<Integer, Product> products;
    private Queue<Order> orders;
    private TreeSet<Order> orderHistory;
    private List<String> logs;
    private PriorityQueue<Product> productQueue;
    private Map<Integer, Integer> failedProductsByCustomer;

    public OrderResult processOrder(Order order) throws InvalidOrderException, ProductOutOfStockException, WarehouseConnectionException {

        try (WarehouseConnection conn = new WarehouseConnection()) {

            for (Map.Entry<Integer, Integer> entry : order.getItems().entrySet()) {

                Product product = products.get(entry.getKey());

                if (product == null) {
                    throw new InvalidOrderException("Məhsul tapılmadı: " + entry.getKey());
                }

                if (product.getStock() < entry.getValue()) {
                    throw new ProductOutOfStockException("Stok kifayət etmir: " + product.getName());
                }
            }

            return new OrderResult(order.getId(), true, null);

        } catch (ProductOutOfStockException | InvalidOrderException e) {
            incrementFailureCount(order.getCustomerId());

            if (getFailureCount(order.getCustomerId()) >= 3) {
                throw new CriticalSystemFailureException("Müştəri üçün kritik xəta həddi aşıldı: " + order.getCustomerId(), e);
            }

            throw e;
        } finally {

            logs.add("Order processed: " + order.getId() + " for customer: " + order.getCustomerId());
        }
    }

    public void incrementFailureCount(Integer customerId) {
        failedProductsByCustomer.put(customerId, failedProductsByCustomer.getOrDefault(customerId, 0) + 1);
    }

    public Integer getFailureCount(Integer customerId) {
        return failedProductsByCustomer.get(customerId);
    }

    public Optional<Product> findProductById(Integer id) {
        return products.get(id);
    }

}
