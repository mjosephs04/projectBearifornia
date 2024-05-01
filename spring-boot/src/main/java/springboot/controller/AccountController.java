package springboot.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.Admin;
import springboot.Clerk;
import springboot.UserType;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/register")
public class createAccountController {

    //payload contains: name, username, password, type
    @PostMapping("/createClerk")
    public ResponseEntity<String> createClerk(@RequestBody String[] payload){
        boolean result = false;
        UserType type = UserType.valueOf(payload[3].toUpperCase());
        String message;

        message = Admin.addClerk(payload[0], payload[1], payload[2], type);

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


    //payload contains: name, username, password, type
    @PostMapping("/createGuest")
    public ResponseEntity<String> createGuest(@RequestBody String[] payload){
        boolean result = false;
        UserType type = UserType.valueOf(payload[3].toUpperCase());
        String message;

        message = Clerk.addGuest(payload[0], payload[1], payload[2], type);

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
