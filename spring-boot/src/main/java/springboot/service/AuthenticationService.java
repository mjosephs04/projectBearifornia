package springboot.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import springboot.UserType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class AuthenticationService {

    public UserType authenticate(String username, String password) {
        String line;
        String splitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(new ClassPathResource("Users.csv").getFile()))) {
            while ((line = br.readLine()) != null) { //reads until the end of the stream
                String[] user = line.split(splitBy); // use comma as separator
                if (user[0].equals(username) && user[1].equals(password)) {
                    return UserType.valueOf(user[2].toUpperCase()); // Convert userType String to Enum
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
