package springboot;

import java.time.LocalDate;
import java.util.Random;

public class Guest implements User {

    private static final Random random = new Random();
    private static UserType classification;
    // Data Members
    private String name = "";
    private Integer idNumber = 0;
    private String streetAddress;


    // Constructor and default constructor
    public Guest() {
        classification = UserType.GUEST;
        this.idNumber = random.nextInt(99999 - 10000 + 1) + 10000;
    }

    public Guest(String name, Integer id) {
        this(); // This just calls the default constructor to increment the id number;
        this.name = name;
        this.idNumber = id;
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
    @Override
    public String reserveRoom(Room reservedRoom) {
        Reservation reservation = new Reservation(reservedRoom.getRoomNumber(), name);

        return reservation.createReservation(reservation);
    }

    @Override
    public UserType getType() {
        return classification;
    }

    public Integer getIdNumber() { // For the Guest
        return this.idNumber;
    }

    public void setIdNumber(Integer id) { // For the Guest
        id = this.idNumber;
    }

    private LocalDate getStartDate() {
        return LocalDate.now();
    }

    private LocalDate getEndDate() {
        return LocalDate.now().plusDays(1);
    }

}