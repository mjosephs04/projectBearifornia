package springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
