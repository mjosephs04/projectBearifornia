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

    //payload should contain name, username, password, userType
    @PostMapping("/createClerk")
    public ResponseEntity<String> createClerk(@RequestBody String[] payload) {
        UserType userType = UserType.valueOf(payload[3]);
        String message = accountService.createClerk(payload[0], payload[1], payload[2], userType);

        if ("success".equalsIgnoreCase(message)) {
            return ResponseEntity.ok("Created clerk account.");
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }

    //payload contains: name, username, password, USERTYPE
    @PostMapping("/createGuest")
    public ResponseEntity<String> createGuest(@RequestBody String[] payload) {
        UserType userType = UserType.valueOf(payload[3]);
        String message = accountService.createGuest(payload[0], payload[1], payload[2], userType);

        if ("success".equalsIgnoreCase(message)) {
            return ResponseEntity.ok("Created guest account.");
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }
}
