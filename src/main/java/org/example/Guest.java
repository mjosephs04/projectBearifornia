package org.example;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Override
    public String reserveRoom() {
        return "Enter room parameter.";
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

    public String reserveRoom(Room r) throws IOException {
        Integer roomID = generateId();
        String name = getName();
        LocalDate startDate = getStartDate();
        LocalDate endDate = getEndDate();
        Room reservedRoom = r;

        ReservationClass reservation = new ReservationClass(roomID, name, startDate, endDate);

        //read in csv file hopefully and transfer the given room into the other csv somehow lmao
        ArrayList<Room> roomList = new ArrayList<>();
        BufferedReader reader = null;
        Room currentRoom = new Room();

        InputStream is = getClass().getClassLoader().getResourceAsStream("RoomsAvailable.csv");
        reader = new BufferedReader(new InputStreamReader(is));

        reader.readLine(); //skip first line
        String line = null;

        //read in csv
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            currentRoom.setCost(Double.parseDouble(split[1]));
            currentRoom.setRoomNumber(Integer.parseInt(split[0]));
            currentRoom.setTypeOfRoom(split[2]);
            currentRoom.setNumOfBeds(Integer.parseInt(split[3]));
            currentRoom.setQualityLevel(Integer.parseInt(split[4]));
            currentRoom.setBedType(split[5]);
            currentRoom.setSmokingStatus(split[6] == "Y");

            roomList.add(currentRoom);
        }

        Integer lineToRemove = roomList.indexOf(r) + 1;
        ClassLoader classLoader = getClass().getClassLoader();
        String resourceFolderPath = classLoader.getResource("").getPath();
        String resourcesPath = resourceFolderPath + "resources" + File.separator;
        String filePath = resourcesPath + "RoomsAvailable.csv";
        try {
            // Open the original file for reading
            BufferedReader reader1 = new BufferedReader(new FileReader(filePath));

            // Create a temporary file to write the updated content
            File tempFile = new File("temp.csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            // Read and write lines, skipping the one to remove
            String currentLine;
            int lineNumber = 0;
            while ((currentLine = reader1.readLine()) != null) {
                lineNumber++;
                // If the current line is not the one to remove, write it to the temporary file
                if (lineNumber != lineToRemove) {
                    writer.write(currentLine);
                    writer.newLine(); // Write new line separator
                }
            }

            // Close resources
            reader1.close();
            writer.close();

            // Delete the original file
            File originalFile = new File(filePath);
            if (!originalFile.delete()) {
                System.out.println("Failed to delete the original file.");
                return "out";
            }

            // Rename the temporary file to the original filename
            if (!tempFile.renameTo(originalFile)) {
                System.out.println("Failed to rename the temporary file.");
            }

            System.out.println("Line removed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //write info onto other csv
        // Output file path
        filePath = resourcesPath + "RoomsTaken.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.newLine();
            // Write data
            writer.write(r.getRoomNumber());
            writer.write(",");
            writer.write(r.getCost() + "");
            writer.write(",");
            writer.write(r.getTypeOfRoom());
            writer.write(",");
            writer.write(r.getNumOfBeds());
            writer.write(",");
            writer.write(r.getQualityLevel());
            writer.write(",");
            writer.write(r.getBedType());
            writer.write(",");
            if (!r.getSmokingStatus()) {
                writer.write("N");
            } else {
                writer.write("Y");
            }

            System.out.println("Data has been written to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return a message indicating the reservation was successful
        return "Room reserved successfully. Reservation ID: " + reservation.getIdNumber();
    }

    private Integer generateId() { // The reservation class should be generating this id when the constructor is called.
        return 1;
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

