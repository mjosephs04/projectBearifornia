package com.example.springboot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class MyController {

    @RequestMapping("/runFunction")
    public List<Room> convertCSV() {

        List<Room> listOfRooms = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader("src/main/resources/RoomsAvailable.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                Room nRoom = new Room(Double.parseDouble(row[1]), Integer.parseInt(row[0]), Integer.parseInt(row[3]), Integer.parseInt(row[4]), row[2], Boolean.parseBoolean(row[6]));
                listOfRooms.add(nRoom);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return listOfRooms;
    }

    @PostMapping("/endpoint")
    public ResponseEntity<String> handlePostRequest(@RequestBody String requestBody) {
        System.out.println(requestBody);
        return ResponseEntity.ok("Request received successfully");
    }

    @GetMapping("/img")
    public ResponseEntity<String> getImageLink() {
        // Replace this with the actual URL or path to your image
        String imageUrl = "https://www.grandbearresort.com/images/slider/slider-2.jpg";

        return ResponseEntity.ok(imageUrl);
    }
}
