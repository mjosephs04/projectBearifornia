package springboot;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private final Map<Product, Integer> items;

    public Cart() {
        items = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException("Product cannot be null and quantity must be greater than zero.");
        }
        items.merge(product, quantity, Integer::sum);
    }

    public void removeProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        items.remove(product);
    }

    public int getProductQuantity(Product product) {
        return items.getOrDefault(product, 0);
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getProductPrice() * entry.getValue();
        }
        return total;
    }

    public Map<Product, Integer> getItems() {
        return new HashMap<>(items);
    }
}
