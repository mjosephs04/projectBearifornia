package springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.Room;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ApplicationController {
    @RequestMapping("/runFunction")
    public List<Room> convertCSV() {
        return null;
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
