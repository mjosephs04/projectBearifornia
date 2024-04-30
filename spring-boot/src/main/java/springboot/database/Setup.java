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
                "CREATE TABLE ROOMS(roomNumber INT PRIMARY KEY, cost DECIMAL(10, 2), roomType VARCHAR(255), numBeds INT, qualityLevel VARCHAR(255), bedType VARCHAR(255), smokingAllowed BOOLEAN)",
                "CREATE TABLE RESERVATIONS (roomNumber INT PRIMARY KEY, startDate DATE, endDate DATE, username VARCHAR(255), FOREIGN KEY (roomNumber) REFERENCES rooms(roomNumber))",
                "CREATE TABLE USERS (Id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, name VARCHAR(255), username VARCHAR(255) NOT NULL, password VARCHAR(255), userType VARCHAR(255) NOT NULL)",
                "CREATE TABLE PRODUCT (productId VARCHAR(255) PRIMARY KEY, productName VARCHAR(255), productStock INT, productDescription VARCHAR(255), productPrice DECIMAL(10, 2))",
        };

        try (Statement statement = conn.createStatement()) {
            for (String sql : tableCreationCommands) {
                String tableName = sql.split(" ")[2];  // Assumes table name is the third word in SQL statement
                if (tableExists(conn, tableName)) {
                    dropForeignKeyConstraints(conn, tableName);
                    statement.executeUpdate("DROP TABLE " + tableName);
                }
                statement.executeUpdate(sql);
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
        try (var resultSet = conn.getMetaData().getTables(null, null, tableName, null)) {
            return resultSet.next();
        }
    }

    private static void dropForeignKeyConstraints(Connection conn, String tableName) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = conn.getMetaData().getExportedKeys(null, null, tableName)) {
            while (rs.next()) {
                String fkName = rs.getString("FK_NAME");
                String fkTable = rs.getString("FKTABLE_NAME");
                stmt.executeUpdate("ALTER TABLE " + fkTable + " DROP CONSTRAINT " + fkName);
            }
        }
    }

}
