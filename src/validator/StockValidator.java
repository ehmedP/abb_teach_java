package src.validator;

import src.model.Order;

import java.util.Random;

public class StockValidator {

    private final Random random = new Random();

    public boolean validate(Order order) throws InterruptedException {

        Thread.sleep(random.nextInt(200, 1500));

        return random.nextBoolean();
    }

}
