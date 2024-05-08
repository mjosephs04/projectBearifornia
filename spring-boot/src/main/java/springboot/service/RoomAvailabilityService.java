package springboot.service;

import springboot.Reservation;
import springboot.Room;
import springboot.database.Setup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoomAvailabilityService {

    //returns a list of all existing reservations
    public static List<Reservation> readInAllReservations(){
        List<Reservation> reservations = new ArrayList<>();
        String selectSQL = "SELECT * FROM RESERVATIONS";
        Connection conn = Setup.getDBConnection();

        try (PreparedStatement statement = conn.prepareStatement(selectSQL)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Retrieve values from the ResultSet and create reservation objects
                Integer roomNum = resultSet.getInt("roomNumber");
                LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
                LocalDate endDate = resultSet.getDate("endDate").toLocalDate();
                String username = resultSet.getString("username");

                // Create a room object and add it to the list
                reservations.add(new Reservation(roomNum, startDate, endDate, username));
            }
        }
        catch(SQLException e) {
            System.out.println("failure " + e);
            return null;
        }
        return reservations;
    }

    // Search for available rooms based on criteria
    //the two strings at the end are in the format: 2024-04-20T20:39:06.000Z
    public static List<Room> searchRooms(boolean smoking, String bedType, int bedNum, String roomType, String start, String end) {
        List<Room> rooms = readInAllRooms();
        List<Reservation> allReservations = readInAllReservations(); // Assuming this method exists to read available rooms

        // Parse the string into a ZonedDateTime
        ZonedDateTime startD = ZonedDateTime.parse(start);
        ZonedDateTime endD = ZonedDateTime.parse(end);

        // Extract the LocalDate part from the ZonedDateTime
        LocalDate startDate = startD.toLocalDate();
        LocalDate endDate = endD.toLocalDate();

        //so now we will check all rooms only rooms that match the desired criteria
        // if room does NOT match criteria, remove it from list
        rooms.removeIf(curr -> !(curr.getSmokingAllowed() == smoking &&
                curr.getBedType().equalsIgnoreCase(bedType) &&
                curr.getNumOfBeds() == bedNum &&
                curr.getTypeOfRoom().equalsIgnoreCase(roomType)));

        // Iterate through all rooms
        for (Room room : rooms) {
            if(allReservations != null) {
                //for each room, check if that room has any reservations associated with it
                for (Reservation res : allReservations) {
                    //if it does, remove it from the total list of rooms if it isn't available for
                    //the desired dates
                    if (res.getRoom().equals(room)) {
                        if (res.conflictsWith(startDate, endDate)) {
                            rooms.remove(res.getRoom());
                        }
                    }
                }
            }
        }
        //at the end of this loop, the list rooms only contains rooms available
        //during the desired timeframe and which match the desired criteria

        return rooms;
    }


    //returns a list of all existing rooms
    public static List<Room> readInAllRooms(){
        List<Room> rooms = new ArrayList<>();
        String selectSQL = "SELECT * FROM ROOMS";
        Connection conn = Setup.getDBConnection();

        try (PreparedStatement statement = conn.prepareStatement(selectSQL)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Retrieve values from the ResultSet and create User objects
                Integer roomNum = resultSet.getInt("roomNumber");
                Double cost = resultSet.getDouble("cost");
                String roomType = resultSet.getString("roomType");
                Integer numBeds = resultSet.getInt("numBeds");
                String qualityLevel = resultSet.getString("qualityLevel");
                String bedType = resultSet.getString("bedType");
                boolean smokingAllowed = resultSet.getBoolean("smokingAllowed");


                // Create a room object and add it to the list
                rooms.add(new Room(roomNum, cost, roomType, numBeds, qualityLevel, bedType, smokingAllowed));
            }
        }
        catch(SQLException e) {
            System.out.println("failure " + e);
        }
        return rooms;
    }


    //checks if a certain room is available for the desired dates -- also returns false if r is null
    public static boolean isAvailable(Room r, LocalDate start, LocalDate end){
        if(r != null) {
            List<Reservation> allReservations = readInAllReservations();

            //check all reservations to see if there are any conflicting ones for the given room
            if(allReservations != null) {
                for (Reservation res : allReservations) {
                    if (res.getRoom().equals(r)) {
                        if (res.conflictsWith(start, end)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        else{
            return false;//this happens when the room parameter is false-- which should ideally NEVER
            //happen, but u never know...
        }
    }

}
