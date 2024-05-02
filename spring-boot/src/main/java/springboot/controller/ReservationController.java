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
import java.util.List;

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
            x = UserFunctions.findUser(payload[3]);

        //find the room associated with the room number
        Room room;
        room = Room.findRoom(Integer.parseInt(payload[2]));

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
        availableRooms = (ArrayList<Room>) Room.searchRooms(
                Boolean.parseBoolean(payload[0]),
                payload[1], bedNum, payload[2],
                payload[3], payload[4]);

        if(availableRooms.isEmpty()){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(availableRooms.get(0));
        }
    }


    @PostMapping("/checkCost") //do i need to add this?
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
        room = Room.findRoom(roomNumber);

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

    //returns the current users reservations
    @GetMapping("/showMyReservations")
    public ResponseEntity<List<Reservation>> showMyReservations(){
        String username = LoggedIn.isLoggedIn();

        if(username != null) {
            User user = UserFunctions.findUser(username);

            if (!(user instanceof Guest)) {
                return ResponseEntity.badRequest().body(null);//throws an exception if the username is not associated with a guest account,
                //or if the username is not associated with an account at all
            }

            //otherwise, if the username is associated with a guest account, returns all reservations
            return ResponseEntity.ok(((Guest) user).getMyReservations());
        }
        return ResponseEntity.badRequest().body(null);
    }


    //pass the username of the admin/user trying to display all guests.
    //this function will check that the username really is associated with an
    //admin account and then will return all guests accordingly.
    @PostMapping("/getAllGuests")
    public ResponseEntity<List<User>> getAllGuests(){
        String username = LoggedIn.isLoggedIn();
        User user = UserFunctions.findUser(username);

        if(username != null && user instanceof Admin) {

            List<User> list;
            list = UserFunctions.readInAllUsers();

            //get only the guests from the list
            list.removeIf(curr -> !(curr instanceof Guest));

            return ResponseEntity.ok(list);
        }
        else{
            return ResponseEntity.badRequest().body(null); //returns null list
        }
    }

    //payload should contain:
    // String newStartDate, String newEndDate, int roomNumber, String oldStartDate, String oldEndDate
    @PatchMapping("/updateRes")
    public ResponseEntity<String> updateReservation(String[] payload) {
        String message = ReservationService.modifyReservation(payload[0], payload[1], Integer.parseInt(payload[2]), payload[3], payload[4]);

        if(message.equalsIgnoreCase("success")){
            return ResponseEntity.ok("successfully modified reservation");
        }
        else{
            return ResponseEntity.badRequest().body(message);
        }
    }

    //payload contains: String checkInDate, String checkOutDate, int roomNumber
    @GetMapping("/deleteRes")
    public ResponseEntity<String> deleteReservation(String[] payload) {
        String username = LoggedIn.isLoggedIn();
        if(username != null) {
            String message = ReservationService.deleteReservation(payload[0], payload[1], Integer.parseInt(payload[2]), username);

            if (message.equalsIgnoreCase("success")) {
                return ResponseEntity.ok("successfully modified reservation");
            }
            else{
                return ResponseEntity.badRequest().body(message);
            }
        }
        else{
            return ResponseEntity.badRequest().body("you need to log in first!");
        }
    }
}
