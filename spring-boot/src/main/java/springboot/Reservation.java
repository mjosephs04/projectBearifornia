package springboot;

import springboot.database.Setup;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reservation {

    Room room;
    private Integer idNumber;
    private String username;
    private LocalDate startDay;
    private LocalDate endDay;
    private Integer price;
    private String email;
    private String address;

    public Reservation(Room room, LocalDate start, LocalDate end) {
        this.room = new Room(room);
        this.startDay = start;
        this.endDay = end;
    }

    public Reservation(Integer roomNum, LocalDate start, LocalDate end, String username) {
        try {
            this.room = Room.findRoom(roomNum);
        }catch(Exception e) {
            System.out.println("could not make reservation-- roomNum is not associated with a room" + e);
        }
        this.startDay = start;
        this.endDay = end;
        this.username = username;
    }

    //returns -1 if the start and end dates of the reservation are not valid
    public Double calculateCost(){
        double cost = -1.0;
        Integer days = (int)ChronoUnit.DAYS.between(getStartDay(), getEndDay());

        if(days > 0){
            cost = room.getCost() * days;
        }

        return cost;
    }

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

    //returns true if the given reservation conflicts with the desired times
    public boolean conflictsWith(LocalDate start, LocalDate end){
        boolean result = true;

        if(startDay.isBefore(start)){
            if(endDay.isAfter(start) || endDay.equals(startDay)){
                result = false;
            }
        }
        else if(startDay.isAfter(start)){
            if(startDay.isBefore(end)){
                result = false;
            }
        }
        else if(startDay.equals(endDay)){
            result = false;
        }


        return result;
    }

    public static LocalDate convertStringToDate(String x){
        // Extract the LocalDate part from the ZonedDateTime
        return ZonedDateTime.parse(x).toLocalDate();
    }

    //returns either a failure message or "success"
    //the strings will be zoned dates
    //calls createReservation(reservation) which then adds it to csv
    public static String addToDatabase(String checkIn, String checkOut, int roomNumber, String name) {
        LocalDate checkInDate = convertStringToDate(checkIn);
        LocalDate checkOutDate = convertStringToDate(checkOut);
        return addToDatabase(checkInDate, checkOutDate, roomNumber, name);
    }

    public static String addToDatabase(LocalDate checkInDate, LocalDate checkOutDate, int roomNumber, String name) {
        Room room = Room.findRoom(roomNumber);

        if(room == null){
            return "failure -- room does not exist";
        }
        if(Room.isAvailable(room, checkInDate, checkOutDate)){
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Reservation) {
            return ((Reservation) obj).getStartDay().equals(startDay) &&
                    ((Reservation) obj).getEndDay().equals(endDay) &&
                    ((Reservation) obj).getRoom().equals(room);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, endDay, startDay);
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Integer idNumber) {
        this.idNumber = idNumber;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


