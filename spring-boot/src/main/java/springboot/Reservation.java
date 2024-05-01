package springboot;

import java.io.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reservation {

    Room room;
    private Integer idNumber;
    private String name;
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

    public Reservation(Room room, LocalDate start, LocalDate end, String n, String e, String a) {
        this.room = new Room(room);
        this.startDay = start;
        this.endDay = end;
        this.name = n;
        email = e;
        address = a;
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

    //opens csv file and returns a list of all existing reservations
    public static List<Reservation> readInAllReservations() throws IOException {
        return null;
    }

    public boolean isAvailable(LocalDate start, LocalDate end){
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
    public static String createReservation(String checkIn, String checkOut, int roomNumber, String name) {
        Room r = null;
        try {
            r = Room.findRoom(roomNumber);
        }
        catch(IOException e){
            return "fail";
        }

        LocalDate startDate = convertStringToDate(checkIn);
        LocalDate endDate = convertStringToDate(checkOut);

        Reservation newReservation = new Reservation(r, startDate, endDate);
        newReservation.setName(name);

        String s = createReservation(newReservation);
        if(s.equalsIgnoreCase("success")) {
            return "success";
        }
        else{
            return "fail";
        }
    }


    //returns either a failure message or "success"
    //calls addReservedRoom to add it to csv
    public static String createReservation(Reservation r) {
        ArrayList<Reservation> existingReservations = null;
        try {
            existingReservations = (ArrayList<Reservation>) readInAllReservations();
        } catch (IOException e) {
            e.printStackTrace();
            return "failure";
        }

        if (!existingReservations.contains(r)) {
            StringBuilder csvFormatRoom = new StringBuilder();
            Room reservedRoom = r.getRoom();

            //convert room to a csv line in desired format
            csvFormatRoom.append(reservedRoom.getRoomNumber()).append(",").
                                append(reservedRoom.getCost()).append(",").
                                append(reservedRoom.getTypeOfRoom()).append(",").
                                append(reservedRoom.getNumOfBeds()).append(",").
                                append(reservedRoom.getQualityLevel()).append(",").
                                append(reservedRoom.getTypeOfRoom()).append(",").
                                append(reservedRoom.getSmokingAllowed());

            // Define the desired date format pattern
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            // Format the LocalDate object using the formatter
            String start = r.getStartDay().format(formatter);
            String end = r.getEndDay().format(formatter);

            csvFormatRoom.append(start).append(",").append(end);

            String reserveRoom = addReservedRoom(csvFormatRoom.toString());
            if (reserveRoom.equals("success")) {
                return "success";
            }
        } else {
            return "failure";
        }

        return "failure";
    }

    //takes a csv formatted line and puts it into the RoomsTaken.csv
    //returns either "success" or a fail message depending on result
    public static String addReservedRoom(String newRoom) {
        try (FileWriter fw = new FileWriter("spring-boot/src/main/resources/RoomsTaken.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(newRoom);
            return "success";
        } catch (IOException e) {
            return "failure to add new reservation";
        }
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Integer idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

/*
    public static void main(String[] args) {

    }*/

}


