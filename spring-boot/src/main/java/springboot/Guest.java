package springboot;

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

    public List<Reservation> reservationList;

    public Guest(String name, String username, String password) {
        reservationList = new ArrayList<>();
        classification = UserType.GUEST;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Guest(String name, String e, String address, String username, String password) {
        reservationList = new ArrayList<>();
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
        reservationList.add(res);

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
}