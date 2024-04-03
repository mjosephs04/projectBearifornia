package springboot;

import springboot.ReservationClass;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Room {
    private Double cost = 0.00;
    private Integer roomNumber, numOfBeds, qualityLevel = 0;
    private String typeOfRoom, bedType = "";
    private boolean smokingAllowed = false;

    private static final Logger logger = Logger.getLogger(Room.class.getName()); // Logger instance

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
    public List<Date> roomListToDates (int roomNumber) throws IOException {
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
    public boolean checkAvailability(ReservationClass potentialReservation) throws IOException {
        List<Date> datePairs = roomListToDates(potentialReservation.room.getRoomNumber());
        Date s1 = java.sql.Date.valueOf(potentialReservation.getStartDay());
        Date e1 = java.sql.Date.valueOf(potentialReservation.getEndDay());
        LocalDate currentDate = LocalDate.now();
        boolean available = true;

        // Convert LocalDate to Date
        Date currentTime = Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        if(s1.before(currentTime)){
            available = false;
        }
        for (int i = 0; i < datePairs.size(); i+=2 ) {
            Date s2 = datePairs.get(i);
            Date e2 = datePairs.get(i+1);
            if(s1.equals(e2)){
                available = false;
            }
            else if(s1.equals(s2)){
                available = false;
            }
            else if(e1.equals(s2)){
                available = false;
            }
            else if(e1.equals(e2)){
                available = false;
            }
            else if(s1.before(e2) && s1.after(s2)){
                available = false;
            }
            else if(e1.before(e2) && e1.after(s2)){
                available = false;
            }
        }

        return available;
    }


    //GETTERS
    public Double getCost() {
        return this.cost;
    }

    //SETTERS
    public void setCost(Double c) {
        this.cost = c;
    }

    public Integer getRoomNumber() {
        return this.roomNumber;
    }

    public void setRoomNumber(Integer num) {
        this.roomNumber = num;
    }

    public Integer getNumOfBeds() {
        return this.numOfBeds;
    }

    public void setNumOfBeds(Integer num) {
        this.numOfBeds = num;
    }

    public Integer getQualityLevel() {
        return this.qualityLevel;
    }

    public void setQualityLevel(Integer num) {
        this.qualityLevel = num;
    }

    public String getTypeOfRoom() {
        return this.typeOfRoom;
    }

    public void setTypeOfRoom(String s) {
        this.typeOfRoom = s;
    }

    public boolean getSmokingStatus() {
        return this.smokingAllowed;
    }

    public void setSmokingStatus(boolean b) {
        this.smokingAllowed = b;
    }

    public String getBedType() {
        return this.bedType;
    }

    public void setBedType(String s) {
        this.bedType = s;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Room) {
            return ((Room) obj).getRoomNumber().equals(roomNumber) && ((Room) obj).getNumOfBeds().equals(numOfBeds) && ((Room) obj).getQualityLevel().equals(qualityLevel) && ((Room) obj).getCost().equals(cost) && ((Room) obj).getTypeOfRoom().equals(typeOfRoom) && ((Room) obj).getBedType().equals(bedType);

        } else {
            return false;
        }
    }
}
