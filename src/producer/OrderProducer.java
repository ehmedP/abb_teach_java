package src.producer;

import src.factory.OrderFactory;
import src.model.Order;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class OrderProducer implements Runnable {

    private static final int MAX_ORDERS = 50;

    private final BlockingQueue<Order> queue;
    private final Random random = new Random();

    public OrderProducer(BlockingQueue<Order> queue) {
        this.queue = queue;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {

        try {

            for (int i = 0; i < MAX_ORDERS; i++) {

                if (Thread.currentThread().isInterrupted()) {
                    break;
                }

                Thread.sleep(random.nextInt(200, 500));

                queue.put(
                        OrderFactory.create(
                                "Product " + (i + 1),
                                random.nextInt(1, 10),
                                random.nextLong(500, 3000)
                        )
                );

                System.out.println("[OrderProducer]: Product "+ Optional.ofNullable(queue.peek()).map(Order::getProductName).orElse("Unknown") + " added to the queue");
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println("[OrderProducer]: Interrupted.");
        } finally {
            System.out.println("[OrderProducer]: Finished.");
        }

    }

}
