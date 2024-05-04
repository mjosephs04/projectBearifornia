//ProdController

package springboot.controller;

//Changed Imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.Product;
import springboot.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/products")
public class ProductController {

    @GetMapping("/{productName}")
    public ResponseEntity<?> getProduct(@PathVariable String productName) {
        Product product = ProductService.getProductByName(productName);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //payload should contain String productName, String productStock, String productDescription, String productPrice, String imageURL, String category
    @PostMapping("/createProduct")
    public ResponseEntity<String> createProduct(@RequestBody String[] payload) {
        int stock = Integer.parseInt(payload[1]);
        double price = Double.parseDouble(payload[3]);
        String message = ProductService.createProduct(payload[0],
                stock,
                payload[2],
                price,
                payload[4],
                payload[5] );

        if ("success".equalsIgnoreCase(message)) {
            return ResponseEntity.ok("Created the new product.");
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }



    //Payload Should Contain Item Name (Case Sensitive to be safe)
    @PostMapping("/deleteProduct")
    public ResponseEntity<String> deleteProduct(@RequestBody String[] payload) {
        String message = ProductService.deleteProduct(payload[1]);
        if ("success".equalsIgnoreCase(message)) {
            return ResponseEntity.ok("Deleted the product.");
        } else if ("Product not found".equalsIgnoreCase(message)) {
            return ResponseEntity.ok("Product Not Found");
        }else {
            return ResponseEntity.badRequest().body(message);
        }
    }

}
