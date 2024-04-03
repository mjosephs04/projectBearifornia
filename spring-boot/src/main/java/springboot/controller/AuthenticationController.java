package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.service.AuthenticationService;
import springboot.UserType;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String[] data) {

        UserType userType = authenticationService.authenticate(data[0], data[1]);

        if (userType == null) {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }

        return ResponseEntity.ok("User authenticated successfully as ");
    }

}
