package springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.LoggedIn;
import springboot.UserType;
import springboot.database.Setup;
import springboot.dto.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ProductService {

    private final Setup setup;

    @Autowired
    public ProductService(Setup setup) {
        this.setup = setup;
    }

    public Product getProductByName(String productName) {
        String sql = "SELECT productStock, category, productPrice, productDescription, imageURL FROM PRODUCTS WHERE productName = ?";
        try (Connection conn = setup.getDBConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, productName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Product(
                            resultSet.getInt("productStock"),
                            productName,
                            resultSet.getString("category"),
                            resultSet.getDouble("productPrice"),
                            resultSet.getString("productDescription"),
                            resultSet.getString("imageURL")
                    );
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
}
