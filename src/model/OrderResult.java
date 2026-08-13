package src.model;

import src.enums.OrderStatusEnum;

public record OrderResult(
        Integer orderId,
        OrderStatusEnum status,
        String message
) {
}