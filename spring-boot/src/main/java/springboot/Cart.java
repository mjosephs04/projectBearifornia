package springboot;
//CHanged Imports
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static List<Product> items = new ArrayList<>();

    public Cart(List<Product> productItems) {
        items = productItems;
    }

    public Cart() {
        items = new ArrayList<Product>();
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

    public double calculateTotalAmount() {
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
