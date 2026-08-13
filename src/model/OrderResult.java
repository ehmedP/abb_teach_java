package src.model;

import src.enums.OrderStatusEnum;

public record OrderResult(
        Integer orderId,
        OrderStatusEnum status,
        String message
) {

    public Boolean isSuccess() {
        return this.status.equals(OrderStatusEnum.SUCCESS);
    }

    public Boolean isRejected() {
        return this.status.equals(OrderStatusEnum.REJECTED);
    }

    public Boolean isTimeOut() {
        return this.status.equals(OrderStatusEnum.TIMEOUT_CANCELLED);
    }

    public Boolean isPartial() {
        return this.status.equals(OrderStatusEnum.PARTIAL);
    }
}