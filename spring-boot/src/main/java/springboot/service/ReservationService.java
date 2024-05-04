package springboot.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import springboot.DateParsing;
import springboot.Reservation;
import springboot.Room;
import springboot.database.Setup;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Service
public class ReservationService {

    public static String createReservation(String checkIn, String checkOut, Integer roomNum, String username) {
        return Reservation.addToDatabase(DateParsing.convertStringToDate(checkIn),
                DateParsing.convertStringToDate(checkOut),
                roomNum,
                username);
    }

    public static String deleteReservation(String checkIn, String checkOut, int roomNumber, String name) {
        LocalDate start = DateParsing.convertStringToDate(checkIn);
        LocalDate end = DateParsing.convertStringToDate(checkOut);

        String deleteRes = "DELETE FROM RESERVATIONS WHERE ROOMNUMBER = ? AND STARTDATE = ? AND ENDDATE = ?";

        Connection conn = Setup.getDBConnection();
        Reservation res = getReservation(checkIn, checkOut, roomNumber);

        if (res != null && start != null && end != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(deleteRes)) {
                pstmt.setInt(1, roomNumber);
                pstmt.setDate(2, Date.valueOf(start));
                pstmt.setDate(3, Date.valueOf(end));

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    return "success";
                } else {
                    return "failure -- no matching reservation found";
                }
            } catch (SQLException e) {
                return "failure -- " + e.getMessage();
            }
        }
        return "failure -- reservation does not exist";
    }

    public static Reservation getReservation(String checkIn, String checkOut, int roomNumber) {
        Reservation res = null;
        LocalDate start = DateParsing.convertStringToDate(checkIn);
        LocalDate end = DateParsing.convertStringToDate(checkOut);
        String findRes = "SELECT * FROM RESERVATIONS WHERE roomNumber = ? AND startDate = ? AND endDate = ?";

        try (Connection conn = Setup.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(findRes)) {

            pstmt.setInt(1, roomNumber);
            pstmt.setDate(2, Date.valueOf(start));
            pstmt.setDate(3, Date.valueOf(end));

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                res = new Reservation(roomNumber,
                        start,
                        end, resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }

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
        room = Room.findRoom(roomNumber);

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
