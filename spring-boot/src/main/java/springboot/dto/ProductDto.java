package springboot.dto;

public class ProductDto {
    private int stock;
    private String name;
    private String category;
    private double price;
    private String description;
    private String imageURL;

    public ProductDto(int stock, String name, String category, double price, String description, String imageURL) {
        this.stock = stock;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

