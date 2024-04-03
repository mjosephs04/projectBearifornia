package springboot.controller;

import org.springframework.web.bind.annotation.*;
import springboot.ReservationClass;
import springboot.Room;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ReservationController {
    @PostMapping("/checkForRooms")
    public Room checkAvailability(@RequestBody boolean smoking,
                                     @RequestBody String bedType,
                                     @RequestBody String roomType,
                                     @RequestBody LocalDate startDate,
                                     @RequestBody LocalDate endDate) throws IOException {
        int bedNum;
        if(bedType.equals("Single")){
            bedNum = 1;
        }
        else if(bedType.equals("Double")){
            bedNum = 2;
        }
        else{
            bedNum = 3;
        }
        ReservationClass r = new ReservationClass();
        ArrayList<Room> availableRooms = (ArrayList<Room>) r.searchRooms(smoking,bedType,bedNum,roomType, startDate, endDate);

        if(availableRooms.isEmpty()){
            return null;
        }
        else{
            return availableRooms.get(0);
        }
    }


}
