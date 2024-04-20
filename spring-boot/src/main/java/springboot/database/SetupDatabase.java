package springboot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupDatabase {
    public static void main(String[] args) {
        String dbURL = "jdbc:derby:HotelDB;create=true";
        try (
                Connection conn = DriverManager.getConnection(dbURL);
                Statement stmt = conn.createStatement();
        ) {
            // SQL to create the 'rooms' table
            String sqlCreateRooms = "CREATE TABLE rooms (" +
                    "roomNumber INT PRIMARY KEY, " +
                    "cost DECIMAL(10, 2), " +
                    "roomType VARCHAR(255), " +
                    "numOfBeds INT, " +
                    "qualityLevel INT, " +
                    "bedType VARCHAR(255), " +
                    "smokingAllowed BOOLEAN)";

            // SQL to create the 'roomsTaken' table
            String sqlCreateRoomsTaken = "CREATE TABLE roomsTaken (" +
                    "roomNumber INT, " +
                    "cost DECIMAL(10, 2), " +
                    "roomType VARCHAR(255), " +
                    "numOfBeds INT, " +
                    "qualityLevel INT, " +
                    "bedType VARCHAR(255), " +
                    "smokingAllowed BOOLEAN, " +
                    "startDate DATE, " +
                    "endDate DATE, " +
                    "username VARCHAR(255), " +
                    "FOREIGN KEY (roomNumber) REFERENCES rooms(roomNumber))";

            // SQL to create the 'users' table
            String sqlCreateUsers = "CREATE TABLE users (" +
                    "name VARCHAR(255), " +
                    "username VARCHAR(255) PRIMARY KEY, " +
                    "password VARCHAR(255), " +
                    "userType VARCHAR(255))";

            // Execute SQL statements to create tables
            stmt.executeUpdate(sqlCreateRooms);
            stmt.executeUpdate(sqlCreateRoomsTaken);
            stmt.executeUpdate(sqlCreateUsers);

            System.out.println("Database and tables created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

