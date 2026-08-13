package src.worker;

import src.model.OrderResult;

import java.util.concurrent.Callable;

public class OrderWorker implements Callable<OrderResult> {


    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public OrderResult call() throws Exception {
        return null;
    }
}
