package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.service.AddRoomService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AddRoomController {

    private final AddRoomService addRoomService;

    @Autowired
    public AddRoomController(AddRoomService addRoomService) {
        this.addRoomService = addRoomService;
    }

    @PostMapping("/addRoom")
    public ResponseEntity<String> addRoom(@RequestBody String[] roomDetails) {
        String x;

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

