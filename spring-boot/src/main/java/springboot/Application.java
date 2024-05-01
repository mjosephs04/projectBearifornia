package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springboot.database.Setup;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Setup.initialize();
        SpringApplication.run(Application.class, args);
    }
}
