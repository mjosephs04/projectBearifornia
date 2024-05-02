package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.UserType;
import springboot.dto.Product;
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

}

