package springboot;
//CHanged Imports
import java.util.ArrayList;
import java.util.List;

public class Cart {
    public static Cart instance = null;
    private static List<Product> items = new ArrayList<>();

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart(); // Create a new instance if it doesn't exist
        }
        return instance;
    }


    public static void addItem(Product product) {
        if (isStockAvailable(product)) {
            items.add(product);
            System.out.println(product.getProductName() + " added to cart.");
        } else {
            System.out.println("Not enough stock available for " + product.getProductName());
        }
    }

    public static String addItemS(Product product) {
        if (isStockAvailable(product)) {
            items.add(product);
            return "success";
        } else {
            return "f";
        }
    }


    private static boolean isStockAvailable(Product product) {
        return product.getProductStock() > 0;
    }

    /*
    public void checkout(Payment payment) {
        if (!items.isEmpty()) {
            double totalAmount = calculateTotalAmount();
            //THIS WILL CONNECT TO SOMETHING THAT CAN PAY
            //payment.makePayment(totalAmount);
            items.clear(); // Empty the cart after checkout
            System.out.println("Checkout completed successfully.");
        } else {
            System.out.println("Cart is empty. Nothing to check out.");
        }
    }
    */

    public static double calculateTotalAmount() {
        double totalAmount = 0.0;
        for (Product item : items) {
            totalAmount += item.getProductPrice() ; // Assuming each product costs the same as its stock
        }
        return totalAmount;
    }

    public static List<Product> getItems() {
        return items;
    }
}
