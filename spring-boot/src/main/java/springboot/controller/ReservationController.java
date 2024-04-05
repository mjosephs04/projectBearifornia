package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.Room;
import springboot.Reservation;
import springboot.service.ReservationService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<String> createReservation(@RequestBody String[] payload) {
        String result = reservationService.createReservation(payload[0], payload[1], Integer.parseInt(payload[2]), payload[3]);
        if ("success".equals(result)) {
            return ResponseEntity.ok("Reservation created successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to create reservation.");
        }
    }


    @PostMapping("/checkForRooms")
    public ResponseEntity<Room> checkAvailability(@RequestBody String[] payload) throws IOException {
        int bedNum;
        if(payload[1].equals("single")){
            bedNum = 1;
        }
        else if(payload[1].equals("double")){
            bedNum = 2;
        }
        else{
            bedNum = 3;
        }
        ArrayList<Room> availableRooms = (ArrayList<Room>) Room.searchRooms(
                                                        Boolean.parseBoolean(payload[0]),
                                                        payload[1],bedNum,payload[2],
                                                        payload[3], payload[4]);

        if(availableRooms.isEmpty()){
            return ResponseEntity.badRequest().body(new Room());
        }
        else{
            return ResponseEntity.ok(availableRooms.get(0));
        }
    }

    public ResponseEntity<Double> calculateCost(@RequestBody
                                 String checkIn,
                                 @RequestBody String checkOut,
                                 @RequestBody Integer roomNumber){
        Room room;
        Double cost = 0.0;

        // Parse the string into a ZonedDateTime
        ZonedDateTime startD = ZonedDateTime.parse(checkIn);
        ZonedDateTime endD = ZonedDateTime.parse(checkOut);

        // Extract the LocalDate part from the ZonedDateTime
        LocalDate startDate = startD.toLocalDate();
        LocalDate endDate = endD.toLocalDate();

        try {
            room = Room.findRoom(roomNumber);
        }
        catch(IOException e){
            return ResponseEntity.badRequest().body(cost);
        }

        if(room != null){
            Reservation r = new Reservation(room, startDate, endDate);
            cost = r.calculateCost();
        }

        if (cost <= 0.0){
            return ResponseEntity.badRequest().body(cost);
        }
        else{
            return ResponseEntity.ok(cost);
        }
    }
}
