package springboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Room {
    private static final Logger logger = Logger.getLogger(Room.class.getName()); // Logger instance
    private Double cost = 0.00;
    private Integer roomNumber, numOfBeds, qualityLevel = 0;
    private String typeOfRoom, bedType = "";
    private boolean smokingAllowed = false;

    //DEFAULT CONSTRUCTOR
    public Room() {
    }

    //CONSTRUCTOR
    public Room(Integer roomNum, Double c, String roomType, Integer numBed, Integer quality, String bedType, boolean smoking) {
        this.cost = c;
        this.roomNumber = roomNum;
        this.numOfBeds = numBed;
        this.qualityLevel = quality;
        this.typeOfRoom = roomType;
        this.smokingAllowed = smoking;
        this.bedType = bedType;
    }

    //Parse Dates
    public static Date parseDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            return sdf.parse(dateString);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing date: " + dateString, e);
            return null;
        }
    }

    //PRINT INFO
    public void printRoomInfo() {
        System.out.println(roomNumber);
        System.out.println("Room type: " + typeOfRoom);
        System.out.println("Bed type: " + bedType);
        System.out.println("# of beds: " + numOfBeds);
        System.out.println("Quality level: " + qualityLevel);
        if (!smokingAllowed) {
            System.out.println("Smoking not allowed.");
        } else {
            System.out.println("Smoking allowed.");
        }
    }

    //Read in Taken Rooms
    public List<String> readInTakenRoomsLines() throws IOException {
        List<String> takenRoomsLines = new ArrayList<>();
        InputStream is = this.getClass().getResourceAsStream("/RoomsTaken.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        reader.readLine(); //skip first line of header info
        String line;

        //read in available rooms from csv and store in list
        while ((line = reader.readLine()) != null) {
            takenRoomsLines.add(line);
        }

        return takenRoomsLines;
    }

    //Parse Taken Rooms - Takes a room number and gives all dates for that room
    public List<Date> roomListToDates(int roomNumber) throws IOException {
        List<String> takenRoomsLines = readInTakenRoomsLines();
        List<Date> datePairs = new ArrayList<>();

        //For every potential Reservation
        for (String line : takenRoomsLines) {
            String[] split = line.split(",");

            // Parse the room number
            int room = Integer.parseInt(split[0].trim());

            // Check if the room number matches
            if (room == roomNumber) {
                String startDateStr = split[1].trim(); // Assuming the start date is the second element
                String endDateStr = split[2].trim(); // Assuming the end date is the third element

                // Parse startDateStr and endDateStr into Date objects.
                Date startDate = parseDate(startDateStr);
                Date endDate = parseDate(endDateStr);

                // Add the pair of dates to the list
                datePairs.add(startDate);
                datePairs.add(endDate);
            }
        }
        return datePairs;
    }

    //Check Availability
    public boolean checkAvailability(Reservation potentialReservation) throws IOException {
        List<Date> datePairs = roomListToDates(potentialReservation.room.getRoomNumber());
        Date s1 = java.sql.Date.valueOf(potentialReservation.getStartDay());
        Date e1 = java.sql.Date.valueOf(potentialReservation.getEndDay());
        LocalDate currentDate = LocalDate.now();
        boolean available = true;

        // Convert LocalDate to Date
        Date currentTime = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if (s1.before(currentTime)) {
            available = false;
        }
        for (int i = 0; i < datePairs.size(); i += 2) {
            Date s2 = datePairs.get(i);
            Date e2 = datePairs.get(i + 1);
            if (s1.equals(e2)) {
                available = false;
            } else if (s1.equals(s2)) {
                available = false;
            } else if (e1.equals(s2)) {
                available = false;
            } else if (e1.equals(e2)) {
                available = false;
            } else if (s1.before(e2) && s1.after(s2)) {
                available = false;
            } else if (e1.before(e2) && e1.after(s2)) {
                available = false;
            }
        }

        return available;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getNumOfBeds() {
        return numOfBeds;
    }

    public void setNumOfBeds(Integer numOfBeds) {
        this.numOfBeds = numOfBeds;
    }

    public Integer getQualityLevel() {
        return qualityLevel;
    }

    public void setQualityLevel(Integer qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    public String getTypeOfRoom() {
        return typeOfRoom;
    }

    public void setTypeOfRoom(String typeOfRoom) {
        this.typeOfRoom = typeOfRoom;
    }

    public boolean getSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(boolean smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }

    public String getBedType(){
        return bedType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return smokingAllowed == room.smokingAllowed &&
                Objects.equals(getRoomNumber(), room.getRoomNumber()) &&
                Objects.equals(getNumOfBeds(), room.getNumOfBeds()) &&
                Objects.equals(getTypeOfRoom(), room.getTypeOfRoom()) &&
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCost(), getRoomNumber(), getNumOfBeds(), getQualityLevel(), getTypeOfRoom(), getTypeOfRoom(), smokingAllowed);
    }
}
