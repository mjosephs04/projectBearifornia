package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.service.AddRoomService;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AddRoomController {

    private final AddRoomService addRoomService;

    @Autowired
    public AddRoomController(AddRoomService addRoomService) {
        this.addRoomService = addRoomService;
    }

    @PostMapping("/addRoom")
    public ResponseEntity<String> addRoom(@RequestBody String[] roomDetails) {
        String message = null;
        try {
            message = addRoomService.addRoom(roomDetails);
            return ResponseEntity.ok("Room successfully added to CSV.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + message + " " + e.getMessage());
        }
    }
}

