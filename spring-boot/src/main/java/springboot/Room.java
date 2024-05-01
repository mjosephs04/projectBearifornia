package springboot;

import springboot.database.Setup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    //PRINT INFO
    public void printRoomInfo() {
        System.out.println(roomNumber);
        System.out.println("Room type: " + typeOfRoom);
        System.out.println("Bed type: " + bedType);
        System.out.println("# of beds: " + numOfBeds);
        System.out.println("Quality level: " + qualityLevel);
        if (!smokingAllowed) {
            System.out.println("Smoking not allowed.");
        } else {
            System.out.println("Smoking allowed.");
        }
    }


    // Search for available rooms based on criteria
    //the two strings at the end are in the format: 2024-04-20T20:39:06.000Z
    public static List<Room> searchRooms(boolean smoking, String bedType, int bedNum, String roomType, String start, String end) throws IOException {
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
            //for each room, check if that room has any reservations associated with it
            for(Reservation res : allReservations){
                //if it does, remove it from the total list of rooms if it isn't available for
                //the desired dates
                if(res.getRoom().equals(room)){
                    if(! res.isAvailable(startDate, endDate)){
                        rooms.remove(res.room);
                    }
                }
            }
        }
        //at the end of this loop, the list rooms only contains rooms available
        //during the desired timeframe and which match the desired criteria

        return rooms;
    }

    // finds a room from a roomNumber
    public static Room findRoom(int roomNumber) throws IOException {
        Room room = null;
        String sql = "SELECT * FROM ROOMS WHERE ROOMNUMBER = " + Integer.toString(roomNumber);
        Connection conn = Setup.getDBConnection();
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, roomNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                room = new Room(resultSet.getInt("ROOMNUMBER"),
                        resultSet.getDouble("COST"),
                        resultSet.getString("ROOMTYPE"),
                        resultSet.getInt("NUMBEDS"),
                        resultSet.getString("QUALITYLEVEL"),
                        resultSet.getString("BEDTYPE"),
                        resultSet.getBoolean("SMOKING"));
            }
        }
        catch(SQLException e) {
            return null;
        }
        return room;
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

    //opens csv file and returns a list of all existing rooms
    public static List<Room> readInAllRooms() throws IOException {
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
                Boolean smokingAllowed = resultSet.getBoolean("smokingAllowed");


                // Create a room object and add it to the list
                rooms.add(new Room(roomNum, cost, roomType, numBeds, qualityLevel, bedType, smokingAllowed));
            }
        }
        catch(SQLException e) {
            System.out.println("failure " + e);
        }
        return rooms;
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
