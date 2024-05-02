package springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.Cart;
import springboot.Product;
import springboot.database.Setup;
import springboot.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    private Bank bank;  // Assuming Bank is a bean, or instantiate it directly if not managed by Spring

    /**
     * Processes checkout by updating the stock of products in the database.
     * Only completes the checkout if the bank approves the transaction.
     *
     * @param cart the cart containing products and their quantities
     * @throws SQLException if there is an issue with database operations
     */
    @Transactional
    public void checkout(Cart cart) throws SQLException {
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart cannot be empty.");
        }

        double totalPrice = cart.getTotalPrice();
        if (!bank.processTransaction(totalPrice)) {
            throw new IllegalStateException("Bank transaction failed, checkout aborted.");
        }

        try (Connection conn = Setup.getDBConnection()) {
            for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
                updateQuantityAtCheckout(conn, entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            System.err.println("Error during checkout: " + e.getMessage());
            throw e; // rethrow to let the transaction management handle the rollback
        }
    }

    /**
     * Updates the product stock in the database at checkout.
     *
     * @param conn the database connection
     * @param product the product to update
     * @param quantity the quantity to decrease from stock
     */
    private void updateQuantityAtCheckout(Connection conn, Product product, int quantity) throws SQLException {
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
