package springboot.database;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InitializeDatabase {

    public static void main(String[] args) {
        String csvFilePath = "spring-boot/src/main/resources/products.csv";
        dropTables();
        Setup.initialize();
        insertAdmin();
        insertClerk();
        readAndInsertProducts(csvFilePath);
        populateRooms();
    }

    public static void dropTables() {
        Connection connection = Setup.getDBConnection();

        try {
            // Disable auto-commit to manage transactions manually
            connection.setAutoCommit(false);

            // Get the list of all tables
            List<String> tables = new ArrayList<>();
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet rs = metaData.getTables(null, null, null, new String[]{"TABLE"})) {
                while (rs.next()) {
                    tables.add(rs.getString("TABLE_NAME"));
                }
            }

            // Drop foreign key constraints and then drop tables
            for (String table : tables) {
                dropForeignKeyConstraints(connection, table);
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate("DROP TABLE \"" + table + "\"");
                    System.out.println("Dropped table: " + table);
                }
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                System.err.println("Transaction rolled back due to an error: " + e.getMessage());
            } catch (SQLException ex) {
                System.err.println("Error during rollback: " + ex.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Error restoring auto-commit mode: " + ex.getMessage());
            }
        }
    }

    private static void dropForeignKeyConstraints(Connection conn, String tableName) throws SQLException {
        try (Statement stmt = conn.createStatement(); ResultSet rs = conn.getMetaData().getExportedKeys(null, null, tableName)) {
            while (rs.next()) {
                String fkName = rs.getString("FK_NAME");
                String fkTable = rs.getString("FKTABLE_NAME");
                if (fkName != null) {
                    stmt.executeUpdate("ALTER TABLE \"" + fkTable + "\" DROP CONSTRAINT \"" + fkName + "\"");
                }
            }
        }
    }

    public static void insertAdmin() {

        try (Connection connection = Setup.getDBConnection()) {
            String sql = "INSERT INTO USERS (name, username, password, userType) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "admin1");
                statement.setString(2, "admin");
                statement.setString(3, "password");
                statement.setString(4, "ADMIN");
                statement.executeUpdate();
                System.out.println("Admin inserted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting admin: " + e.getMessage());
        }
    }

    public static void insertClerk() {
        try (Connection connection = Setup.getDBConnection()) {
            String sql = "INSERT INTO USERS (name, username, password, userType) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "clerk1");
                statement.setString(2, "clerk");
                statement.setString(3, "password");
                statement.setString(4, "CLERK");
                statement.executeUpdate();
                System.out.println("Clerk inserted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting clerk: " + e.getMessage());
        }
    }

    private static void readAndInsertProducts(String filePath) {
        try (Connection connection = Setup.getDBConnection(); Reader reader = new FileReader(filePath)) {

            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

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

    private static void populateRooms(){
        try{
            Connection connection = Setup.getDBConnection();
            //"CREATE TABLE ROOMS (roomNumber INT PRIMARY KEY, cost DECIMAL(10, 2), roomType VARCHAR(255), numBeds INT, qualityLevel VARCHAR(255), bedType VARCHAR(255), smokingAllowed BOOLEAN)",
            String sql = "INSERT INTO ROOMS (roomNumber, cost, roomType, numBeds, qualityLevel, bedType, smokingAllowed) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            BufferedReader br = new BufferedReader(new FileReader("spring-boot/src/main/resources/Rooms.csv"));
            String line;
            br.readLine(); //skip header

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                pstmt.setInt(1, Integer.parseInt(values[0])); // roomNumber
                pstmt.setBigDecimal(2, BigDecimal.valueOf(Double.parseDouble(values[1]))); // cost
                pstmt.setString(3, values[2]); // roomType
                pstmt.setInt(4, Integer.parseInt(values[3])); // numOfBeds
                pstmt.setString(5, values[4]); // qualityLevel
                pstmt.setString(6, values[5]); // bedType
                pstmt.setBoolean(7, Boolean.parseBoolean(values[6])); // smokingAllowed
                pstmt.executeUpdate();
            }

            System.out.println("Successfully populated rooms database");
        } catch (SQLException e) {
            System.err.println("Error inserting rooms: " + e.getMessage());
        }
        catch(IOException e){
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}