package springboot.service;

import java.math.BigDecimal;
import java.sql.*;
import org.springframework.stereotype.Service;
import springboot.Room;
import springboot.database.Setup;

@Service
public class RoomService {

    //roomDetails contains: roomNum, cost, roomType, numBeds,quality,bedtype, smoking
    //this fxn creates a room from those details and then calls the other overloaded
    //addRoom fxn
    //returns either "success" or a fail message
    public static String addRoom(String[] roomDetails){
        if (roomDetails.length != 7) {
            throw new IllegalArgumentException("Invalid number of room details provided.");
        }

        Room r = new Room(Integer.parseInt(roomDetails[0]), //room Num
                        Double.parseDouble(roomDetails[1]), //cost
                        roomDetails[2], //roomType
                        Integer.parseInt(roomDetails[3]), //numBeds
                        roomDetails[4], //qualityLevel
                        roomDetails[5], //bedType
                        Boolean.parseBoolean(roomDetails[6]) //smoking
                        );

        return addRoom(r); //returns
    }

    //adds a room to the database if it does not already exist
    public static String addRoom(Room room){
        Room copy = SearchRoomsService.findRoom(room.getRoomNumber()); //see if room already exists in database

        //if room does not already exist in database
        if(copy == null){
            String insertQuery = "INSERT INTO ROOMS (roomNumber, cost, roomType, numBeds, qualityLevel, bedType, smokingAllowed) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try{
                Connection conn = Setup.getDBConnection();
                PreparedStatement pstmt = conn.prepareStatement(insertQuery);

                pstmt.setInt(1, room.getRoomNumber()); // roomNumber
                pstmt.setBigDecimal(2, BigDecimal.valueOf(room.getCost())); // cost
                pstmt.setString(3, room.getTypeOfRoom()); // roomType
                pstmt.setInt(4, room.getNumOfBeds()); // numOfBeds
                pstmt.setString(5, room.getQualityLevel()); // qualityLevel
                pstmt.setString(6, room.getBedType()); // bedType
                pstmt.setBoolean(7, room.getSmokingAllowed()); // smokingAllowed

                pstmt.executeUpdate();

            } catch (SQLException e) {
                return "failure -- could not insert room " + e.getMessage();
            }

            return "success";
        }
        else{
            return "failure -- room already exists";
        }
    }


    public static String modifyRoom(Integer roomNumber, Room newRoom){
        String updateQuery = "UPDATE ROOMS SET cost = ?, roomType = ?, numBeds = ?, qualityLevel = ?, bedType = ?, smokingAllowed = ? WHERE ROOMNUMBER = ?";
        try (Connection conn = Setup.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            // Set parameters for the prepared statement
            pstmt.setBigDecimal(1, BigDecimal.valueOf(newRoom.getCost())); // cost
            pstmt.setString(2, newRoom.getTypeOfRoom()); // roomType
            pstmt.setInt(3, newRoom.getNumOfBeds()); // numBeds
            pstmt.setString(4, newRoom.getQualityLevel()); // qualityLevel
            pstmt.setString(5, newRoom.getBedType()); // bedType
            pstmt.setBoolean(6, newRoom.getSmokingAllowed()); // smokingAllowed
            pstmt.setInt(7, roomNumber); // roomNumber

            // Execute the update query
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                // No matching room found
                return "No matching room found to modify.";
            } else {
                return "success";
            }
        } catch (SQLException e) {
            return "Failed to modify room: " + e.getMessage();
        }
    }

}
