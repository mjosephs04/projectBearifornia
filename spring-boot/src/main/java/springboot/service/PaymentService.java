package springboot.service;

import springboot.Cart;
import springboot.Product;
import springboot.database.Setup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Service
public class PaymentService {

    @Transactional
    public void checkout(Cart cart) {
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart cannot be empty.");
        }

        Connection conn = Setup.getDBConnection();

        try {
            for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
                updateQuantityAtCheckout(entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            System.err.println("Error during checkout: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error rolling back transaction: " + ex.getMessage());
            }
        }
    }

    private void updateQuantityAtCheckout(Product product, int quantity) throws SQLException {
        Connection conn = Setup.getDBConnection();
        String sql = "UPDATE PRODUCTS SET productStock = productStock - ? WHERE productId = ? AND productStock >= ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, product.getProductId());
            pstmt.setInt(3, quantity);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update failed, no rows affected. Possibly due to insufficient stock.");
            }
        }
    }
}

