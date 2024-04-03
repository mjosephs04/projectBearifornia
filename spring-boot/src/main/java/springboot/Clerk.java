package springboot;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Clerk implements User {

    private static UserType classification;
    private String name;
    private Integer idNumber;

    public Clerk() {
        classification = UserType.CLERK;
    }

    public Clerk(String n, Integer id) {
        this.name = n;
        this.idNumber = id;
        classification = UserType.CLERK;
    }

    public UserType getType() {
        return classification;
    }

    //takes a csv formatted string with the info for a room and inserts it into the
    // database of available rooms
    //returns either "success" or a string containing "failure" depending on result
    public String addRoom(String newRoom) {
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String x) {
        name = x;
    }

    @Override
    public Integer getIdNumber() {
        return idNumber;
    }

    @Override
    public void setIdNumber(Integer id) {
        idNumber = id;
    }
}
