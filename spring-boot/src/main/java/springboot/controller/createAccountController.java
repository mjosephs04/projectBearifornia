package springboot.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.Admin;
import springboot.UserType;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/register")
public class createAccountController {

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody String[] payload){
        boolean result = false;
        UserType type = UserType.GUEST;
        String message;

        Admin admin = new Admin("default");
        message = admin.addClerk(payload[0], payload[1], type);

        if(message.equalsIgnoreCase("success")){
            result = true;
        }

        if(result){
            return ResponseEntity.ok("Created account.");
        }
        else{
            return ResponseEntity.badRequest().body("Failed to create account." + message);
        }
    }
}
