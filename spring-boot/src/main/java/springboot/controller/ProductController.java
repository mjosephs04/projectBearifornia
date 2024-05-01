package springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.dto.Product;
import springboot.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{productName}")
    public ResponseEntity<?> getProduct(@PathVariable String productName) {
        Product product = productService.getProductByName(productName);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

