package src.worker;

import src.enums.OrderStatusEnum;
import src.model.OrderResult;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class MonitorThread implements Runnable {

    private final BlockingQueue<?> queue;
    private final ConcurrentHashMap<Integer, OrderResult> results;
    private final ThreadPoolExecutor workerPool;

    public MonitorThread(
            BlockingQueue<?> queue,
            ConcurrentHashMap<Integer, OrderResult> results,
            ThreadPoolExecutor workerPool
    ) {
        this.queue = queue;
        this.results = results;
        this.workerPool = workerPool;
    }

    @Override
    public void run() {

        try {

            while (!Thread.currentThread().isInterrupted()) {

                printDashboard();

                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(
                    "[MONITOR] Interrupted. Shutting down."
            );
        }
    }

    private void printDashboard() {

        long completed = count(OrderStatusEnum.SUCCESS);
        long rejected = count(OrderStatusEnum.REJECTED);
        long timeout = count(OrderStatusEnum.TIMEOUT_CANCELLED);

        System.out.println();
        System.out.println("======================================== DASHBOARD ========================================");
        System.out.println("Queue       : " + queue.size());
        System.out.println("Completed   : " + completed);
        System.out.println("Rejected    : " + rejected);
        System.out.println("Timeout     : " + timeout);
        System.out.println("Active      : " + workerPool.getActiveCount());
        System.out.println("===========================================================================================");
    }

    private long count(OrderStatusEnum status) {

        return results.values()
                .stream()
                .filter(result -> result.status() == status)
                .count();
    }
}