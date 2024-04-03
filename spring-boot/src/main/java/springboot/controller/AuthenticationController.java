package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.service.AuthenticationService;
import springboot.UserType;

/*
// Example React function to handle login
function loginUser() {
    const username = 'user'; // These values would actually come from form inputs
    const password = 'pass';

    fetch('/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);
        // Handle success here (e.g., redirecting to another page or showing a success message)
    })
    .catch((error) => {
        console.error('Error:', error);
        // Handle errors here (e.g., showing an error message)
    });
}

*/

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserType userType = authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (userType == null) {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }

        return ResponseEntity.ok("User authenticated successfully as " + userType);
    }

    static class LoginRequest {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
