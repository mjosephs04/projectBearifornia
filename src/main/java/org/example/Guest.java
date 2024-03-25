package org.example;

import java.time.LocalDate;
import java.util.Random;

public class Guest implements User {

    // Data Members
    private String name = "";
    private String streetAddress = "";

    private Integer idNumber = 0;
    private static Random random = new Random();


    // Constructor and default constructor
    public Guest(){
        this.idNumber = random.nextInt(99999 - 10000 + 1 ) + 10000;
    }
    public Guest(String name, String streetAddress){
        this(); // This just calls the default constructor to increment the id number;
        this.name = name;
        this.streetAddress = streetAddress;
    }

    // GETTERS
    public String getName(){
        return this.name;
    }
    public String getStreetAddress(){
        return this.streetAddress;
    }


    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    // User class interface methods

    public String reserveRoom(){
        Integer roomID = generateId();

        String name = getName();
        LocalDate startDate = getStartDate();
        LocalDate endDate = getEndDate();

        ReservationClass reservation = new ReservationClass(roomID, name, startDate, endDate);

        // Return a message indicating the reservation was successful
        return "Room reserved successfully. Reservation ID: " + reservation.getIdNumber();
    }

    private Integer generateId() { // The reservation class should be generating this id when the constructor is called.

    }

    public void setIdNumber(Integer id){ // For the Guest
        id = this.idNumber;
    }

    public Integer getIdNumber(){ // For the Guest
        return this.idNumber;
    }

    private LocalDate getStartDate() {
        return LocalDate.now();
    }

    private LocalDate getEndDate() {
        return LocalDate.now().plusDays(1);
    }

}

