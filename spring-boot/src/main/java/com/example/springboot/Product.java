import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private String productId;
    private String productName;
    private int productStock;
    private String productDescription;

    public Product(String productId, String productName, int productStock, String productDescription) {
        this.productId = productId;
        this.productName = productName;
        this.productStock = productStock;
        this.productDescription = productDescription;
    }

    public static List<Product> readProductsFromCSV(String filename) throws IOException {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String productId = data[0].trim();
                String productName = data[1].trim();
                int productStock = Integer.parseInt(data[2].trim());
                String productDescription = data[3].trim();
                Product product = new Product(productId, productName, productStock, productDescription);
                products.add(product);
            }
        }
        return products;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productStock=" + productStock +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }
}
