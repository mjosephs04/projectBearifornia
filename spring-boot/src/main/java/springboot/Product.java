package springboot;

public class Product {
    private final String productId;
    private final String imageURL;
    private final String category;
    private final String productName;
    private final int productStock;
    private final String productDescription;
    private final double productPrice;

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
//                String productId = rs.getString("PRODUCTID");
//                String productName = rs.getString("PRODUCTNAME");
//                int productStock = rs.getInt("PRODUCTSTOCK");
//                String productDescription = rs.getString("PRODUCTDESCRIPTION");
//                double productPrice = rs.getDouble("PRODUCTPRICE");
//                Product product = new Product(productId, productName, productStock, productDescription, productPrice);
//                products.add(product);
//            }
//        }
//        return products;
//    }

    // Update product stock in the database based on the items purchased
//    //Pass in a List of the items purchased (NEED ID
//    public static void updateStockAtCheckout (List<Product> itemList) {
//        String selectQuery = "SELECT PRODUCTSTOCK FROM PRODUCT WHERE PRODUCTID = ?";
//        String updateQuery = "UPDATE PRODUCT SET PRODUCTSTOCK = ? WHERE PRODUCTID = ?";
//        try (Connection conn = Setup.getDBConnection();
//             PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
//             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
//
//            for (Product item : itemList) {
//                // Get current stock for the product
//                selectStmt.setString(1, item.getProductId());
//                ResultSet rs = selectStmt.executeQuery();
//                if (rs.next()) {
//                    int currentStock = rs.getInt("PRODUCTSTOCK");
//                    int newStock = currentStock - 1; // Subtract 1 for each purchased product
//                    updateStmt.setInt(1, newStock);
//                    updateStmt.setString(2, item.getProductId());
//                    updateStmt.executeUpdate();
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Failed to update product stock in the database at checkout.");
//        }
//    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductStock() {
        return productStock;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getCategory() {
        return category;
    }
}
