package bearifornia.hotel;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Guest implements User {

    private static final Random random = new Random();
    // Data Members
    private String name = "";
    private String streetAddress = "";
    private Integer idNumber = 0;


    // Constructor and default constructor
    public Guest() {
        this.idNumber = random.nextInt(99999 - 10000 + 1) + 10000;
    }

    public Guest(String name, String streetAddress) {
        this(); // This just calls the default constructor to increment the id number;
        this.name = name;
        this.streetAddress = streetAddress;
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

    public String reserveRoom(Room reservedRoom) throws IOException {
        //attempt removing the available room from the RoomsAvailable.csv
        String removedRoom = removeAvailableRoom(reservedRoom);
        if(! removedRoom.equals("failure")){
            String reserveRoom = addReservedRoom(removedRoom);
            if(reserveRoom.equals("success")) {
                return "success";
            }
        }
        return "failure";
    }

    //returns the String that was removed from the csv file (commas included)
    //or it returns failure
    private String removeAvailableRoom(Room reservedRoom) throws IOException{
        ArrayList<Room> availableRoomList = new ArrayList<>(); //store all the rooms we read in
        InputStream is = this.getClass().getResourceAsStream("/RoomsAvailable.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        reader.readLine(); //skip first line of header info
        String line;
        //store all the lines in the csv
        List<String> availableRoomsLines = new ArrayList<>();

        //read in available rooms from csv and store in list
        while ((line = reader.readLine()) != null) {
            availableRoomsLines.add(line);

            String[] split = line.split(",");
            Room currentRoom = new Room(Double.parseDouble(split[1]),//cost
                    Integer.parseInt(split[0]), //roomNumber
                    Integer.parseInt(split[3]), //number of beds
                    Integer.parseInt(split[4]), //quality level
                    split[2], //roomType
                    split[6].equals("Y") //smoking
            );
            currentRoom.setBedType(split[5]); //bedType

            availableRoomList.add(currentRoom);
        }

        int indexRemove = availableRoomList.indexOf(reservedRoom);

        // Read and write lines, skipping the one to remove
        if(indexRemove >= 0 && indexRemove < availableRoomsLines.size()){
            String removedLine = availableRoomsLines.get(indexRemove);
            availableRoomsLines.remove(indexRemove);
            FileWriter fw = new FileWriter("RoomsTaken.csv");
            try(BufferedWriter writer = new BufferedWriter(fw)){
                for (String l : availableRoomsLines){
                    writer.write(l);
                    writer.newLine();
                }
                //overwrite the last line in the csv which is now a duplicate
                writer.write("");
                writer.newLine();
                return removedLine;
            }
            catch(IOException e){
                e.printStackTrace();
                return "Could not modify and write to the RoomsTaken database";
            }
        }
        else{ //if the room to remove wasn't found in the available Rooms
            return "Room to reserve does not exist";
        }
    }

    public String addAvailableRoom(Room newRoom){
        return "test";
    }

    //takes a csv formatted line and puts it into the RoomsTaken.csv
    //returns either "success" or a fail message depending on result
    private String addReservedRoom(String newRoom){
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
        if(! lines.contains(newRoom)) {
            lines.add(newRoom);
        }
        else{
            return "Room is already reserved";
        }

        FileWriter fw;
        try {
            fw = new FileWriter("RoomsTaken.csv");
        }
        catch(IOException x){
            x.printStackTrace();
            return "Could not write to RoomsTaken database";
        }

        // Write the updated content back to the CSV file
        try (BufferedWriter writer = new BufferedWriter(fw))
        {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return "Could not write to RoomsTaken database";
        }

        return "success";
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