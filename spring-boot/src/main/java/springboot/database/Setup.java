package springboot.database;

import java.sql.*;

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
        String[] tableCreationCommands = {
                "CREATE TABLE ROOMS (roomNumber INT PRIMARY KEY, cost DECIMAL(10, 2), roomType VARCHAR(255), numOfBeds INT, qualityLevel INT, bedType VARCHAR(255), smokingAllowed BOOLEAN)",
                "CREATE TABLE RESERVATIONS (roomNumber INT, cost DECIMAL(10, 2), roomType VARCHAR(255), numOfBeds INT, qualityLevel INT, bedType VARCHAR(255), smokingAllowed BOOLEAN, startDate DATE, endDate DATE, username VARCHAR(255), FOREIGN KEY (roomNumber) REFERENCES rooms(roomNumber))",
                "CREATE TABLE USERS (name VARCHAR(255), username VARCHAR(255) PRIMARY KEY, password VARCHAR(255), userType VARCHAR(255)",
                "CREATE TABLE PRODUCTS (productId VARCHAR(255) PRIMARY KEY, productName VARCHAR(255), productStock INT, productDescription VARCHAR(255), productPrice DECIMAL(10, 2))"
        };

        try (Statement statement = conn.createStatement()) {
            for (String sql : tableCreationCommands) {
                String tableName = sql.split(" ")[2];  // Assumes table name is the third word in SQL statement
                if (tableExists(conn, tableName)) {
                    statement.executeUpdate("DELETE FROM " + tableName);
                } else {
                    statement.executeUpdate(sql);
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
            return dbConnection;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't connect to database");
        }
        return dbConnection;
    }

    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        // Check if the table exists in the database
        DatabaseMetaData dbMetaData = conn.getMetaData();
        try (ResultSet resultSet = dbMetaData.getTables(null, null, tableName.toUpperCase(), new String[] {"TABLE"})) {
            return resultSet.next();  // Returns true if the table exists
        } catch (SQLException e) {
            System.err.println("Error checking for table existence: " + e.getMessage());
            throw e;  // Re-throw the exception after logging it
        }
    }
}
