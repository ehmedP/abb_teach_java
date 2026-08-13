package src.model;

import java.util.Objects;

public class Order {

    private static int nextId = 1;

    private final Integer id;
    private String productName;
    private Integer quantity;
    private Integer processingDuration;

    public Order(String ProductName, Integer quantity, Integer processingDuration) {
        this.id = nextId++;

        this.productName = ProductName;
        this.quantity = quantity;
        this.processingDuration = processingDuration;
    }

    public Integer getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProcessingDuration() {
        return processingDuration;
    }

    public void setProcessingDuration(Integer processingDuration) {
        this.processingDuration = processingDuration;
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
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", processingDuration=" + processingDuration +
                '}';
    }
}
