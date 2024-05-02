package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springboot.database.InitializeDatabase;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        InitializeDatabase.main(args);
        SpringApplication.run(Application.class, args);
    }
}
