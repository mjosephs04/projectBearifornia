package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.*;
import springboot.database.InitializeDatabase;
import springboot.database.Setup;
import springboot.service.AccountService;
import springboot.service.ReservationService;

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
    //checkIn, checkOut, roomNumber, USERNAME!!!!!!----> username is only passed if a clerk is making
    //                                              a reservation on behalf of a guest, and in that case
    //                                              then it should be the guest's username
                                                    //if a guest is logged in, you DO NOT NEED TO PASS
    //                                              A FOURTH STRING
    // adds reservation to database if it does not already exist
    @PostMapping("/create")
    public ResponseEntity<String> createReservation(@RequestBody String[] payload) {
        String username = null;
        String message;
        boolean good = true;
        //if payload is length three, then they should be trying to make a reservation
        //for the current user that is logged in
        if(payload.length == 3) {
            username = LoggedIn.isLoggedIn();
        } else if(payload.length >= 4) {
            username = payload[3];
        }
        if(username != null && ! LoggedIn.type.equals(UserType.ADMIN)) {
            //find the room associated with the room number
            Room room = Room.findRoom(Integer.parseInt(payload[2]));

            if(room != null) {
                return ResponseEntity.ok().body(Reservation.addToDatabase(DateParsing.convertStringToDate(payload[0]),
                        DateParsing.convertStringToDate(payload[1]),
                        room.getRoomNumber(),
                        username));
            }
            else{
                return ResponseEntity.badRequest().body("room does not exist");
            }
        }
        else{
            return ResponseEntity.badRequest().body("you must be logged in");
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
        // Extract the LocalDate part from the ZonedDateTime
        LocalDate startDate = DateParsing.convertStringToDate(checkIn);
        LocalDate endDate = DateParsing.convertStringToDate(checkOut);

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
    public ResponseEntity<Reservation> showMyReservations(){
        String username = LoggedIn.isLoggedIn();

        if(username != null && LoggedIn.type.equals(UserType.GUEST)) {
            //otherwise, if the username is associated with a guest account, returns all reservations
            return ResponseEntity.ok().body(Guest.getMyReservations(username).get(0));
        }

        return ResponseEntity.badRequest().body(null);
    }


    //pass the username of the admin/user trying to display all guests.
    //this function will check that the username really is associated with an
    //admin account and then will return all guests accordingly.
    @PostMapping("/getAllGuests")
    public ResponseEntity<List<User>> getAllGuests(){
        String username = LoggedIn.isLoggedIn();

        if(username != null && LoggedIn.type.equals(UserType.ADMIN)) {
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
    //yyyy-mm-dd is how the dates are passed
    // String newStartDate, String newEndDate, int roomNumber, String oldStartDate, String oldEndDate
    @PatchMapping("/updateRes")
    public ResponseEntity<String> updateReservation(@RequestBody String[] payload) {
        String message = ReservationService.modifyReservation(payload[0], payload[1], Integer.parseInt(payload[2]), payload[3], payload[4]);

        if(message.equalsIgnoreCase("success")){
            return ResponseEntity.ok("successfully modified reservation");
        }
        else{
            return ResponseEntity.badRequest().body(message);
        }
    }


    //payload should contain
    //yyyy-mm-dd is how the dates are passed
    //payload contains: String checkInDate, String checkOutDate, int roomNumber
    @PostMapping("/deleteRes")
    public ResponseEntity<String> deleteReservation(@RequestBody String[] payload) {
        String username = LoggedIn.isLoggedIn();
        if(username != null) {
            String message = ReservationService.deleteReservation(payload[0], payload[1], Integer.parseInt(payload[2]), username);

            if (message.equalsIgnoreCase("success")) {
                return ResponseEntity.ok("successfully deleted reservation");
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
