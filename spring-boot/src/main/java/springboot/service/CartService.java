package springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.Cart;
import springboot.Product;
import springboot.database.Setup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final Setup setup;

    @Autowired
    public CartService(Setup setup) {
        this.setup = setup;
    }

    public String getPriceCart(String [] items){
        Cart shopCart = new Cart(createProductItems(items));
        double price = shopCart.calculateTotalAmount();
        return String.valueOf(price);
    }


    // Method to check if a product is in stock and create product items for those in stock
    private List<Product> createProductItems(String[] itemNames) {
        List<springboot.Product> products = new ArrayList<>();
        Connection conn = Setup.getDBConnection();

        String selectSQL = "SELECT * FROM PRODUCTS WHERE productName = ? AND productStock > 0";

        try (PreparedStatement statement = conn.prepareStatement(selectSQL)) {
            for (String itemName : itemNames) {
                statement.setString(1, itemName);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int productId = resultSet.getInt("productId");
                    String productIdString = String.valueOf(productId);
                    String productName = resultSet.getString("productName");
                    int productStock = resultSet.getInt("productStock");
                    String productDescription = resultSet.getString("productDescription");
                    double productPrice = resultSet.getDouble("productPrice");
                    String imageURL = resultSet.getString("imageURL");
                    String category = resultSet.getString("category");
                    //String productId, String productName, int productStock, String productDescription, double productPrice, String imageURL, String category
                    springboot.Product product = new Product(productIdString,productName, productStock, productDescription, productPrice, imageURL, category);
                    products.add(product);
                } else {
                    System.out.println("Item '" + itemName + "' is not in stock or does not exist.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error accessing database: " + e.getMessage());
        }

        return products;
    }
}
