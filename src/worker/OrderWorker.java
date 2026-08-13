package src.worker;

import src.enums.OrderStatusEnum;
import src.model.Order;
import src.model.OrderResult;
import src.validator.PaymentValidator;
import src.validator.StockValidator;

import java.util.concurrent.*;

public class OrderWorker implements Callable<OrderResult> {

    private final Order order;

    private final PaymentValidator paymentValidator;
    private final StockValidator stockValidator;

    public OrderWorker(Order order) {
        this.order = order;
        this.paymentValidator = new PaymentValidator();
        this.stockValidator = new StockValidator();
    }

    public Order getOrder() {
        return order;
    }

    public PaymentValidator getPaymentValidator() {
        return paymentValidator;
    }

    public StockValidator getStockValidator() {
        return stockValidator;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     */
    @Override
    public OrderResult call() {

        ExecutorService validationExecutorService = Executors.newFixedThreadPool(2);

        try {

            Future<Boolean> validPaymentFuture = validationExecutorService.submit(() -> paymentValidator.validate(order));
            Future<Boolean> validStockFuture = validationExecutorService.submit(() -> stockValidator.validate(order));

            Boolean validPayment;
            Boolean validStock;

            try {

                validPayment = validPaymentFuture.get(1, TimeUnit.SECONDS);
                validStock = validStockFuture.get(1, TimeUnit.SECONDS);

            } catch (TimeoutException | ExecutionException e) {

                validPaymentFuture.cancel(true);
                validStockFuture.cancel(true);

                return new OrderResult(
                        order.getId(),
                        OrderStatusEnum.REJECTED,
                        "Payment/stock validation timeout"
                );
            }

            if (!validPayment || !validStock) {

                return new OrderResult(
                        order.getId(),
                        OrderStatusEnum.REJECTED,
                        "Payment or stock validation failed"
                );
            }

            return processOrder();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            return new OrderResult(
                    order.getId(),
                    OrderStatusEnum.TIMEOUT_CANCELLED,
                    "Worker interrupted"
            );

        } finally {
            validationExecutorService.shutdownNow();
        }
    }

    private OrderResult processOrder() {

        long remaining = order.getProcessingDuration();

        try {

            while (remaining > 0) {

                if (Thread.currentThread().isInterrupted()) {

                    return new OrderResult(
                            order.getId(),
                            OrderStatusEnum.TIMEOUT_CANCELLED,
                            "Order processing interrupted"
                    );
                }

                long sleepTime = Math.min(100, remaining);

                Thread.sleep(sleepTime);

                remaining -= sleepTime;
            }

            return new OrderResult(
                    order.getId(),
                    OrderStatusEnum.SUCCESS,
                    "Order processed successfully"
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            return new OrderResult(
                    order.getId(),
                    OrderStatusEnum.TIMEOUT_CANCELLED,
                    "Order processing interrupted"
            );
        }
    }

}
