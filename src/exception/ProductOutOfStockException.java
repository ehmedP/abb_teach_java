package src.exception;

public class ProductOutOfStockException extends WarehouseException {
    public ProductOutOfStockException(String message) {
        super(message, "PRODUCT_OUT_OF_STOCK");
    }
}
