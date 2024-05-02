package springboot;

import springboot.database.Setup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Guest implements User {

    private static UserType classification;
    // Data Members
    private String name = "";
    private String username;
    private String password;
    private String streetAddress;
    private String email;

    public Guest(String name, String username, String password) {
        classification = UserType.GUEST;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Guest(String name, String e, String address, String username, String password) {
        classification = UserType.GUEST;
        this.name = name;
        this.username = username;
        this.password = password;
        email = e;
        streetAddress = address;
    }

    // GETTERS
    public String getName() {
        return this.name;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    // User class interface methods

    //returns either a failure message or "success"
    public String reserveRoom(Room reservedRoom, LocalDate start, LocalDate end) {
        Reservation res = new Reservation(reservedRoom, start, end);

        return Reservation.addToDatabase(res.getStartDay(),
                res.getEndDay(),
                res.getRoom().getRoomNumber(),
                res.getUsername());
    }

    public String reserveRoom(Room reservedRoom, String start, String end) {
        return reserveRoom(reservedRoom,
                            Reservation.convertStringToDate(start),
                            Reservation.convertStringToDate(end));
    }


        @Override
    public UserType getType() {
        return classification;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected String getPassword() {
        return password;
    }

    public List<Reservation> getMyReservations(){
        String selectQuery = "SELECT * FROM RESERVATIONS WHERE USERNAME = " + username;
        List<Reservation> reservations = new ArrayList<>();

        try {
            ResultSet rs = Setup.getDBConnection().createStatement().executeQuery(selectQuery);

            while (rs.next()) {
                // Extract data from result set
                int roomNumber = rs.getInt("ROOMNUMBER");
                LocalDate startDate = rs.getDate("STARTDATE").toLocalDate();
                LocalDate endDate = rs.getDate("ENDDATE").toLocalDate();

                // Create Reservation object and add to list
                Reservation reservation = new Reservation(roomNumber, startDate, endDate, username);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            System.err.println("failure to get the reservations " + e.getMessage());
            return null;
        }

        return reservations;
    }

}