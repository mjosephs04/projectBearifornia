package springboot.service;

import springboot.DateParsing;
import springboot.Reservation;
import springboot.database.Setup;

import java.sql.*;
import java.time.LocalDate;

public class DeleteReservationService {

    private static Reservation getReservation(String checkIn, String checkOut, int roomNumber) {
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

}
