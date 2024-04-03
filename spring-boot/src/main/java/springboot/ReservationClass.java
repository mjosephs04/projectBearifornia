package springboot;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReservationClass {

    private static Integer idNumber;
    private static String name;
    private static LocalDate startDay;
    private static LocalDate endDay;
    private static Integer price;
    public Room room;

    public ReservationClass(Integer id, String n) {
        idNumber = id;
        name = n;
    }

    public ReservationClass(Integer id, String n, LocalDate start, LocalDate end) {
        idNumber = id;
        name = n;
        startDay = start;
        endDay = end;
    }

    //returns either a failure message or "success"
    public String reserveRoom(Room reservedRoom){
        ArrayList<Room> availableRooms = null;
        try {
            availableRooms = (ArrayList<Room>) readInAvailableRooms();
        }
        catch(IOException e){
            e.printStackTrace();
            return "failure";
        }

        if(availableRooms.contains(reservedRoom)) {
            StringBuilder csvRoomFormat = new StringBuilder();
            csvRoomFormat.append(reservedRoom.getRoomNumber() + "," + reservedRoom.getCost() + "," +
                                reservedRoom.getBedType() + "," + reservedRoom.getNumOfBeds() + "," +
                                reservedRoom.getQualityLevel() + ",");

            if(reservedRoom.getSmokingStatus()){
                csvRoomFormat.append("Y");
            }
            else{
                csvRoomFormat.append("N");
            }

            String reserveRoom = addReservedRoom(csvRoomFormat.toString());
            if (reserveRoom.equals("success")) {
                return "success";
            }
            return "failure";
        }

        return "failure";
    }

    //returns the String that was removed from the csv file (commas included)
    //or it returns failure
    public String removeAvailableRoom(Room reservedRoom) throws IOException {
        ArrayList<Room> availableRoomList = (ArrayList<Room>) readInAvailableRooms();
        ArrayList<String> availableRoomsLines = (ArrayList<String>) readInAvailableRoomsLines();

        int indexRemove = availableRoomList.indexOf(reservedRoom);

        // Read and write lines, skipping the one to remove
        if (indexRemove >= 0 && indexRemove < availableRoomsLines.size()) {
            String removedLine = availableRoomsLines.get(indexRemove);
            availableRoomsLines.remove(indexRemove);
            FileWriter fw = new FileWriter("RoomsTaken.csv");
            try (BufferedWriter writer = new BufferedWriter(fw)) {
                for (String l : availableRoomsLines) {
                    writer.write(l);
                    writer.newLine();
                }
                //overwrite the last line in the csv which is now a duplicate
                writer.write("");
                writer.newLine();
                return removedLine;
            } catch (IOException e) {
                e.printStackTrace();
                return "failure, could not modify and write to the RoomsTaken database";
            }
        } else { //if the room to remove wasn't found in the available Rooms
            return "failure , room to reserve does not exist";
        }
    }

    //opens csv file and returns a list of all available rooms
    public List<Room> readInAvailableRooms() throws IOException {
        ArrayList<Room> availableRoomList = new ArrayList<>(); //store all the rooms we read in
        InputStream is = this.getClass().getResourceAsStream("/Rooms.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

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

            availableRoomList.add(currentRoom);
        }

        return availableRoomList;
    }

    public List<String> readInAvailableRoomsLines() throws IOException {
        List<String> availableRoomsLines = new ArrayList<>();
        InputStream is = this.getClass().getResourceAsStream("/Rooms.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        reader.readLine(); //skip first line of header info
        String line;

        //read in available rooms from csv and store in list
        while ((line = reader.readLine()) != null) {
            availableRoomsLines.add(line);
        }

        return availableRoomsLines;
    }

    //returns either a failure message or "success"
    public String reserveRoom(Room reservedRoom) {
        ArrayList<Room> availableRooms = null;
        try {
            availableRooms = (ArrayList<Room>) readInAvailableRooms();
        }catch(IOException e){
            e.printStackTrace();
            return "failure";
        }

        if (availableRooms.contains(reservedRoom)) {
            StringBuilder csvFormatRoom = new StringBuilder();

            csvFormatRoom.append(reservedRoom.getRoomNumber() + "," +
                                reservedRoom.getCost() + "," +
                                reservedRoom.getTypeOfRoom() + "," +
                                reservedRoom.getNumOfBeds() + "," +
                                reservedRoom.getQualityLevel() + "," +
                                reservedRoom.getBedType() + ",") ;
            if(reservedRoom.getSmokingStatus()){
                csvFormatRoom.append("Y");
            }
            else{
                csvFormatRoom.append("N");
            }

            String reserveRoom = addReservedRoom(csvFormatRoom.toString());
            if (reserveRoom.equals("success")) {
                return "success";
            }
        }
        return "failure";
    }

    //takes a csv formatted line and puts it into the RoomsTaken.csv
    //returns either success" or a fail message depending on result
    public String addReservedRoom(String newRoom) {
        InputStream is = this.getClass().getResourceAsStream("/RoomsTaken.csv");

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
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
            fw = new FileWriter("RoomsTaken.csv");
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

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReservationClass) {
            return ((ReservationClass) obj).getIdNumber().equals(idNumber) && ((ReservationClass) obj).getName().equals(name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, idNumber, endDay, startDay);
    }

    public static LocalDate getStartDay() {
        return startDay;
    }

    public static void setStartDay(LocalDate startDay) {
        ReservationClass.startDay = startDay;
    }

    public static LocalDate getEndDay() {
        return endDay;
    }

    public static void setEndDay(LocalDate endDay) {
        ReservationClass.endDay = endDay;
    }

    public static Integer getPrice() {
        return price;
    }

    public static void setPrice(Integer price) {
        ReservationClass.price = price;
    }
}


