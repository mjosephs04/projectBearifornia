package springboot.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
}

