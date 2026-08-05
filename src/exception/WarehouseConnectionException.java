package src.exception;

public class WarehouseConnectionException extends WarehouseException {
    public WarehouseConnectionException(String message) {
        super(message, "WAREHOUSE_CONNECTION_ERROR");
    }
}
