package src.exception;

public class InvalidOrderException extends WarehouseException {
    public InvalidOrderException(String message) {
        super(message, "INVALID_ORDER");
    }
}
