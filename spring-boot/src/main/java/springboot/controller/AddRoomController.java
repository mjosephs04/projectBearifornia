package springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.service.RoomService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AddRoomController {

    @PostMapping("/addRoom")
    public ResponseEntity<String> addRoom(@RequestBody String[] roomDetails) {
        String message = null;
        try {
            message = RoomService.addRoom(roomDetails);
            return ResponseEntity.ok("Room successfully added to CSV.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + message + " " + e.getMessage());
        }
    }
}

