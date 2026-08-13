package src.provider;

import src.model.Order;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class QueueProvider {

    private final BlockingQueue<Order> queue;

    public QueueProvider() {
        this.queue = new LinkedBlockingDeque<>(100);
    }

    public BlockingQueue<Order> getQueue() {
        return queue;
    }

}
