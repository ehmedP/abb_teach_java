package src.comparator;

import src.model.Product;

import java.util.Comparator;

public final class ProductComparators {

    public static final Comparator<Product> BY_STOCK_ASC =
            Comparator.comparing(Product::getStock)
                    .thenComparing(Product::getId);

    private ProductComparators() {
    }
}
