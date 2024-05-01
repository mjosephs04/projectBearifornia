package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.UserType;
import springboot.dto.UserDto;
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
    public ResponseEntity<String> createClerk(@RequestBody UserDto payload) {
        UserType userType = UserType.valueOf(payload.getUserType().toUpperCase());
        String message = accountService.createClerk(payload.getName(), payload.getUsername(), payload.getPassword(), userType);

        if ("success".equalsIgnoreCase(message)) {
            return ResponseEntity.ok("Created clerk account.");
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }

    @PostMapping("/createGuest")
    public ResponseEntity<String> createGuest(@RequestBody UserDto payload) {
        UserType userType = UserType.valueOf(payload.getUserType().toUpperCase());
        String message = accountService.createGuest(payload.getName(), payload.getUsername(), payload.getPassword(), userType);

        if ("success".equalsIgnoreCase(message)) {
            return ResponseEntity.ok("Created guest account.");
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }
}
