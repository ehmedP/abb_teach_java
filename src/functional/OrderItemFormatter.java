package src.functional;

import src.model.Product;

import java.util.Map;

@FunctionalInterface
public interface OrderItemFormatter {

    String formatItem(Map.Entry<Integer, Integer> item);

    static OrderItemFormatter withProductNames(Map<Integer, Product> products) {
        return item -> {

            Product product = products.get(item.getKey());

            String name = product == null ? "naməlum#" + item.getKey() : product.getName();

            return name + "×" + item.getValue();
        };
    }

}
