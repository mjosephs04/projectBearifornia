package springboot.database;

import org.springframework.context.annotation.Configuration;

import java.sql.*;

@Configuration
public class Setup {

    private static final String DB_CONNECTION = "jdbc:derby:beariforniaDB;";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD)) {
            initDbTables(conn);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    private static void initDbTables(Connection conn) {
        String[] tableCreationCommands = {"CREATE TABLE ROOMS (roomNumber INT PRIMARY KEY, cost DECIMAL(10, 2), roomType VARCHAR(255), numBeds INT, qualityLevel VARCHAR(255), bedType VARCHAR(255), smokingAllowed BOOLEAN)",
                "CREATE TABLE RESERVATIONS (roomNumber INT PRIMARY KEY, startDate DATE, endDate DATE, username VARCHAR(255), status BOOLEAN, FOREIGN KEY (roomNumber) REFERENCES rooms(roomNumber))",
                "CREATE TABLE USERS (Id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, name VARCHAR(255), username VARCHAR(255) NOT NULL, password VARCHAR(255), userType VARCHAR(255) NOT NULL)",
                "CREATE TABLE PRODUCTS (productId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, productStock INT, productName VARCHAR(255), category VARCHAR(255), productPrice DECIMAL(10, 2), productDescription VARCHAR(255), imageURL VARCHAR(2048))",};

        try (Statement statement = conn.createStatement()) {
            for (String sql : tableCreationCommands) {
                String tableName = sql.split(" ")[2];  // Assumes table name is the third word in SQL statement

                System.out.println("Checking if table " + tableName + " exists.");
                if (!tableExists(conn, tableName)) {
                    System.out.println("Table " + tableName + " does not exist. Creating table.");
                    statement.executeUpdate(sql);
                    System.out.println("Table " + tableName + " created.");
                } else {
                    System.out.println("Table " + tableName + " already exists. No action taken.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating/modifying tables: " + e.getMessage());
        }
    }

    public static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't connect to database");
        }
        return dbConnection;
    }

    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData dbm = conn.getMetaData();
        try (ResultSet rs = dbm.getTables(null, null, tableName.toUpperCase(), null)) {
            return rs.next();
        }
    }
}
