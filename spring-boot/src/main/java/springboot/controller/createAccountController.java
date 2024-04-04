package springboot.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.Admin;
import springboot.UserType;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/register")
public class createAccountController {

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(String username, String password){
        boolean result = false;
        UserType type = UserType.GUEST;
        String message;

        Admin admin = new Admin("default", 1001);
        message = admin.addUser(username, password, type);

        if(message.equalsIgnoreCase("success")){
            result = true;
        }

        if(result){
            return ResponseEntity.ok("Created account.");
        }
        else{
            return ResponseEntity.badRequest().body("Failed to create account.");
        }
    }
}
