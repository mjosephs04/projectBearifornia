package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.Room;
import springboot.Reservation;
import springboot.service.ReservationService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/search")
    public ResponseEntity<List<Room>> searchAvailableRooms(
            @RequestParam boolean smoking,
            @RequestParam String bedType,
            @RequestParam int numOfBeds,
            @RequestParam String roomType,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<Room> rooms = reservationService.searchAvailableRooms(smoking, bedType, numOfBeds, roomType, startDate, endDate);
        if (rooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createReservation(@RequestBody Reservation reservation) {
        String result = reservationService.createReservation(reservation);
        if ("success".equals(result)) {
            return ResponseEntity.ok("Reservation created successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to create reservation.");
        }
    }


    @PostMapping("/checkForRooms")
    public Room checkAvailability(@RequestBody boolean smoking,
                                  @RequestBody String bedType,
                                  @RequestBody String roomType,
                                  @RequestBody LocalDate startDate,
                                  @RequestBody LocalDate endDate) throws IOException {
        int bedNum;
        if(bedType.equals("Single")){
            bedNum = 1;
        }
        else if(bedType.equals("Double")){
            bedNum = 2;
        }
        else{
            bedNum = 3;
        }
        Reservation r = new Reservation();
        ArrayList<Room> availableRooms = (ArrayList<Room>) r.searchRooms(smoking,bedType,bedNum,roomType, startDate, endDate);

        if(availableRooms.isEmpty()){
            return null;
        }
        else{
            return availableRooms.get(0);
        }
    }
}
