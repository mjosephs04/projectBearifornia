package springboot.service;

import org.springframework.stereotype.Service;
import springboot.DateParsing;
import springboot.Reservation;
import springboot.Room;
import springboot.database.Setup;
import java.sql.*;
import java.time.LocalDate;


@Service
public class ReservationService {

    public static String modifyReservation(String newStart, String newEnd, int roomNumber, String oldStart, String oldEnd) {
        LocalDate updatedStart = DateParsing.convertStringToDate(newStart);
        LocalDate updatedEnd = DateParsing.convertStringToDate(newEnd);
        LocalDate oldS = DateParsing.convertStringToDate(oldStart);
        LocalDate oldE = DateParsing.convertStringToDate(oldEnd);

        String updateQuery = "UPDATE RESERVATIONS SET STARTDATE = ?, ENDDATE = ? WHERE ROOMNUMBER = ? AND STARTDATE = ? AND ENDDATE = ?";

        try (Connection conn = Setup.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setDate(1, Date.valueOf(updatedStart));
            pstmt.setDate(2, Date.valueOf(updatedEnd));
            pstmt.setInt(3, roomNumber);
            pstmt.setDate(4, Date.valueOf(oldS));
            pstmt.setDate(5, Date.valueOf(oldE));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                return "No matching reservation found to modify.";
            } else {
                return "success";
            }
        } catch (SQLException e) {
            return "Failed to modify reservation: " + e.getMessage();
        }
    }

    public static Double calculateCost(String checkIn, String checkOut, int roomNumber){
        Room room;
        Double cost = 0.0;
        // Extract the LocalDate part from the string
        LocalDate startDate = DateParsing.convertStringToDate(checkIn);
        LocalDate endDate = DateParsing.convertStringToDate(checkOut);

        //try to find room associated with the roomNumber
        room = SearchRoomsService.findRoom(roomNumber);

        //if we found the room, check the cost!
        if(room != null && startDate != null && endDate != null){
            Reservation r = new Reservation(room, startDate, endDate);
            cost = r.calculateCost();
        }
        if(cost > 0){
            return cost;
        }
        //return null bc we couldnt find room or parsing date didnt work
        return null;
    }
}
