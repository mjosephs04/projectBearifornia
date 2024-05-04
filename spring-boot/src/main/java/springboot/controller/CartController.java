//Cart Controller

package springboot.controller;


//Changed IMports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.Cart;
import springboot.LoggedIn;
import springboot.Product;
import springboot.UserType;
import springboot.service.CartService;
import springboot.service.ProductService;

import java.util.List;

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
    public ResponseEntity<String> getCartTotal() {
        String result = CartService.getPriceCart(Cart.getItems());
        if (result.startsWith("Error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }



    //Returns success if checkout happened
    //Returns Failed to complete purchase {ITEM} is out of stock if out of stock
    //Returns You are Not logged in if your not logged in
    @GetMapping("/shopCheckout")
    public ResponseEntity<String> shopCheckout() {
        String result = CartService.shopCheckout(Cart.getItems());
        if(LoggedIn.isLoggedIn()) {
            if (result.equals("t")) {
                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.badRequest().body("Failed to complete purchase " + result + " is out of stock.");
            }
        }else{
            return ResponseEntity.badRequest().body("You are not logged in");
        }
    }


    //Cross-references Product database for stock and adds to STATIC CART
    // Returns
    // success - added to cart
    // Failed to complete purchase {payload} is out of stock - if out of stock item
    // HTTP BAD REQUEST - Something exploded GET HELP
    // You are not logged in - User not logged in
    @GetMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestBody String payload) {
        if(LoggedIn.isLoggedIn()) {
            String result = CartService.addToCart(payload);
            if (result.equals("success")) {
                return ResponseEntity.ok("success");
            } else if (result.equals("f")) {
                return ResponseEntity.badRequest().body("Failed to complete purchase " + result + " is out of stock.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }
        } else {
            return ResponseEntity.badRequest().body("You are not logged in.");
        }
    }


    //Returns List of Products if logged in Else returns NUll
    @GetMapping("/viewCart")
    public ResponseEntity<List<Product>> addToCart() {
        if(LoggedIn.isLoggedIn()) {
            return ResponseEntity.ok(Cart.getItems());
        } else{
            return ResponseEntity.badRequest().body(null);
        }
    }

}

