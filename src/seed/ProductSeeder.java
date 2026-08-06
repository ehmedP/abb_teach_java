package src.seed;

import src.comparator.ProductComparators;
import src.model.Product;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public final class ProductSeeder {

    public static final String LAPTOP = "Laptop";
    public static final String SMARTPHONE = "Smartfon";
    public static final String HEADPHONES = "Qulaqlıq";
    public static final String KEYBOARD = "Klaviatura";
    public static final String MOUSE = "Siçan";
    public static final String MONITOR = "Monitor";
    public static final String PRINTER = "Printer";
    public static final String WEBCAM = "Veb kamera";
    public static final String EXTERNAL_DISK = "Xarici disk";
    public static final String POWERBANK = "Powerbank";

    public static final Integer UNKNOWN_PRODUCT_ID = 9_999;
    public static final Integer ANOTHER_UNKNOWN_PRODUCT_ID = 8_888;

    private ProductSeeder() {
    }

    public static Map<Integer, Product> seed() {

        List<Product> catalog = List.of(
                new Product(LAPTOP, 25, 1500.0),
                new Product(SMARTPHONE, 40, 900.0),
                new Product(HEADPHONES, 3, 120.0),
                new Product(KEYBOARD, 0, 45.0),
                new Product(MOUSE, 12, 25.0),
                new Product(MONITOR, 2, 300.0),
                new Product(PRINTER, 7, 220.0),
                new Product(WEBCAM, 1, 60.0),
                new Product(EXTERNAL_DISK, 15, 130.0),
                new Product(POWERBANK, 4, 55.0)
        );

        Map<Integer, Product> products = new LinkedHashMap<>();
        for (Product product : catalog) {
            products.put(product.getId(), product);
        }

        return products;
    }

    public static PriorityQueue<Product> seedLowStockQueue(Map<Integer, Product> products) {

        PriorityQueue<Product> queue = new PriorityQueue<>(ProductComparators.BY_STOCK_ASC);
        queue.addAll(products.values());

        return queue;
    }

    public static Integer idOf(Map<Integer, Product> products, String name) {
        return products.values().stream()
                .filter(product -> product.getName().equals(name))
                .map(Product::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Seed məhsulu tapılmadı: " + name));
    }
}
