package springboot;

import springboot.database.Setup;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {
    private Double cost = 0.00;
    private Integer roomNumber, numOfBeds;
    private String qualityLevel, typeOfRoom, bedType = "";
    private boolean smokingAllowed = false;

    //DEFAULT CONSTRUCTOR
    public Room() {
    }

    //CONSTRUCTOR
    public Room(Integer roomNum, Double c, String roomType, Integer numBed, String quality, String bedType, boolean smoking) {
        this.cost = c;
        this.roomNumber = roomNum;
        this.numOfBeds = numBed;
        this.qualityLevel = quality;
        this.typeOfRoom = roomType;
        this.smokingAllowed = smoking;
        this.bedType = bedType;
    }

    public Room(Room room){
        this.cost = room.getCost();
        this.roomNumber = room.getRoomNumber();
        this.numOfBeds = room.getNumOfBeds();
        this.qualityLevel = room.getQualityLevel();
        this.typeOfRoom = room.getTypeOfRoom();
        this.smokingAllowed = room.getSmokingAllowed();
        this.bedType = room.getBedType();
    }


    // Search for available rooms based on criteria
    //the two strings at the end are in the format: 2024-04-20T20:39:06.000Z
    public static List<Room> searchRooms(boolean smoking, String bedType, int bedNum, String roomType, String start, String end) {
        List<Room> rooms = readInAllRooms();
        List<Reservation> allReservations = Reservation.readInAllReservations(); // Assuming this method exists to read available rooms

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
                            rooms.remove(res.room);
                        }
                    }
                }
            }
        }
        //at the end of this loop, the list rooms only contains rooms available
        //during the desired timeframe and which match the desired criteria

        return rooms;
    }


    //checks if a certain room is available for the desired dates -- also returns false if r is null
    public static boolean isAvailable(Room r, LocalDate start, LocalDate end){
        if(r != null) {
            List<Reservation> allReservations = Reservation.readInAllReservations();

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


    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getNumOfBeds() {
        return numOfBeds;
    }

    public void setNumOfBeds(Integer numOfBeds) {
        this.numOfBeds = numOfBeds;
    }

    public String getQualityLevel() {
        return qualityLevel;
    }

    public void setQualityLevel(String qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    public String getTypeOfRoom() {
        return typeOfRoom;
    }

    public void setTypeOfRoom(String typeOfRoom) {
        this.typeOfRoom = typeOfRoom;
    }

    public boolean getSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(boolean smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }

    public String getBedType(){
        return bedType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return getSmokingAllowed() == room.getSmokingAllowed() &&
                getRoomNumber().equals(room.getRoomNumber()) &&
                getNumOfBeds().equals(room.getNumOfBeds()) &&
                getBedType().equalsIgnoreCase(room.getBedType())&&
                getTypeOfRoom().equalsIgnoreCase(room.getTypeOfRoom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCost(), getRoomNumber(), getNumOfBeds(), getQualityLevel(), getTypeOfRoom(), getTypeOfRoom(), smokingAllowed);
    }
}
