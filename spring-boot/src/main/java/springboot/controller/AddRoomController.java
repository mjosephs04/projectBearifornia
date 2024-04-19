package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.service.AddRoomService;

import java.io.IOException;

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
        try {
            addRoomService.addRoom(roomDetails);
            return ResponseEntity.ok("Room successfully added to CSV.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to add room: " + e.getMessage());
        }
    }
}

