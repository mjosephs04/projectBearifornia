package springboot.service;

import springboot.DateParsing;
import springboot.Room;
import springboot.database.Setup;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class CreateReservationService {

    public static String createReservation(String checkIn, String checkOut, Integer roomNum, String username) {
        return addToDatabase(DateParsing.convertStringToDate(checkIn),
                DateParsing.convertStringToDate(checkOut),
                roomNum,
                username);
    }

    private static String addToDatabase(LocalDate checkInDate, LocalDate checkOutDate, int roomNumber, String name) {
        Room room = SearchRoomsService.findRoom(roomNumber);

        if(room == null){
            return "failure -- room does not exist";
        }
        if(RoomAvailabilityService.isAvailable(room, checkInDate, checkOutDate)){
            try (Connection conn = Setup.getDBConnection()) {
                // Insert the reservation into the database
                String sql = "INSERT INTO RESERVATIONS (ROOMNUMBER, STARTDATE, ENDDATE, USERNAME) VALUES (?, ?, ?, ?)";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setInt(1, roomNumber);
                    statement.setDate(2, Date.valueOf(checkInDate));
                    statement.setDate(3, Date.valueOf(checkOutDate));
                    statement.setString(4, name);
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "failure -- An error occurred while adding the reservation. " + e.getMessage();
            }

            return "success";
        }
        else{
            return "failure -- room is not available for those dates";
        }
    }


}
