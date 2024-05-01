package springboot.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Service;
import springboot.Room;
import springboot.database.Setup;

@Service
public class AddRoomService {

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

        return addRoom(r);
    }

    public static String addRoom(Room room){
        Room copy = Room.findRoom(room.getRoomNumber()); //see if room already exists in database

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

}
