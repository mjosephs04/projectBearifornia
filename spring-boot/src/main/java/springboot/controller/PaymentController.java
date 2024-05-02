package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.Cart;
import springboot.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/submitOrder")
    public ResponseEntity<String> submitOrder(@RequestBody Cart cart) {
        try {
            paymentService.checkout(cart);
            return ResponseEntity.ok("Order has been successfully processed.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process order: " + e.getMessage());
        }
    }
}

