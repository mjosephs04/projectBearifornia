package springboot;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rooms")
public class RoomController {

    @GetMapping("/nature-retreat")
    public Listing getNatureRetreat(){
        return new Listing("Nature Retreat", 249.99, "\n" +
                "Welcome to our nature retreat-themed hotel room, where tranquility meets comfort.\n" +
                "\n" +
                "A cozy king-sized bed awaits amidst nature-inspired decor. Large windows frame serene " +
                "views of lush greenery, bringing the outdoors inside.\n" +
                "\n" +
                "Unwind in a rustic yet elegant en-suite bathroom with wooden " +
                "accents and organic elements. Escape the hustle and bustle and reconnect with nature in the " +
                "heart of our urban oasis.", "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/14/6f/93/69/wild-grass-nature-resort.jpg?w=700&h=-1&s=1");
    }

    @GetMapping("/urban-elegance")
    public Listing getUrbanElegance(){
        return new Listing("Urban Elegance", 249.99, "\n" +
                "Step into our urban elegance-themed hotel room, where sleek modernity meets metropolitan charm.\n" +
                "\n" +
                "A plush bed beckons amidst tasteful artwork adorning the walls. " +
                "Floor-to-ceiling windows offer stunning city views, while ambient lighting sets a relaxing mood.\n" +
                "\n" +
                "Relax in a spacious en-suite bathroom featuring marble countertops and premium amenities. " +
                "Whether for business or leisure, indulge in luxury and style in the heart of the city.",
                "https://www.designhotels.com/media/2nyj2eve/hotel-urban-s-01r-jpg.jpg?width=1920&format=webp&quality=80&rnd=133053929884700000");
    }

    @GetMapping("/vintage")
    public Listing getVintage(){
        return new Listing("Vintage", 349.99, "\n" +
                "Welcome to our vintage charm-themed hotel room, where nostalgia meets elegance.\n" +
                "\n" +
                "Sink into a sumptuous bed surrounded by retro decor and antique accents.\n" +
                "\n" +
                "Large windows invite in natural light, illuminating classic furnishings and timeless details.\n" +
                "\n" +
                "Step into the past in the en-suite bathroom, with vintage-inspired fixtures and cozy touches.\n" +
                "\n" +
                "Experience the allure of a bygone era in the heart of our stylish retreat.",
                "https://i.pinimg.com/originals/bb/ce/4b/bbce4b1fb3216aa36d02005082896338.jpg");
    }

}
