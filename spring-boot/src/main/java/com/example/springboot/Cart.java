package com.example.springboot;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> items;

    public Cart() {
        this.items = new ArrayList<Product>();
    }

    public void addItem(Product product) {
        if (isStockAvailable(product)) {
            items.add(product);
            System.out.println(product.getProductName() + " added to cart.");
        } else {
            System.out.println("Not enough stock available for " + product.getProductName());
        }
    }

    private boolean isStockAvailable(Product product) {
        return product.getProductStock() > 0;
    }

    public void checkout(Payment payment) {
        if (!items.isEmpty()) {
            double totalAmount = calculateTotalAmount();
            //THIS WILL CONNECT TO SOMETHING THAT CAN PAY
            //payment.makePayment(totalAmount);
            items.clear(); // Empty the cart after checkout
            System.out.println("Checkout completed successfully.");
        } else {
            System.out.println("Cart is empty. Nothing to checkout.");
        }
    }

    private double calculateTotalAmount() {
        double totalAmount = 0.0;
        for (Product item : items) {
            totalAmount += item.getProductStock(); // Assuming each product costs the same as its stock
        }
        return totalAmount;
    }
}
