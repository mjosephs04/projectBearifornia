package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.*;
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

    //CREATE RESERVATION -- payload should contain this info IN THIS ORDER!!!
    //checkIn, checkOut, roomNumber, USERNAME!!!!!!----> if this is a guest making the reservation, then it should be
                                                        //their personal username. if it's a clerk making
                                                        //a reservation on behalf of a guest, then it should be the
                                                        //guest's username
    // adds reservation to database if it does not already exist
    @PostMapping("/create")
    public ResponseEntity<String> createReservation(@RequestBody String[] payload) {
        User x;
        //try to find the user associated with the username
        try {
            x = UserFunctions.findUser(payload[3]);
        }
        catch(IOException e){
            return ResponseEntity.badRequest().body("User not found.");
        }

        //find the room associated with the room number
        Room room;
        try{ room = Room.findRoom(Integer.parseInt(payload[2])); }
        catch(IOException e ){
            return ResponseEntity.badRequest().body("Room number not found.");
        }

        //if the given username belongs to a guest, then make the reservation.
        //otherwise, return an error message
        if(x instanceof Guest){
            String result = ((Guest) x).reserveRoom(room, payload[0], payload[1]);
            if ("success".equals(result)) {
                return ResponseEntity.ok("Reservation created successfully.");
            } else {
                return ResponseEntity.badRequest().body("Failed to create reservation.");
            }
        }
        //if username belongs to admin -- not allowed
        else if(x instanceof Admin){
            return ResponseEntity.badRequest().body("Admins cannot make reservations.");
        }
        //if username belongs to clerk --- they should have given the guest's username instead, not their own
        else{
            return ResponseEntity.badRequest().body("Clerks can only make reservations on behalf of Guests.");
        }
    }


    //payload should contain the following info in this order:
    // smoking, bedType (single/double/family),
    // roomType (urban elegance/vintage charm/nature retreat),
    // checkInDate (in zoned time format), checkOutDate (in zoned time format)
    //** this function then either returns null or a room depending on success rate
    @PostMapping("/checkForRooms")
    public ResponseEntity<Room> checkAvailability(@RequestBody String[] payload) {
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

        ArrayList<Room> availableRooms;
        try {
            availableRooms = (ArrayList<Room>) Room.searchRooms(
                    Boolean.parseBoolean(payload[0]),
                    payload[1], bedNum, payload[2],
                    payload[3], payload[4]);

        }catch(IOException e){
            return ResponseEntity.badRequest().body(null);
        }

        if(availableRooms.isEmpty()){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(availableRooms.get(0));
        }
    }

    //checkIn and checkOut must be in zonedDate format
    //**this function returns either
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

        //try to find room associated with the roomNumber
        try {
            room = Room.findRoom(roomNumber);
        }
        catch(IOException e){
            return ResponseEntity.badRequest().body(-1.0);
        }

        //if we found the room, check the cost!
        if(room != null){
            Reservation r = new Reservation(room, startDate, endDate);
            cost = r.calculateCost();
        }

        //if the cost is less than 0, something went wrong
        if (cost <= 0.0){
            return ResponseEntity.badRequest().body(cost);
        }
        //otherwise successsss!!!!!!!!!!!!!!!!!!!!!!
        else{
            return ResponseEntity.ok(cost);
        }
    }
}
