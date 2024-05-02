package springboot.service;

import org.springframework.stereotype.Service;
import springboot.Reservation;
import springboot.Room;
import springboot.database.Setup;
import java.sql.*;
import java.time.LocalDate;


@Service
public class ReservationService {

    // Method to handle creating a new reservation
    public String createReservation(Reservation res) {
        return Reservation.addToDatabase(res.getStartDay(),
                                        res.getEndDay(),
                                        res.getRoom().getRoomNumber(),
                                        res.getUsername());
    }

    public String createReservation(String checkIn, String checkOut, int roomNumber, String username) {
        return Reservation.addToDatabase(checkIn, checkOut, roomNumber, username);
    }

    public String createReservation(LocalDate checkIn, LocalDate checkOut, int roomNumber, String username) {
        return Reservation.addToDatabase(checkIn, checkOut, roomNumber, username);
    }
    
    public static String deleteReservation(String checkIn, String checkOut, int roomNumber, String name) {
        LocalDate start = Reservation.convertStringToDate(checkIn);
        LocalDate end = Reservation.convertStringToDate(checkOut);

        String deleteRes = "DELETE FROM RESERVATIONS WHERE ROOMNUMBER = ? AND STARTDATE = ? AND ENDDATE = ?";

        Connection conn = Setup.getDBConnection();
        Reservation res = getReservation(checkIn, checkOut, roomNumber);

        if (res != null) {
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
                return "failure " + e.getMessage();
            }
        }
        return "failure -- reservation does not exist";
    }


    public static Reservation getReservation(String checkIn, String checkOut, int roomNumber) {
        Reservation res = null;
        LocalDate start = Reservation.convertStringToDate(checkIn);
        LocalDate end = Reservation.convertStringToDate(checkOut);
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
        LocalDate updatedStart = Reservation.convertStringToDate(newStart);
        LocalDate updatedEnd = Reservation.convertStringToDate(newEnd);
        LocalDate oldS = Reservation.convertStringToDate(oldStart);
        LocalDate oldE = Reservation.convertStringToDate(oldEnd);

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
}
