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


    public static Product createAproduct (String name){
        Product b = new Product();
        b.setProductName(name);
        return b;
    }

    public static void addAmountToCart (Product a, int amount){
        for (int i = 0; i < amount; i++){
            Cart.addItem(a);
        }
    }

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





        Product apple = testClass.createAproduct("Apple");
        Product banana = testClass.createAproduct("Banana");
        ProductService.addProduct(apple);
        ProductService.addProduct(banana);
        banana.setProductPrice(100.00);
        banana.setProductStock(3);

        //                        Product   Amount  Cart
        testClass.addAmountToCart(apple, 10);
        testClass.addAmountToCart(banana, 10);

        System.out.println("testing Cart For in Able to Purchase: " + Thing.shopCheckout(Cart.getItems()));
        System.out.println("If was able to purchase price would be: " + Thing.getPriceCart());



    }
}
