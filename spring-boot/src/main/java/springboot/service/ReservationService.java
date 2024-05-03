package springboot.service;

import org.springframework.stereotype.Service;
import springboot.Reservation;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(checkIn, formatter);
        LocalDate end = LocalDate.parse(checkOut, formatter);

        String deleteRes = "DELETE FROM RESERVATIONS WHERE ROOMNUMBER = ? AND STARTDATE = ? AND ENDDATE = ?";

        Connection conn = Setup.getDBConnection();
        Reservation res = getReservation(checkIn, checkOut, roomNumber, name);
        if(res != null) {
            try {
                conn.createStatement().executeQuery(deleteRes);
            } catch (SQLException e) {
                return "failure " + e.getMessage();
            }
            return "success";
        }
        return "failure -- reservation does not exist";
    }


    public static Reservation getReservation(String checkIn, String checkOut, int roomNumber, String name) {
        Reservation res = null;
        String findRes  = "SELECT * FROM RESERVATIONS WHERE ROOMNUMBER = " + roomNumber +
                            " AND USERNAME = " + name
                + " AND STARTDATE = " + Date.valueOf(Reservation.convertStringToDate(checkIn))
                + " AND ENDDATE = " + Date.valueOf(Reservation.convertStringToDate(checkOut));
        Connection conn = Setup.getDBConnection();
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(findRes);
            if (resultSet.next()) {//if the res was found, make a new room w its info
                res = new Reservation(roomNumber,
                        Reservation.convertStringToDate(checkIn),
                        Reservation.convertStringToDate(checkOut),
                        name);
            }
        }
        catch(SQLException e) {
            return null;
        }
        return res;
    }

    public static String modifyReservation(String newStart, String newEnd, int roomNumber, String oldStart, String oldEnd) {
        /*LocalDate updatedStart = Reservation.convertStringToDate(newStart);
        LocalDate updatedEnd = Reservation.convertStringToDate(newEnd);
        LocalDate oldS = Reservation.convertStringToDate(oldStart);
        LocalDate oldE = Reservation.convertStringToDate(oldEnd);


        String updateQuery = "UPDATE RESERVATIONS SET STARTDATE = " + Date.valueOf(updatedStart) +
                ", ENDDATE = " + Date.valueOf(updatedEnd) +
                " WHERE ROOMNUMBER = " + roomNumber +
                " AND STARTDATE = " + Date.valueOf(oldS) +
                " AND ENDDATE = " + Date.valueOf(oldE);

        try {
            Statement stmt = Setup.getDBConnection().createStatement();
            int rowsAffected = stmt.executeUpdate(updateQuery);

            if (rowsAffected == 0) {
                // No matching reservation found
                return "No matching reservation found to modify.";
            } else {
                return "success";
            }
        } catch (SQLException e) {
            return "Failed to modify reservation" + e.getMessage();
        }*/

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
