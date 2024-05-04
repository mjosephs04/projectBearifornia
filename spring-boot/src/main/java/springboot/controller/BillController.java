package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.DateParsing;
import springboot.LoggedIn;
import springboot.Reservation;
import springboot.Room;
import springboot.service.BillService;
import springboot.service.CartService;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/bill")
public class BillController {

    private final BillService billService;
    CartController cartController;

    @Autowired
    public BillController(BillService billService, CartService cartService) {
        this.billService = billService;
        CartController cartController = new CartController(cartService);
    }

    @GetMapping("/getAccountName")
    public ResponseEntity<String> getAccountName() {
        if (LoggedIn.isLoggedIn()) {
            String accountName = billService.getAccountNameFromUser();
            return ResponseEntity.ok(accountName != null ? accountName : "No Name found on File");
        } else {
            return ResponseEntity.badRequest().body("You are not logged in.");
        }
    }

    @GetMapping("/getCardInfo")
    public ResponseEntity<String> getCardInfo() {
        return LoggedIn.isLoggedIn() ? ResponseEntity.ok("**** **** **** ****") :
                ResponseEntity.badRequest().body("You are not logged in.");
    }


    //Returns Double or String
    @GetMapping("/cartTotal")
    public ResponseEntity<?> getCartTotal() {
        return cartController.getCartTotal();

    }

    @PostMapping("/checkCost")
    //checkIn and checkOut must be in zonedDate format
    //**this function returns either
    public ResponseEntity<Double> calculateCost(@RequestBody
                                                String checkIn,
                                                @RequestBody String checkOut,
                                                @RequestBody Integer roomNumber){

    }


}
