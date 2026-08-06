package src.model;

import src.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order {

    private static Integer nextId = 1;

    private final Integer id;
    private Integer customerId;

    // productId => count
    private Map<Integer, Integer> items;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public Order(Integer customerId) {
        this.id = nextId++;

        this.customerId = customerId;
        this.items = new HashMap<>();
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Integer, Integer> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void markProcessing() {
        this.status = OrderStatus.PROCESSING;
    }

    public void markCompleted() {
        this.status = OrderStatus.COMPLETED;
    }

    public void markFailed() {
        this.status = OrderStatus.FAILED;
    }

    public boolean isPending() {
        return this.status == OrderStatus.PENDING;
    }

    public boolean isProcessing() {
        return this.status == OrderStatus.PROCESSING;
    }

    public boolean isCompleted() {
        return this.status == OrderStatus.COMPLETED;
    }

    public boolean isFailed() {
        return this.status == OrderStatus.FAILED;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", items=" + items +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
