package springboot.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Service;
import springboot.database.Setup;

@Service
public class AddRoomService {

    public void addRoom(String[] roomDetails) throws SQLException {
        if (roomDetails.length != 7) {
            throw new IllegalArgumentException("Invalid number of room details provided.");
        }

        String insertQuery = "INSERT INTO rooms (roomNumber, cost, roomType, numOfBeds, qualityLevel, bedType, smokingAllowed) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Setup.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setInt(1, Integer.parseInt(roomDetails[0])); // roomNumber
            pstmt.setBigDecimal(2, new java.math.BigDecimal(roomDetails[1])); // cost
            pstmt.setString(3, roomDetails[2]); // roomType
            pstmt.setInt(4, Integer.parseInt(roomDetails[3])); // numOfBeds
            pstmt.setInt(5, Integer.parseInt(roomDetails[4])); // qualityLevel
            pstmt.setString(6, roomDetails[5]); // bedType
            pstmt.setBoolean(7, Boolean.parseBoolean(roomDetails[6])); // smokingAllowed

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to add room to the database.", e);
        }
    }

}
