package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.User;
import springboot.UserFunctions;
import springboot.service.AuthenticationService;
import springboot.UserType;

import java.io.IOException;
import java.util.ArrayList;


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

        return ResponseEntity.ok(new String[]{"User authenticated successfully as " + data[0] + " " + data[1], userType.toString()});
    }

    @PostMapping("/getAllUsers")
    public ResponseEntity<String> getAllUsers() {
        ArrayList<User> users = null;
        try {
            users = (ArrayList<User>) UserFunctions.readInAllUsers();
        }catch(IOException e) {
            return ResponseEntity.ok("failure -- couldn't read in all users");
        }

        return ResponseEntity.ok("success -- all users read");

    }

}
