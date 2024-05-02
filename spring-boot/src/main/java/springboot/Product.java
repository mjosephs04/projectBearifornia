package springboot;

import springboot.database.Setup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private final String productId;
    private String productName;
    private int productStock;
    private String productDescription;
    private double productPrice;
    private final String imageURL;
    private final String category;

    public Product(String productId, String productName, int productStock, String productDescription, double productPrice, String imageURL, String category) {
        this.productId = productId;
        this.productName = productName;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.imageURL = imageURL;
        this.category = category;
    }

//    public static List<Product> getProductsFromDatabase() throws SQLException {
//        List<Product> products = new ArrayList<>();
//        String query = "SELECT * FROM PRODUCTS";
//        try (Connection conn = Setup.getDBConnection();
//             PreparedStatement stmt = conn.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//            while (rs.next()) {
//                String productId = rs.getString("productId");
//                String productName = rs.getString("productName");
//                int productStock = rs.getInt("productStock");
//                String productDescription = rs.getString("productDescription");
//                double productPrice = rs.getDouble("productPrice");
//                Product product = new Product(productId, productName, productStock, productDescription, productPrice);
//                products.add(product);
//            }
//        }
//        return products;
//    }

    // Update product stock in the database based on the items purchased
//    //Pass in a List of the items purchased (NEED ID
//    //This should take in a cart of items considered PURCHASED AND update them
//productStock, productName, category, productPrice, productDescription, productId
//
    public static void updateStockAtCheckout(Cart cart) {
        String selectQuery = "SELECT productStock FROM PRODUCTS WHERE Product = ?";
        String updateQuery = "UPDATE PRODUCTS SET productStock = ? WHERE productId = ?";
        try (Connection conn = Setup.getDBConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            for (Product item : cart.getItems()) {
                // Get current stock for the product
                selectStmt.setString(1, item.getProductId());
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    int currentStock = rs.getInt("productStock");
                    int newStock = currentStock - 1; // Subtract 1 for each purchased product
                    updateStmt.setInt(1, newStock);
                    updateStmt.setString(2, item.getProductId());
                    updateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            //NEEDS LOGGING EVENTUALLY
            e.printStackTrace();
            System.out.println("Failed to update product stock in the database at checkout.");
        }
    }


    // Database update methods
    private void updateProductNameInDatabase(String newName) {
        String updateQuery = "UPDATE PRODUCTS SET productName = ? WHERE productId = ?";
        try (Connection conn = Setup.getDBConnection(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update product name in the database.");
        }
    }

    private void updateProductStockInDatabase(int newStock) {
        String updateQuery = "UPDATE PRODUCTS SET productStock = ? WHERE productId = ?";
        try (Connection conn = Setup.getDBConnection(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setInt(1, newStock);
            pstmt.setString(2, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update product stock in the database.");
        }
    }

    private void updateProductDescriptionInDatabase(String newDescription) {
        String updateQuery = "UPDATE PRODUCTS SET productDescription = ? WHERE productId = ?";
        try (Connection conn = Setup.getDBConnection(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, newDescription);
            pstmt.setString(2, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update product description in the database.");
        }
    }

    private void updateProductPriceInDatabase(double newPrice) {
        String updateQuery = "UPDATE PRODUCTS SET productPrice = ? WHERE productId = ?";
        try (Connection conn = Setup.getDBConnection(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setDouble(1, newPrice);
            pstmt.setString(2, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update product price in the database.");
        }
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    // Update product details in the database when setters are called
    public void setProductName(String productName) {
        this.productName = productName;
        updateProductNameInDatabase(productName);
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
        updateProductStockInDatabase(productStock);
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
        updateProductDescriptionInDatabase(productDescription);
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
        updateProductPriceInDatabase(productPrice);
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getCategory() {
        return category;
    }
}
