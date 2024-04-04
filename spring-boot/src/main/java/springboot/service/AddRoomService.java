package springboot.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AddRoomService {

    public void addRoom(String[] roomDetails) throws IOException {
        if (roomDetails.length != 7) {
            throw new IllegalArgumentException("Invalid number of room details provided.");
        }

        // Assuming roomDetails order is: roomNumber, cost, roomType, numOfBeds, qualityLevel, bedType, smokingAllowed
        try (FileWriter fw = new FileWriter("spring-boot/src/main/resources/Rooms.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            String line = String.join(",", roomDetails); // Join all details separated by commas
            out.println(line);
        } catch (IOException e) {
            throw new IOException("Failed to add room to CSV", e);
        }
    }

    //takes a csv formatted string with the info for a room and inserts it into the
    // database of available rooms
    //returns either "success" or a string containing "failure" depending on result
    public String addRoom2(String newRoom) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("spring-boot/src/main/resources/Rooms.csv"));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to read in the currently available rooms";
        }

        // Add the new line containing the new room
        if (!lines.contains(newRoom)) {
            lines.add(newRoom);
        } else {
            return "Room is already there";
        }

        FileWriter fw;
        try {
            fw = new FileWriter("spring-boot/src/main/resources/Rooms.csv");
        } catch (IOException x) {
            x.printStackTrace();
            return "Could not write to RoomsAvailable database";
        }

        // Write the updated content back to the CSV file
        try (BufferedWriter writer = new BufferedWriter(fw)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Could not write to Rooms database";
        }

        return "success";
    }
/*
    public static void main(String[] args) {
        AddRoomService x = new AddRoomService();
        String[] testRoomString = new String[7];
        testRoomString[0] = "room";
        testRoomString[1] = "next";
        testRoomString[2] = "3";
        testRoomString[3] = "3";
        testRoomString[4] = "3";
        testRoomString[5] = "3";
        testRoomString[6] = "3";

    try {
        x.addRoom(testRoomString);
    }
    catch(IOException e){

    }
    }*/
}

