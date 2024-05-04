package springboot.controller;

import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.LoggedIn;
import springboot.UserType;
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

    @GetMapping("/LOGGED-IN")
    public ResponseEntity<Boolean> loggedIn() {
        return ResponseEntity.ok(LoggedIn.isLoggedIn());
    }


    //userType backend:
    //return null if not logged in
    //otherwise, return type of user
    @GetMapping("/getUserType")
    public ResponseEntity<UserType> getUserType() {
        return ResponseEntity.ok(LoggedIn.getType());
    }

    @GetMapping("/logOut")
    public ResponseEntity<Boolean> logOut() {
        LoggedIn.logIn(null, null);

        if (LoggedIn.isLoggedIn()) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok().body(false);
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

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody String[] payload) {
        String username = payload[0];
        String newPassword = payload[1];
        String message = accountService.changePassword(username, newPassword);

        if ("Password updated successfully.".equalsIgnoreCase(message)) {
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }
}
