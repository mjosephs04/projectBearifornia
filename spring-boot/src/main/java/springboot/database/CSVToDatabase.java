package springboot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CSVToDatabase {

    private static final String DB_CONNECTION = "jdbc:derby:beariforniaDB;create=true";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        String csvFilePath = "spring-boot/src/main/resources/products.csv";
        readAndInsertData(csvFilePath);
    }

    private static void readAndInsertData(String filePath) {
        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
             Reader reader = new FileReader(filePath)) {

            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());

            String query = "INSERT INTO PRODUCTS (productStock, productName, category, productPrice, productDescription, imageURL) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (CSVRecord record : parser) {
                preparedStatement.setInt(1, 100);  // Assuming default stock is 100 for all items
                preparedStatement.setString(2, record.get("productName"));
                preparedStatement.setString(3, record.get("category"));
                preparedStatement.setBigDecimal(4, new java.math.BigDecimal(record.get("productPrice")));
                preparedStatement.setString(5, record.get("productDescription"));
                preparedStatement.setString(6, record.get("imageURL"));
                preparedStatement.executeUpdate();
            }

            System.out.println("Data insertion complete.");
        } catch (SQLException e) {
            System.err.println("Error during database connection or query execution: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
