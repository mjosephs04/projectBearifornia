package springboot;

import java.io.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    public Reservation(){
        room = null;
    }


    public Reservation(Room room, LocalDate start, LocalDate end) {
        this.room = room;
        this.startDay = start;
        this.endDay = end;
    }

    //opens csv file and returns a list of all existing rooms
    public List<Room> readInAllRooms() throws IOException {
        ArrayList<Room> roomList = new ArrayList<>(); //store all the rooms we read in
        BufferedReader reader = new BufferedReader(new FileReader("spring-boot/src/main/resources/Rooms.csv"));

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
                    split[6].equals("Y") //smoking
            );

            roomList.add(currentRoom);
        }

        return roomList;
    }


    //opens csv file and returns a list of all existing reservations
    public List<Reservation> readInAllReservations() throws IOException {
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


    // Search for available rooms based on criteria
    //the two strings at the end are in the format: 2024-04-20T20:39:06.000Z
    public List<Room> searchRooms(boolean smoking, String bedType, int bedNum, String roomType, String start, String end) throws IOException {
        List<Room> rooms = readInAllRooms();
        List<Reservation> allReservations = readInAllReservations(); // Assuming this method exists to read available rooms

        // Parse the string into a ZonedDateTime
        ZonedDateTime startD = ZonedDateTime.parse(start);
        ZonedDateTime endD = ZonedDateTime.parse(end);

        // Extract the LocalDate part from the ZonedDateTime
        LocalDate startDate = startD.toLocalDate();
        LocalDate endDate = endD.toLocalDate();

        //so now we will check all rooms only rooms that match the desired criteria
        // if room does NOT match criteria, remove it from list
        rooms.removeIf(curr -> !(curr.getSmokingAllowed() == smoking &&
                curr.getBedType().equals(bedType) &&
                curr.getNumOfBeds() == bedNum &&
                curr.getTypeOfRoom().equals(roomType)));

        // Iterate through all rooms
        for (Room room : rooms) {
            //for each room, check if that room has any reservations associated with it
            for(Reservation res : allReservations){
                //if it does, remove it from the total list of rooms if it isn't available for
                //the desired dates
                if(res.getRoom().equals(room)){
                    if(! isAvailable(res, startDate, endDate)){
                        rooms.remove(res.room);
                    }
                }
            }
        }
        //at the end of this loop, the list rooms only contains rooms available
        //during the desired timeframe and which match the desired criteria

        return rooms;
    }

    public boolean isAvailable(Reservation r, LocalDate start, LocalDate end){
        boolean result = true;

        if(r.startDay.isBefore(start)){
            if(r.endDay.isAfter(start) || r.endDay.equals(startDay)){
                result = false;
            }
        }
        else if(r.startDay.isAfter(start)){
            if(r.startDay.isBefore(end)){
                result = false;
            }
        }
        else if(r.startDay.equals(r.endDay)){
            result = false;
        }


        return result;
    }

    //returns either a failure message or "success"
    public String createReservation(Reservation newReservation) {
        ArrayList<Reservation> existingReservations = null;
        try {
            existingReservations = (ArrayList<Reservation>) readInAllReservations();
        } catch (IOException e) {
            e.printStackTrace();
            return "failure";
        }

        if (!existingReservations.contains(newReservation)) {
            StringBuilder csvFormatRoom = new StringBuilder();
            Room reservedRoom = newReservation.room;

            //convert room to a csv line in desired format
            csvFormatRoom.append(reservedRoom.getRoomNumber()).append(",").
                                append(reservedRoom.getCost()).append(",").
                                append(reservedRoom.getTypeOfRoom()).append(",").
                                append(reservedRoom.getNumOfBeds()).append(",").
                                append(reservedRoom.getQualityLevel()).append(",").
                                append(reservedRoom.getTypeOfRoom()).append(",");
            if (reservedRoom.getSmokingAllowed()) {
                csvFormatRoom.append("Y" + ",");
            } else {
                csvFormatRoom.append("N" + ",");
            }
            // Define the desired date format pattern
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            // Format the LocalDate object using the formatter
            String start = newReservation.getStartDay().format(formatter);
            String end = newReservation.getEndDay().format(formatter);

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
    public String addReservedRoom(String newRoom) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("spring-boot/src/main/resources/RoomsTaken.csv"))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to read in the currently reserved rooms";
        }

        // Add the new line containing the new reservation
        if (!lines.contains(newRoom)) {
            lines.add(newRoom);
        } else {
            return "Room is already reserved";
        }

        FileWriter fw;
        try {
            fw = new FileWriter("spring-boot/src/main/resources/RoomsTaken.csv");
        } catch (IOException x) {
            x.printStackTrace();
            return "Could not write to RoomsTaken database";
        }

        // Write the updated content back to the CSV file
        try (BufferedWriter writer = new BufferedWriter(fw)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Could not write to RoomsTaken database";
        }

        return "success";
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
            return ((Reservation) obj).getIdNumber().equals(idNumber) &&
                    ((Reservation) obj).getName().equals(name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, idNumber, endDay, startDay);
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

    public static void main(String[] args) {
        Reservation test = new Reservation();
        List<Room> rooms = null;
        try{
            rooms = test.searchRooms(false, "Single", 1, "Urban Elegance", "2024-04-20T20:39:06.000Z", "2024-04-22T20:39:06.000Z");
        }catch(IOException e) {
            System.out.println("failll");
        }

        rooms.forEach(room -> System.out.println(room.getRoomNumber()));

    }

}


