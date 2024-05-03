package springboot.controller;


//Changed IMports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.UserType;
import springboot.service.CartService;
import springboot.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/products")
public class CartController {

    private final CartService CartService;

    @Autowired
    public CartController(CartService CartService) {
        this.CartService = CartService;
    }

    @GetMapping("/cartTotal")
    public ResponseEntity<String> getCartTotal(@RequestBody String[] payload) {
        String result = CartService.getPriceCart(payload);
        if (result.startsWith("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/shopCheckout")
    public ResponseEntity<String> shopCheckout(@RequestBody String[] payload) {
        String result = CartService.shopCheckout(payload);
        if (result.equals("t")) {
            return ResponseEntity.ok("success");
        } else{
            return ResponseEntity.ok("Failed to complete purchase " + result + " is out of stock.");
        }
    }

}

