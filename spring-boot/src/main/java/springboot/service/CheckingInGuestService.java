package springboot.service;

import springboot.DateParsing;
import springboot.database.Setup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class CheckingInGuestService {
    //returns "success" if it worked, otherwise a fail message
    public static String checkIn(String checkInDate, String checkOutDate, Integer roomNumber){
        return changeStatus(checkInDate, checkOutDate, roomNumber, true);
    }

    //returns "success" if it worked, otherwise a fail message
    public static String checkOut(String checkInDate, String checkOutDate, Integer roomNumber){
        return changeStatus(checkInDate, checkOutDate, roomNumber, false);
    }

    private static String changeStatus(String checkInDate, String checkOutDate, Integer roomNumber, Boolean status){
        LocalDate start = DateParsing.convertStringToDate(checkInDate);
        LocalDate end = DateParsing.convertStringToDate(checkOutDate);

        String selectQuery = "UPDATE RESERVATIONS SET STATUS = ? WHERE ROOMNUMBER = ? AND STARTDATE = ? AND ENDDATE = ?";

        try (Connection conn = Setup.getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {

            pstmt.setBoolean(1, status);
            pstmt.setInt(2, roomNumber);
            pstmt.setDate(3, Date.valueOf(start));
            pstmt.setDate(4, Date.valueOf(end));

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                return "failure -- No matching reservation found to modify or status was already desired value.";
            } else {
                return "success";
            }

        } catch (SQLException e) {
            return "Failed to get the reservation to checkIn: " + e.getMessage();
        }
    }
}
