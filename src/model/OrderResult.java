package src.model;

import java.util.Objects;

public class OrderResult {

    private static Integer nextId = 1;

    private final Integer id;
    private final Integer orderId;
    private final Boolean success;
    private final String errorMessage;

    public OrderResult(Integer orderId, Boolean success, String errorMessage) {
        this.id = nextId++;

        this.orderId = orderId;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public Integer getId() {
        return id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderResult that = (OrderResult) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderResult{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", success=" + success +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
