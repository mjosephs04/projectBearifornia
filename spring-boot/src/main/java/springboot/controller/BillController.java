package springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.LoggedIn;
import springboot.service.BillService;
import springboot.service.CartService;
import springboot.service.ReservationService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/bill")
public class BillController {

    @GetMapping("/getAccountName")
    public ResponseEntity<String> getAccountName() {
        if (LoggedIn.isLoggedIn()) {
            String accountName = BillService.getAccountNameFromUser();
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


    //Returns Double price success
    //Returns badRequest you are not logged in if not logged in
    @GetMapping("/cartTotal")
    public ResponseEntity<Double> getCartTotal() {
        if (LoggedIn.isLoggedIn()) {
            Double total = CartService.getPriceCart();
            return ResponseEntity.ok(total);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/checkCostReservation")
    public ResponseEntity<Double> calculateCost(@RequestBody String checkIn,
                                                @RequestBody String checkOut,
                                                @RequestBody Integer roomNumber) {
        if (LoggedIn.isLoggedIn()) {
            Double cost = ReservationService.calculateCost(checkIn, checkOut, roomNumber);
            if (cost != null) {
                return ResponseEntity.ok(cost);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    //Return Double Tax if Works
    //Return Null if any errors with either costRoom or CostShop or if not logged in
    @PostMapping("/getTax")
    public ResponseEntity<Double> getTax(@RequestBody String checkIn,
                                         @RequestBody String checkOut,
                                         @RequestBody Integer roomNumber) {
        if (LoggedIn.isLoggedIn()) {
            Double costRoom = ReservationService.calculateCost(checkIn, checkOut, roomNumber);
            Double costShop = CartService.getPriceCart();

            if (costRoom == null || costShop == null) {
                return null;
            }
            Double cost = BillService.getTax(costRoom,costShop);
            return ResponseEntity.ok(cost);
        } else {
            return null;
        }
    }


    @PostMapping("/getTotalBill")
    public ResponseEntity<Double> getTotalBill(@RequestBody String checkIn,
                                         @RequestBody String checkOut,
                                         @RequestBody Integer roomNumber) {
        if (LoggedIn.isLoggedIn()) {
            Double costRoom = ReservationService.calculateCost(checkIn, checkOut, roomNumber);
            Double costShop = CartService.getPriceCart();
            if (costRoom == null || costShop == null) {
                return null;
            }
            Double cost = BillService.getPriceFinal(costRoom,costShop);
            return ResponseEntity.ok(cost);
        } else {
            return null;
        }
    }





}
