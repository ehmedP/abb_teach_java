package src.comparator;

import src.model.Order;

import java.util.Comparator;

public final class OrderComparators {

    public static final Comparator<Order> BY_CREATED_AT =
            Comparator.comparing(Order::getCreatedAt)
                    .thenComparing(Order::getId);

    public static final Comparator<Order> BY_STATUS_THEN_CREATED_AT =
            Comparator.comparing(Order::getStatus)
                    .thenComparing(Order::getCreatedAt)
                    .thenComparing(Order::getId);

    private OrderComparators() {
    }
}
