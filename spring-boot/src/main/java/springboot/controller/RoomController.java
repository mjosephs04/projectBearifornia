package springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.*;
import springboot.service.RoomService;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rooms")
public class RoomController {

    @GetMapping("/nature-retreat")
    public Listing getNatureRetreat() {
        return new Listing("Nature Retreat", 249.99, """

                Welcome to our nature retreat-themed hotel room, where tranquility meets comfort.

                A cozy king-sized bed awaits amidst nature-inspired decor. Large windows frame serene views of lush greenery, bringing the outdoors inside.

                Unwind in a rustic yet elegant en-suite bathroom with wooden accents and organic elements. Escape the hustle and bustle and reconnect with nature in the heart of our urban oasis.""", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/14/6f/93/69/wild-grass-nature-resort.jpg?w=700&h=-1&s=1");
    }

    @GetMapping("/urban-elegance")
    public Listing getUrbanElegance() {
        return new Listing("Urban Elegance", 249.99, """

                Step into our urban elegance-themed hotel room, where sleek modernity meets metropolitan charm.

                A plush bed beckons amidst tasteful artwork adorning the walls. Floor-to-ceiling windows offer stunning city views, while ambient lighting sets a relaxing mood.

                Relax in a spacious en-suite bathroom featuring marble countertops and premium amenities. Whether for business or leisure, indulge in luxury and style in the heart of the city.""", "https://www.designhotels.com/media/2nyj2eve/hotel-urban-s-01r-jpg.jpg?width=1920&format=webp&quality=80&rnd=133053929884700000");
    }

    @GetMapping("/vintage")
    public Listing getVintage() {
        return new Listing("Vintage", 349.99, """

                Welcome to our vintage charm-themed hotel room, where nostalgia meets elegance.

                Sink into a sumptuous bed surrounded by retro decor and antique accents.

                Large windows invite in natural light, illuminating classic furnishings and timeless details.

                Step into the past in the en-suite bathroom, with vintage-inspired fixtures and cozy touches.

                Experience the allure of a bygone era in the heart of our stylish retreat.""", "https://i.pinimg.com/originals/bb/ce/4b/bbce4b1fb3216aa36d02005082896338.jpg");
    }

    @GetMapping("/getAllRooms")
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> rooms;
         rooms = Room.readInAllRooms();

        return ResponseEntity.ok().body(rooms);
    }

    //payload should contain IN THIS ORDER!!!
    // AND!!!!: ONLY GUESTS CAN MODIFY RESERVATIONS, SO THEY SHOULD BE LOGGED IN BEFORE DOING SO
    //AND!!! DO NOT PASS USERNAME TO BACKEND OR ANYTHING

    // roomNumber of room you are trying to modify
    // Double newCost,
    // String New roomType,
    // Integer New numBed,
    // String New quality,
    // String New bedType,
    // boolean New smoking
    @PatchMapping("/updateRoom")
    public ResponseEntity<String> modifyRoom(String[] payload){
        String username = LoggedIn.isLoggedIn();
        UserType type = LoggedIn.type;
        Integer roomNum =Integer.parseInt(payload[0]);

        if(username != null && type.equals(UserType.GUEST)){
            Room r = new Room(roomNum, //roomnum
                                Double.parseDouble(payload[1]), //cost
                                payload[2], //roomtype
                                Integer.parseInt(payload[3]), //numbed
                                payload[4], ///quality
                                payload[5], //bedtype
                                Boolean.parseBoolean(payload[6])); //smoking
            String message = RoomService.modifyRoom(roomNum, r);
            if(message.equalsIgnoreCase("success")){
                return ResponseEntity.ok().body("success");
            }
        }
        return ResponseEntity.badRequest().body("only clerks may modify rooms");

    }
}
