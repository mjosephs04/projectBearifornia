//testClass

package springboot;
//Debugging Only
import springboot.controller.AccountController;
import springboot.controller.CartController;
import springboot.database.InitializeDatabase;
import springboot.database.Setup;
import springboot.service.AccountService;
import springboot.service.CartService;
import springboot.service.ProductService;

import java.sql.SQLException;

public class testClass {


    public static void main(String[] args) throws SQLException {

        InitializeDatabase.main(args);
        LoggedIn.logIn("catherine", UserType.GUEST);
        AccountController create = new AccountController(new AccountService(new Setup()));
        create.createGuest(new String[]{
                "cate", "catherine", "password", "GUEST"
        });
        CartController cartServe = new CartController(new CartService(new Setup()));

//        Product ID: "defaultProductId"
//        Product Name: "Default Item"
//        Product Stock: 10
//        Product Description: "This is a default product."
//        Product Price: 10.00
//        Image URL: "https://example.com/default_image.jpg"
//        Category: "Default Category"

        CartService Thing = new CartService(new Setup());
        Product a = new Product();
        Product b = new Product();
        ProductService.addProduct(a);
        ProductService.addProduct(b);
        b.setProductName("item2");
        Cart testCart = new Cart();
        int numberA = 1;
        for (int i = 0; i < numberA; i++){
            testCart.addItem(a);
        }
        testCart.addItem(b);

        System.out.println("testing Cart: " + Thing.parseStockResult(Thing.checkAndUpdateStock(testCart)));



    }
}
