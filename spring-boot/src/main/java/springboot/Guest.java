package springboot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Guest implements User {

    private static final Random random = new Random();
    private static UserType classification;
    // Data Members
    private String name = "";
    private String username;
    private String password;
    private String streetAddress;

    public List<Reservation> reservationList;

    public Guest(String name, String username, String password) {
        reservationList = new ArrayList<>();
        classification = UserType.GUEST;
        this.name = name;
        this.username = username;
        this.password = password;
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
        Reservation reservation = new Reservation(reservedRoom, start, end);
        reservationList.add(reservation);

        return Reservation.createReservation(reservation);
    }

    public static List<Guest> readInAllGuests() throws IOException {
        ArrayList<Guest> guests = new ArrayList<>(); //store all the rooms we read in
        BufferedReader reader = new BufferedReader(new FileReader("spring-boot/src/main/resources/RoomsTaken.csv"));

        reader.readLine(); //skip first line of header info
        String line;

        //name,username,password,userType
        //read in available rooms from csv and store in list
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            Guest curr = new Guest(split[0], split[1], split[2]);
            guests.add(curr);
        }

        return guests;
    }

    @Override
    public UserType getType() {
        return classification;
    }
}