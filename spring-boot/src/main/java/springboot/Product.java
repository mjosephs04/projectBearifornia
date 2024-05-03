package springboot;

import springboot.database.Setup;
//Changed Imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private int productId;
    private String productName;
    private int productStock;
    private String productDescription;
    private double productPrice;
    private final String imageURL;
    private final String category;

    public Product(int productId, String productName, int productStock, String productDescription, double productPrice, String imageURL, String category) {
        this.productId = productId;
        this.productName = productName;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.imageURL = imageURL;
        this.category = category;
    }

    public Product() {
        this.productId = 0;
        this.productName = "Default Item";
        this.productStock = 10;
        this.productDescription = "This is a default product.";
        this.productPrice = 10.00;
        this.imageURL = "https://example.com/default_image.jpg";
        this.category = "Default Category";
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
                selectStmt.setInt(1, item.getProductId());
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    int currentStock = rs.getInt("productStock");
                    int newStock = currentStock - 1; // Subtract 1 for each purchased product
                    updateStmt.setInt(1, newStock);
                    updateStmt.setInt(2, item.getProductId());
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
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update product name in the database.");
        }
    }

    public void updateProductId(int newProductId) {
        String updateQuery = "UPDATE PRODUCTS SET productId = ? WHERE productId = ?";
        try (Connection conn = Setup.getDBConnection(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setInt(1, newProductId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
            // Update the productId in the current object
            this.productId = newProductId;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update product ID in the database.");
        }
    }

    public void updateProductIdFromDatabase() {
        String selectQuery = "SELECT productId FROM PRODUCTS WHERE productName = ?";
        try (Connection conn = Setup.getDBConnection(); PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
            pstmt.setString(1, this.productName);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                int newProductId = resultSet.getInt("productId");
                this.productId = newProductId;
            } else {
                System.out.println("Product ID not found in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update product ID from the database.");
        }
    }

    private void updateProductStockInDatabase(int newStock) {
        String updateQuery = "UPDATE PRODUCTS SET productStock = ? WHERE productId = ?";
        try (Connection conn = Setup.getDBConnection(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setInt(1, newStock);
            pstmt.setInt(2, productId);
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
            pstmt.setInt(2, productId);
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
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update product price in the database.");
        }
    }

    public int getProductId() {
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
