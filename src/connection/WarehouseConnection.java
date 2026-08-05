package src.connection;

import src.exception.WarehouseConnectionException;

import java.util.Random;

public class WarehouseConnection implements AutoCloseable {

    public WarehouseConnection() throws WarehouseConnectionException {

        if (new Random().nextBoolean()) {
            throw new WarehouseConnectionException("Connection failed.");
        }

    }

    public void executeQuery(String query) {
        System.out.println("Executing query: " + query);
    }

    @Override
    public void close() {
        System.out.println("Connection closed.");
    }
}