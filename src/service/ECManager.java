package src.service;

import src.enums.OrderStatusEnum;
import src.model.Order;
import src.model.OrderResult;
import src.producer.OrderProducer;
import src.provider.QueueProvider;
import src.validator.PaymentValidator;
import src.validator.StockValidator;
import src.worker.MonitorThread;
import src.worker.OrderWorker;

import java.util.concurrent.*;

public class ECManager {

    private static final int WORKER_COUNT = 5;
    private static final int MAX_RUNTIME_SECONDS = 20;

    public static void execute() {

        QueueProvider queueProvider = new QueueProvider();

        BlockingQueue<Order> queue =
                queueProvider.getQueue();

        ConcurrentHashMap<Integer, OrderResult> results =
                new ConcurrentHashMap<>();

        ConcurrentHashMap<Integer, Order> inFlight =
                new ConcurrentHashMap<>();

        ThreadPoolExecutor workerPool =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(WORKER_COUNT);

        Thread producer = new Thread(
                new OrderProducer(queue),
                "Producer"
        );

        Thread monitor = new Thread(
                new MonitorThread(queue, results, workerPool),
                "Monitor"
        );

        producer.start();
        monitor.start();

        long startTime = System.currentTimeMillis();

        try {

            while (true) {

                if (isSystemFinished(producer, queue, results)) {
                    break;
                }

                long elapsed = System.currentTimeMillis() - startTime;

                if (elapsed >= TimeUnit.SECONDS.toMillis(MAX_RUNTIME_SECONDS)) {

                    System.out.println("!!! GLOBAL TIMEOUT !!!");

                    cancelSystem(producer, workerPool, queue, inFlight, results);

                    break;
                }

                submitAvailableOrders(queue, workerPool, inFlight, results);

                Thread.sleep(50);

            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            cancelSystem(producer, workerPool, queue, inFlight, results);
        }

        finishProducer(producer);

        shutdownWorkerPool(workerPool);

        stopMonitor(monitor);

        printFinalReport(results);

    }

    private static void submitAvailableOrders(
            BlockingQueue<Order> queue,
            ExecutorService workerPool,
            ConcurrentHashMap<Integer, Order> inFlight,
            ConcurrentHashMap<Integer, OrderResult> results
    ) {

        Order order;

        while ((order = queue.poll()) != null) {

            Order finalOrder = order;

            inFlight.put(finalOrder.getId(), finalOrder);

            workerPool.submit(() -> {

                OrderWorker worker = new OrderWorker(finalOrder);

                OrderResult result = worker.call();

                results.put(finalOrder.getId(), result);

                inFlight.remove(finalOrder.getId());

                return result;
            });
        }
    }

    private static void stopMonitor(Thread monitor) {

        if (monitor.isAlive()) {
            monitor.interrupt();
        }

        try {
            monitor.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private static void shutdownWorkerPool(ExecutorService workerPool) {

        workerPool.shutdown();

        try {

            if (workerPool.awaitTermination(5, TimeUnit.SECONDS)) {

                workerPool.shutdownNow();
                System.out.println("Worker pool terminated successfully.");
            } else {
                System.out.println("Worker pool did not terminate within the specified time.");
                workerPool.shutdownNow();
            }

        } catch (InterruptedException e) {

            workerPool.shutdownNow();

            Thread.currentThread().interrupt();
        }

    }

    private static void finishProducer(Thread producer) {

        if (producer.isAlive()) {
            producer.interrupt();
        }

        try {
            producer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private static void cancelSystem(
            Thread producer,
            ExecutorService workerPool,
            BlockingQueue<Order> queue,
            ConcurrentHashMap<Integer, Order> inFlight,
            ConcurrentHashMap<Integer, OrderResult> results
    ) {

        producer.interrupt();
        workerPool.shutdownNow();

        Order order;

        while ((order = queue.poll()) != null) {

            results.put(order.getId(),
                    new OrderResult(
                            order.getId(),
                            OrderStatusEnum.TIMEOUT_CANCELLED,
                            "Order never started"
                    )
            );
        }

        for (Order pending : inFlight.values()) {

            results.putIfAbsent(pending.getId(),
                    new OrderResult(
                            pending.getId(),
                            OrderStatusEnum.TIMEOUT_CANCELLED,
                            "Order cancelled during processing"
                    )
            );
        }

        inFlight.clear();
    }

    private static void printFinalReport(ConcurrentHashMap<Integer, OrderResult> results) {

        long total = results.size();

        long success = results.values().stream()
                .filter(OrderResult::isSuccess)
                .count();

        long rejected = results.values().stream()
                .filter(OrderResult::isRejected)
                .count();

        long timeout = results.values().stream()
                .filter(OrderResult::isTimeOut)
                .count();

        long partial = results.values().stream()
                .filter(OrderResult::isPartial)
                .count();

        System.out.println();
        System.out.println("==========================================");
        System.out.println("              FINAL REPORT                ");
        System.out.println("==========================================");

        System.out.printf("| %-20s | %10d |%n", "Total Created", total);

        System.out.printf("| %-20s | %10d |%n", "Successful", success);

        System.out.printf("| %-20s | %10d |%n", "Rejected", rejected);

        System.out.printf("| %-20s | %10d |%n", "Timeout", timeout);

        System.out.printf("| %-20s | %10d |%n", "Partial", partial);

        System.out.println("==========================================");
    }

    private static boolean isSystemFinished(
            Thread producer,
            BlockingQueue<Order> queue,
            ConcurrentHashMap<Integer, OrderResult> results
    ) {

        return !producer.isAlive()
                && queue.isEmpty()
                && results.size() >= 50;
    }
}
