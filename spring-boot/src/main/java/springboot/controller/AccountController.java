package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.service.AccountService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/register")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/createClerk")
    public ResponseEntity<String> createClerk(@RequestBody String[] payload) {
        String message = accountService.createClerk(payload[0], payload[1], payload[2]);

        if ("success".equalsIgnoreCase(message)) {
            return ResponseEntity.ok("Created account.");
        } else {
            return ResponseEntity.badRequest().body("Failed to create account. " + message);
        }
    }

    @PostMapping("/createGuest")
    public ResponseEntity<String> createGuest(@RequestBody String[] payload) {
        String message = accountService.createGuest(payload[0], payload[1], payload[2]);

        if ("success".equalsIgnoreCase(message)) {
            return ResponseEntity.ok("Created account.");
        } else {
            return ResponseEntity.badRequest().body("Failed to create account. " + message);
        }
    }
}
