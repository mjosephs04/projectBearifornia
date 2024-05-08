package springboot.service;

import springboot.Room;
import springboot.database.Setup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchRoomsService {

    // finds a room from a roomNumber
    public static Room findRoom(int roomNumber) {
        Room room = null;
        String findRoom = "SELECT * FROM ROOMS WHERE ROOMNUMBER = " + roomNumber;
        Connection conn = Setup.getDBConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(findRoom);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {//if the room was found, make a new room w its info
                room = new Room(resultSet.getInt("ROOMNUMBER"),
                        resultSet.getBigDecimal("COST").doubleValue(),
                        resultSet.getString("ROOMTYPE"),
                        resultSet.getInt("NUMBEDS"),
                        resultSet.getString("QUALITYLEVEL"),
                        resultSet.getString("BEDTYPE"),
                        resultSet.getBoolean("SMOKINGALLOWED"));
            }
        }
        catch(SQLException e) {
            return null;
        }
        return room;
    }

}
