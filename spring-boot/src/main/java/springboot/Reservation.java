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

    // Constructors
    public Reservation(Integer id, String name) {
        this.idNumber = id;
        this.name = name;
    }


    public Reservation(Room room, LocalDate start, LocalDate end) {
        this.room = new Room(room);
        this.startDay = start;
        this.endDay = end;
    }

    public Double calculateCost(){
        Double cost = 0.0;
        Integer days = (int)ChronoUnit.DAYS.between(getStartDay(), getEndDay());
        Integer beds = room.getNumOfBeds();


        cost = switch (room.getTypeOfRoom().toLowerCase()) {
            case "urban elegance" -> beds * days * 53.2;
            case "vintage charm" -> beds * days * 60.0;
            case "nature retreat" -> beds * days * 40.0;
            default -> days * 35.0;
        };

        return cost;
    }


    //opens csv file and returns a list of all existing reservations
    public static List<Reservation> readInAllReservations() throws IOException {
        ArrayList<Reservation> reservations = new ArrayList<>(); //store all the rooms we read in
        BufferedReader reader = new BufferedReader(new FileReader("spring-boot/src/main/resources/RoomsTaken.csv"));

        reader.readLine(); //skip first line of header info
        String line;

        //read in available rooms from csv and store in list
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            Room currentRoom = new Room(Integer.parseInt(split[0]), //roomNumber
                    Double.parseDouble(split[1]),//cost
                    split[2], //roomType
                    Integer.parseInt(split[3]), //number of beds
                    Integer.parseInt(split[4]), //quality level
                    split[5], //bedType
                    Boolean.parseBoolean(split[6]) //smoking
            );
            // Define the date format pattern
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            // Parse the string to LocalDate
            LocalDate startD = LocalDate.parse(split[7], formatter);
            LocalDate endD = LocalDate.parse(split[8], formatter);
            Reservation currReservation = new Reservation(currentRoom, startD, endD);

            reservations.add(currReservation);
        }

        return reservations;
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

    //returns either a failure message or "success"
    public static String createReservation(String checkIn, String checkOut, int roomNumber, String name) {
        Room r = null;
        try {
            r = Room.findRoom(roomNumber);
        }
        catch(IOException e){
            return "fail";
        }
        // Parse the string into a ZonedDateTime
        ZonedDateTime startD = ZonedDateTime.parse(checkIn);
        ZonedDateTime endD = ZonedDateTime.parse(checkOut);

        // Extract the LocalDate part from the ZonedDateTime
        LocalDate startDate = startD.toLocalDate();
        LocalDate endDate = endD.toLocalDate();

        Reservation newReservation = new Reservation(r, startDate, endDate);

        String s = createReservation(newReservation);
        if(s.equalsIgnoreCase("success")) {
            return "success";
        }
        else{
            return "fail";
        }
    }


    //returns either a failure message or "success"
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

    /*
    public static void main(String[] args) {
        Reservation test = new Reservation();
        List<Room> rooms = null;
            test.addReservedRoom("hello,test");

    }*/

}


