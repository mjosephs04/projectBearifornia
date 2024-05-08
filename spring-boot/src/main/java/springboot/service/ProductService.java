//Product Service

package springboot.service;

import org.springframework.stereotype.Service;
import springboot.LoggedIn;
import springboot.Product;
import springboot.UserType;
import springboot.database.Setup;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//Changed Imports
@Service
public class ProductService {

    public static Product getProductByName(String productName) {
        String sql = "SELECT productId, productStock, category, productPrice, productDescription, imageURL FROM PRODUCTS WHERE productName = ?";
        try (Connection conn = Setup.getDBConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, productName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int productId = resultSet.getInt("productId");
                    int productStock = resultSet.getInt("productStock");
                    String category = resultSet.getString("category");
                    double productPrice = resultSet.getDouble("productPrice");
                    String productDescription = resultSet.getString("productDescription");
                    String imageURL = resultSet.getString("imageURL");

                    return new Product(productId, productName, productStock, productDescription, productPrice, imageURL, category);
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null;
    }


    public static String createProduct(String productName, int productStock, String productDescription, double productPrice, String imageURL, String category) {
        if (!LoggedIn.type.equals(UserType.CLERK)) {
            return "Failure: Only clerks can create products.";
        }
        return addProduct(productName, productStock, productDescription, productPrice, imageURL, category);
    }

    public static String deleteProduct(String productName) {
        if (!LoggedIn.type.equals(UserType.CLERK)) {
            return "Failure: Only clerks can delete products.";
        }
        return removeProduct(productName);
    }


    public static String addProduct(String productName, int productStock, String productDescription, double productPrice, String imageURL, String category) {
        Connection conn = Setup.getDBConnection();
        String insertSQL = "INSERT INTO PRODUCTS (productName, productStock, productDescription, productPrice, imageURL, category) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(insertSQL)) {
            statement.setString(1, productName);
            statement.setInt(2, productStock);
            statement.setString(3, productDescription);
            statement.setDouble(4, productPrice);
            statement.setString(5, imageURL);
            statement.setString(6, category);
            statement.executeUpdate();
            return "success";
        } catch (SQLException e) {
            return "Could not insert product into database: " + e.getMessage();
        }
    }


    //This is really just for Debugging ;)
    public static String addProduct(Product product) {
        Connection conn = Setup.getDBConnection();
        String insertSQL = "INSERT INTO PRODUCTS (productName, productStock, productDescription, productPrice, imageURL, category) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(insertSQL)) {
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getProductStock());
            statement.setString(3, product.getProductDescription());
            statement.setDouble(4, product.getProductPrice());
            statement.setString(5, product.getImageURL());
            statement.setString(6, product.getCategory());
            statement.executeUpdate();
            product.updateProductIdFromDatabase();
            return "success";
        }
        catch (SQLException e) {
            return "Could not insert product into database: " + e.getMessage();
        }



    }


    public static String removeProduct(String productName) {
        Connection conn = Setup.getDBConnection();
        String deleteSQL = "DELETE FROM PRODUCTS WHERE productName = ?";

        try (PreparedStatement statement = conn.prepareStatement(deleteSQL)) {
            statement.setString(1, productName);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return "success";
            } else {
                return "Product not found";
            }
        } catch (SQLException e) {
            return "Could not delete product from database: " + e.getMessage();
        }
    }

}

